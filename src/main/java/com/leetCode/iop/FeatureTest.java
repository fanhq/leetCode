package com.leetCode.iop;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

/**
 * Created by Hachel on 2018/2/27
 */
public class FeatureTest {


    public static void main(String[] args) {
        Map<String, List<FeatureDto>> book = getCodeBook();
        List<FeatureDto> list = book.get("00000001");
        String dna = getDNA(list);
        System.out.println(dna);
        long start = System.currentTimeMillis();
        StringBuilder newDna = new StringBuilder(dna);
        for (FeatureDto featureDto : list) {
            if (dna.charAt(featureDto.getColumnNum() - 1) == featureDto.getBigDataFeature()) {
                newDna.replace(featureDto.getColumnNum() - 1, featureDto.getColumnNum(), String.valueOf(featureDto.getMyFeature()));
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(newDna.toString());

        List<String> dnas = getDNAs(list);
        long start1 = System.currentTimeMillis();
        for (String d : dnas) {
            //System.out.println(dna);
            StringBuilder nd = new StringBuilder(d);
            for (FeatureDto featureDto : list) {
                if (d.charAt(featureDto.getColumnNum() - 1) == featureDto.getBigDataFeature()) {
                    nd.replace(featureDto.getColumnNum() - 1, featureDto.getColumnNum(), String.valueOf(featureDto.getMyFeature()));
                }
            }
           // System.out.println(newDna.toString());
        }
        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start1);
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

    //获取随机字母
    public static char getRandomCharacter() {
        return  RandomStringUtils.randomAscii(1).charAt(0);
    }

    //获取随机数
    public static int getRandomNum() {
        int max = 500;
        int min = 1;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    //获取编码本
    public static Map<String, List<FeatureDto>> getCodeBook() {
        Map<String, List<FeatureDto>> codeBook = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            List<FeatureDto> list = new ArrayList<>();
            for (int j = 0; j < 150; j++) {
                FeatureDto featureDto = new FeatureDto();
                featureDto.setColumnNum(getRandomNum());
                for (int n =0; n< 10; n++){
                    featureDto.setBigDataFeature(getRandomCharacter());
                    featureDto.setMyFeature(getRandomCharacter());
                }
                list.add(featureDto);
            }
            codeBook.put(String.format("%08d", i), list);
        }
        return codeBook;
    }


}
