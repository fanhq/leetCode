package com.fanhq.example.zkCurator;

/**
 * Created by Hachel on 2018/1/23
 */
public class ServerPayload {

    private String cluster;
    private int payload;

    public ServerPayload(){

    }

    public ServerPayload(String cluster, int payload) {
        this.cluster = cluster;
        this.payload = payload;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public int getPayload() {
        return payload;
    }

    public void setPayload(int payload) {
        this.payload = payload;
    }
}
