package com.starpattern.assistant;

public class ResultRecord {
    public final int round;
    public final String result;
    public final String family;
    public final long time;
    public ResultRecord(int round, String result, String family, long time) {
        this.round = round; this.result = result; this.family = family; this.time = time;
    }
}
