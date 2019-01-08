package com.wardziniak.kafka.streams.apps.missing_records;

public class SessionModel {
    public String sessionId;
    public String sesssionStatus;

    public SessionModel(String sessionId, String sesssionStatus) {
        this.sessionId = sessionId;
        this.sesssionStatus = sesssionStatus;
    }
}