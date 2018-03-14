package com.leetCode.iop;

/**
 * Created by Hachel on 2018/2/27
 */
public class FeatureDto {

    private int columnNum;

    private char myFeature;

    private char bigDataFeature;

    private long iopKey;

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public char getMyFeature() {
        return myFeature;
    }

    public void setMyFeature(char myFeature) {
        this.myFeature = myFeature;
    }

    public char getBigDataFeature() {
        return bigDataFeature;
    }

    public void setBigDataFeature(char bigDataFeature) {
        this.bigDataFeature = bigDataFeature;
    }

    public long getIopKey() {
        return iopKey;
    }

    public void setIopKey(long iopKey) {
        this.iopKey = iopKey;
    }
}
