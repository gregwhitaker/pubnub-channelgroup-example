package com.github.gregwhitaker.pubnub.example.publishers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemoryMetricsPublisher implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(MemoryMetricsPublisher.class);

    private final String subscribeKey;
    private final String publishKey;

    public MemoryMetricsPublisher(String subscribeKey, String publishKey) {
        this.subscribeKey = subscribeKey;
        this.publishKey = publishKey;
    }

    @Override
    public void run() {

    }
}
