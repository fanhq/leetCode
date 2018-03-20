package com.leetCode.iop;

import com.ai.obc.iop.cache.RedisTemplateUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.leetCode.security.AESUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Hachel on 2018/3/5
 * 必须配置redis参数
 * -Dredis.conf=
 */
public final class FeatureUtil {

    private final static Logger logger = LoggerFactory.getLogger(FeatureUtil.class);

    /**
     * 编码本在redis中的key
     */
    public static final String CODE_KEY = "codeIdKey";

    private static final char SEPARATOR = 1;

    private static final int EXPIRE_DAY = 3;

    private static final String KEY_SETS = "BIGDATA#CODEID";

    private static final ReentrantLock lock = new ReentrantLock();

    private static final FeatureUtil instance = new FeatureUtil();

    private final RedisTemplate<String, String> stringRedisTemplate;

    private final ListOperations<String, String> listOperations;

    private final SetOperations<String, String> setOperations;

    //编码本
    private final Cache<String, List<FeatureDto>> bookCache;

    private final ScheduledExecutorService scheduledThreadPool;

    private FeatureUtil() {
        this.stringRedisTemplate = RedisTemplateUtil.newInstance(String.class);
        this.listOperations = stringRedisTemplate.opsForList();
        this.setOperations = stringRedisTemplate.opsForSet();
        this.bookCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_DAY, TimeUnit.DAYS).build();
        this.scheduledThreadPool = Executors.newScheduledThreadPool(1);
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据编码本把大数据的DNA转换成IOP的映射值
     *
     * @param originDna 大数据生成DNA值
     * @param codeId    编码本ID
     * @return
     */
    public static String transform2IopDna(String originDna, String codeId) {
        if (codeId == null) {
            return originDna;
        }
        List<FeatureDto> featureDtos = getBookByKey(codeId);
        if (featureDtos == null) {
            logger.info("编码本未获取到，编码本ID:{}", codeId);
            return originDna;
        }
        StringBuilder newDna = new StringBuilder(originDna);
        for (FeatureDto featureDto : featureDtos) {
            int charIndex = featureDto.getColumnNum() - 1;
            if (featureDto.getBigDataMappedVal() == originDna.charAt(charIndex)) {
                newDna.replace(charIndex, featureDto.getColumnNum(), String.valueOf(featureDto.getIopMappedVal()));
            }
        }
        return newDna.toString();
    }

    /**
     * 根据编码本把IOPDNA值转换为大数据的值，用于更新redis
     *
     * @param originDna
     * @param codeId
     * @return
     */
    public static String transform2BigData(String originDna, String codeId) {
        if (codeId == null) {
            return originDna;
        }
        List<FeatureDto> featureDtos = getBookByKey(codeId);
        if (featureDtos == null) {
            logger.info("编码本未获取到，编码本ID:{}", codeId);
            return originDna;
        }
        StringBuilder newDna = new StringBuilder(originDna);
        for (FeatureDto featureDto : featureDtos) {
            int charIndex = featureDto.getColumnNum() - 1;
            if (featureDto.getIopMappedVal() == originDna.charAt(charIndex)) {
                newDna.replace(charIndex, featureDto.getColumnNum(), String.valueOf(featureDto.getBigDataMappedVal()));
            }
        }
        return newDna.toString();
    }

    /**
     * 获取 获取编码本
     *
     * @param codeId
     * @return
     */
    private static List<FeatureDto> getBookByKey(String codeId) {
        try {
            lock.lock();
            return instance.bookCache.getIfPresent(codeId);
        } finally {
            lock.unlock();
        }
    }

    private void init() throws Exception {
        //加载最近三天的编码本
        loadLast3DaysBook();
        //定时任务从redis中更新编码本
        this.scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                onWork();
            }
        }, 1, 1, TimeUnit.MINUTES);
    }

    /**
     * 工作线程方法
     */
    private void onWork() {
        try {
            Set<String> codeIds = this.setOperations.members(KEY_SETS);
            if (CollectionUtils.isEmpty(codeIds)) {
                logger.info("codeId set is empty");
                return;
            }
            long expireTime = getExpireBeforeTime();
            for (String codeId : codeIds) {
                String[] wrap = codeId.split(String.valueOf(SEPARATOR));
                List<FeatureDto> featureDtos = this.bookCache.getIfPresent(wrap[0]);
                //没有则新增
                if (CollectionUtils.isEmpty(featureDtos) && expireTime < Long.valueOf(wrap[1])) {
                    List<String> codeValues = this.listOperations.range(codeId, 0, -1);
                    if (CollectionUtils.isEmpty(codeValues)) {
                        throw new Exception("codeValues error");
                    }
                    addCodeBook(codeId, codeValues);
                }
                //如果过期了则移除
                if (expireTime > Long.valueOf(wrap[1])) {
                    this.setOperations.remove(KEY_SETS, codeId);
                }
            }
        } catch (Exception e) {
            logger.error("update thread error :{}", e);
        }
    }

    /**
     * 增加或者新增编码本
     *
     * @param codes
     */
    private void addCodeBook(String key, List<String> codeVal) throws Exception {
        logger.info("begin load codebook key ={}", key);
        List<FeatureDto> featureDtos = new ArrayList<>();
        for (String val : codeVal) {
            String relVal = new String(AESUtil.decrypt(Base64.decodeBase64(val), AESUtil.DEFAULT_KEY));
            String[] vals = relVal.split(String.valueOf(SEPARATOR));
            FeatureDto featureDto = new FeatureDto();
            featureDto.setColumnNum(Integer.valueOf(vals[0]));
            featureDto.setIopMappedVal(vals[1].charAt(0));
            featureDto.setBigDataMappedVal(vals[2].charAt(0));
            featureDtos.add(featureDto);
        }
        try {
            lock.lock();
            this.bookCache.put(key, featureDtos);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 加载最近三天的编码本
     * codeId = 大数据的编码本文件名称 + 时间戳
     */
    private void loadLast3DaysBook() throws Exception {
        Set<String> codeIdSet = this.setOperations.members(KEY_SETS);
        if (CollectionUtils.isEmpty(codeIdSet)) {
            logger.info("codeIds is empty");
            return;
        }
        long expireTime = getExpireBeforeTime();
        for (String codeId : codeIdSet) {
            String[] wrap = codeId.split(String.valueOf(SEPARATOR));
            if (Long.valueOf(wrap[1]) > expireTime) {
                List<String> codeValues = this.listOperations.range(wrap[0], 0, -1);
                addCodeBook(wrap[0], codeValues);
            } else {
                this.setOperations.remove(KEY_SETS, codeId);
            }
        }
    }

    /**
     * 获取三天前的时间戳
     *
     * @return
     */
    private long getExpireBeforeTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -EXPIRE_DAY);
        return calendar.getTimeInMillis();
    }


    private static class FeatureDto {

        private char iopMappedVal;

        private char bigDataMappedVal;

        private int columnNum;

        public char getIopMappedVal() {
            return iopMappedVal;
        }

        public void setIopMappedVal(char iopMappedVal) {
            this.iopMappedVal = iopMappedVal;
        }

        public char getBigDataMappedVal() {
            return bigDataMappedVal;
        }

        public void setBigDataMappedVal(char bigDataMappedVal) {
            this.bigDataMappedVal = bigDataMappedVal;
        }

        public int getColumnNum() {
            return columnNum;
        }

        public void setColumnNum(int columnNum) {
            this.columnNum = columnNum;
        }
    }


}
