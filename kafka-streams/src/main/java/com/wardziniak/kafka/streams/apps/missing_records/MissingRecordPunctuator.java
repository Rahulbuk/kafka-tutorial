package com.wardziniak.kafka.streams.apps.missing_records;

import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.Punctuator;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

public class MissingRecordPunctuator implements Punctuator {

    private String sessionStateStoreName;
    private KeyValueStore<String, CachedSessionModel> sessionStateStore;
    private ProcessorContext context;

    public MissingRecordPunctuator(String sessionStateStoreName, ProcessorContext context) {
        this.sessionStateStoreName = sessionStateStoreName;
        this.context = context;
        this.sessionStateStore = (KeyValueStore<String, CachedSessionModel>) context.getStateStore(sessionStateStoreName);
    }

    @Override
    public void punctuate(long timestamp) {
        long currentTimestamp = context.timestamp();
        KeyValueIterator<String, CachedSessionModel> sessions = sessionStateStore.all();

        sessions.forEachRemaining(keyValue -> {
            CachedSessionModel sessionModel = keyValue.value;
            if (sessionModel.lastTimestamp + DetectMissingRecordApp.SESSION_TIMEOUT < currentTimestamp) {
                context.forward(keyValue.key, new SessionModel(keyValue.key, keyValue.value.lastStatus));
                sessionStateStore.delete(keyValue.key);
            }
        });
    }
}
