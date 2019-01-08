package com.wardziniak.kafka.streams.apps.missing_records;

public class CachedSessionModel {

    public final static String RUNNING = "RUNNING";
    public final static String DONE = "DONE";

    public String sessionId;
    public String lastStatus;
    public long lastTimestamp;


    public CachedSessionModel(){}


    public CachedSessionModel(String sessionId, String lastStatus, long lastTimestamp) {
        this.sessionId = sessionId;
        this.lastStatus = lastStatus;
        this.lastTimestamp = lastTimestamp;
    }

}
