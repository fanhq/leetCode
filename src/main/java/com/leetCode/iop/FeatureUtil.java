package com.leetCode.iop;

import com.ai.obc.common.util.AESUtil;
import com.ai.obc.iop.cache.RedisTemplateUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    private static final String KEYS_LIST = "BIGDATA:CODEIDS";

    private static final int LOAD_BOOK_MAX = 10;

    private static final FeatureUtil instance = new FeatureUtil();

    private final RedisTemplate<String, String> stringRedisTemplate;

    private final ListOperations<String, String> listOperations;

    //编码本
    private final Map<String, List<FeatureDto>> bookCache;


    private final ScheduledExecutorService scheduledThreadPool;

    private FeatureUtil() {
        this.stringRedisTemplate = RedisTemplateUtil.newInstance(String.class);
        this.listOperations = stringRedisTemplate.opsForList();
        this.bookCache = new HashMap<>();
        this.scheduledThreadPool = Executors.newScheduledThreadPool(1);
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException("FeatureUtil init");
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
        List<FeatureDto> featureDtos = instance.bookCache.get(codeId);
        if (featureDtos == null) {
            logger.info("编码本未获取到，编码本ID:{}", codeId);
            return originDna;
        }
        StringBuilder newDna = new StringBuilder(originDna);
        for (FeatureDto featureDto : featureDtos) {
            if (featureDto.getColumnNum() > originDna.length()) {
                continue;
            }
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
        List<FeatureDto> featureDtos = instance.bookCache.get(codeId);
        if (featureDtos == null) {
            logger.info("编码本未获取到，编码本ID:{}", codeId);
            return originDna;
        }
        StringBuilder newDna = new StringBuilder(originDna);
        for (FeatureDto featureDto : featureDtos) {
            if (featureDto.getColumnNum() > originDna.length()) {
                continue;
            }
            int charIndex = featureDto.getColumnNum() - 1;
            if (featureDto.getIopMappedVal() == originDna.charAt(charIndex)) {
                newDna.replace(charIndex, featureDto.getColumnNum(), String.valueOf(featureDto.getBigDataMappedVal()));
            }
        }
        return newDna.toString();
    }

    /**
     * 获取某一位的大数据映射值
     *
     * @param codeId
     * @param colNum
     * @return
     */
    public static String getMappedValByCol(String codeId, int colNum) {
        List<FeatureDto> featureDtos = new ArrayList<>();
        List<FeatureDto> allFeature = instance.bookCache.get(codeId);
        if (allFeature != null) {
            for (FeatureDto featureDto : allFeature) {
                if (featureDto.getColumnNum() == colNum) {
                    featureDtos.add(featureDto);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (FeatureDto featureDto : featureDtos) {
            sb.append(featureDto.toString() + " ;");
        }
        return sb.toString();
    }

    private void init() throws Exception {
        loadBook();
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
            loadBook();
        } catch (Exception e) {
            logger.error("update thread error :{}", e);
        }
    }

    /**
     * 增加或者新增编码本
     *
     * @param key
     * @param codeVal
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
        this.bookCache.put(key, featureDtos);
    }

    /**
     * @throws Exception
     */
    private void loadBook() {
        long maxCount = this.listOperations.size(KEYS_LIST);
        long loadedCount = 0;
        for (int i = 0; i < maxCount; i++) {
            String codeId = this.listOperations.index(KEYS_LIST, i);
            if (codeId == null) {
                continue;
            }
            //不让加载一套失败而不继续加载了
            try {
                String preCodeId = this.listOperations.index(KEYS_LIST, (i + 1));
                //没有则新增,前一个和当前相等表示修改
                if (this.bookCache.containsKey(codeId) == false || codeId.equals(preCodeId)) {
                    List<String> codeValues = this.listOperations.range(codeId, 0, -1);
                    if (CollectionUtils.isNotEmpty(codeValues)) {
                        addCodeBook(codeId, codeValues);
                        loadedCount++;
                    }
                    if (codeId.equals(preCodeId)) {
                        this.listOperations.remove(KEYS_LIST, 1, codeId);
                        maxCount--;
                    }
                }
            } catch (Exception e) {
                logger.error("codeId :{} error:{}", codeId, e);
            }
            //加载超过十个后，移除其他的，map保留最新的10套编码本
            if (loadedCount > LOAD_BOOK_MAX ) {
                this.bookCache.remove(codeId);
            }
        }
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

        @Override
        public String toString() {
            return "{" +
                    "iopMappedVal=" + iopMappedVal +
                    ", bigDataMappedVal=" + bigDataMappedVal +
                    ", columnNum=" + columnNum +
                    '}';
        }
    }

}
