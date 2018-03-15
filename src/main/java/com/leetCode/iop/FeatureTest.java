package com.leetCode.iop;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Hachel on 2018/2/27
 */
public class FeatureTest {

    private static final char sp = 1;

    private static int offset = 0;

    /**
     * 数据库连接池
     **/
    private static DruidDataSource selfsale;


    public static void main(String[] args) {
//        Map<String, List<FeatureDto>> book = getCodeBook();
//        List<FeatureDto> list = book.get("00000001");
//
//
//        String dna = getDNA(list);
//
//        System.out.println("------begin------");
//        System.out.println(dna);
//        long start = System.currentTimeMillis();
//        StringBuilder newDna = new StringBuilder(dna);
//        for (FeatureDto featureDto : list) {
//            if (dna.charAt(featureDto.getColumnNum() - 1) == featureDto.getBigDataFeature()) {
//                newDna.replace(featureDto.getColumnNum() - 1, featureDto.getColumnNum(), String.valueOf(featureDto.getMyFeature()));
//            }
//        }
//        long end = System.currentTimeMillis();
//        System.out.println(end - start);
//        System.out.println(newDna.toString());

//        List<String> dnas = getDNAs(list);
//        long start1 = System.currentTimeMillis();
//        for (String d : dnas) {
//            //System.out.println(dna);
//            StringBuilder nd = new StringBuilder(d);
//            for (FeatureDto featureDto : list) {
//                if (d.charAt(featureDto.getColumnNum() - 1) == featureDto.getBigDataFeature()) {
//                    nd.replace(featureDto.getColumnNum() - 1, featureDto.getColumnNum(), String.valueOf(featureDto.getMyFeature()));
//                }
//            }
//            // System.out.println(newDna.toString());
//        }
//        long end1 = System.currentTimeMillis();
//        System.out.println(end1 - start1);

        initDataSource();
        List<Long> keys = getIOPKeys();
        List<FeatureDto> book = getCodeBook(keys);
        List<String> dnas = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            String dna = getDNAR(book);
            dnas.add(dna);
        }

        try {
            //写文件
            BufferedWriter osw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("D:\\temp file\\code.val"), true), "UTF-8"));
            book.forEach(x -> {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.valueOf(x.getIopKey()) + sp + x.getBigDataFeature());
                    osw.write(sb.toString());
                    osw.newLine();
                    osw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            osw.close();
            StringBuilder builder = new StringBuilder("phoneno");
            for (int i = 801; i < 833; i++) {
                builder.append(String.valueOf(sp) + i);
            }
            BufferedWriter oswb = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("D:\\temp file\\dna.val"), true), "UTF-8"));
            oswb.write(builder.toString());
            oswb.newLine();
            oswb.flush();
            long phoneNnm = 15900100000l;
            dnas.forEach(x -> {
                try {
                    x = x.replace("-", "");
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < x.length(); i++) {
                        t.append(String.valueOf(sp) + String.valueOf(x.charAt(i)));
                    }
                    x = String.valueOf(phoneNnm + offset) + t.toString();
                    oswb.write(x);
                    oswb.newLine();
                    oswb.flush();
                    offset++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            oswb.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<String> getDNAs(List<FeatureDto> list) {
        List<String> dnas = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            dnas.add(getDNA(list));
        }
        return dnas;
    }

    public static String getDNA(List<FeatureDto> list) {
        StringBuilder dna = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            dna.append("-");
        }
        for (FeatureDto featureDto : list) {
            dna.replace(featureDto.getColumnNum() - 1, featureDto.getColumnNum(), String.valueOf(featureDto.getBigDataFeature()));
        }
        return dna.toString();
    }

    public static String getDNAR(List<FeatureDto> list) {
        StringBuilder dna = new StringBuilder();
        for (int i = 0; i < 850; i++) {
            dna.append("-");
        }
        Map<Integer, List<FeatureDto>> colMap = getByColMap(list);
        for (int i = 801; i < 833; i++) {
            List<FeatureDto> data = colMap.get(i);
            FeatureDto featureDto = data.get(getRandomNum(data.size() - 1));
            dna.replace(featureDto.getColumnNum() - 1, featureDto.getColumnNum(), String.valueOf(featureDto.getBigDataFeature()));
        }
        return dna.toString();
    }

    public static Map<Integer, List<FeatureDto>> getByColMap(List<FeatureDto> list) {
        Map<Integer, List<FeatureDto>> map = new HashMap<>();
        for (FeatureDto featureDto : list) {
            List<FeatureDto> temp = map.get(featureDto.getColumnNum());
            if (temp == null) {
                temp = new ArrayList<>();
                map.put(featureDto.getColumnNum(), temp);
            }
            temp.add(featureDto);
        }
        return map;
    }

    //获取随机字母
    public static char getRandomCharacter() {
        return RandomStringUtils.randomAscii(1).charAt(0);
    }

    //获取随机数
    public static int getRandomNum() {
        int max = 900;
        int min = 800;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    //获取随机数
    public static int getRandomNum(int max) {
        int min = 0;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    //获取编码本
    public static Map<String, List<FeatureDto>> getCodeBook() {
        Map<String, List<FeatureDto>> codeBook = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            List<FeatureDto> list = new ArrayList<>();
            for (int j = 0; j < 150; j++) {
                int columnNum = getRandomNum();
                for (int n = 0; n < 10; n++) {
                    FeatureDto featureDto = new FeatureDto();
                    featureDto.setColumnNum(columnNum);
                    featureDto.setBigDataFeature(getRandomCharacter());
                    featureDto.setMyFeature(getRandomCharacter());
                    list.add(featureDto);
                }
            }
            codeBook.put(String.format("%08d", i), list);
        }
        return codeBook;
    }

    public static List<FeatureDto> getCodeBook(List<Long> keys) {
        List<FeatureDto> list = new ArrayList<>();
        int columnNum = 801;
        for (int j = 0; j < keys.size(); j++) {
            if (columnNum > 832) {
                columnNum = 801;
            }
            FeatureDto featureDto = new FeatureDto();
            featureDto.setColumnNum(columnNum);
            featureDto.setBigDataFeature(getRandomCharacter());
            featureDto.setMyFeature(getRandomCharacter());
            featureDto.setIopKey(keys.get(j));
            list.add(featureDto);
            columnNum++;
        }
        return list;
    }

    private static List<Long> getIOPKeys() {
        List<Long> keys = new ArrayList<>();
        try {
            Connection conn = selfsale.getConnection();
            String sql = "SELECT ID FROM COLUMN_VALUE_MAP WHERE  COLUMN_NUM >800 AND COLUMN_NUM < 900";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                keys.add(rs.getLong("ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keys;
    }


    /**
     * 初始化
     *
     * @param
     */
    private static void initDataSource() {
        selfsale = new DruidDataSource();
        selfsale.setDriverClassName("oracle.jdbc.OracleDriver");
        selfsale.setUrl("jdbc:oracle:thin:@10.19.13.43:1521:orc11g");
        selfsale.setPassword("ynselfsale");
        selfsale.setUsername("ynselfsale");
        selfsale.setInitialSize(1);
        selfsale.setMinIdle(2);
        selfsale.setMaxActive(4);
        selfsale.setOracle(true);
        selfsale.setDbType("oracle");

        try {
            selfsale.init();
        } catch (SQLException e) {
            System.exit(-1);
        }
        // 数据库连接属性初始化结束
    }

}
