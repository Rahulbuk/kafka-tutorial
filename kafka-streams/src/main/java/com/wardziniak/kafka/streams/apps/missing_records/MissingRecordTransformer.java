package com.wardziniak.kafka.streams.apps.missing_records;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Optional;

public class MissingRecordTransformer implements Transformer<String, String, KeyValue<String, SessionModel>> {

    private String sessionStateStoreName;
    private KeyValueStore<String, CachedSessionModel> sessionStateStore;
    private ProcessorContext context;

    public MissingRecordTransformer(String sessionStateStoreName) {
        this.sessionStateStoreName = sessionStateStoreName;
    }

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.sessionStateStore = (KeyValueStore<String, CachedSessionModel>) context.getStateStore(sessionStateStoreName);
        context.schedule(DetectMissingRecordApp.PUNCTUATION_INTERVAL, PunctuationType.WALL_CLOCK_TIME, new MissingRecordPunctuator(sessionStateStoreName, context));
    }

    @Override
    public KeyValue<String, SessionModel> transform(String sessionId, String newStatus) {
        sessionStateStore.put(sessionId, new CachedSessionModel(sessionId, newStatus, context.timestamp()));
        return null;
    }

    @Override
    public void close() {

    }
}
