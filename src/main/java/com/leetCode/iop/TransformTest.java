package com.leetCode.iop;

/**
 * Created by Hachel on 2018/3/15
 */
public class TransformTest {


    public static void main(String[] args) {
        String originDna = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------" +
                "----------------------------$]S---";
        System.out.println(originDna.length());
        String newDna = FeatureUtil.transform2IopDna(originDna, "20180315_00001");
        System.out.println(newDna);
        String oldDna = FeatureUtil.transform2BigData(newDna, "20180315_00001");
        System.out.println(oldDna);

    }

}
