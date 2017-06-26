package com.github.gregwhitaker.pubnub.example.publishers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CpuMetricsPublisher implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(CpuMetricsPublisher.class);

    private final String subscribeKey;
    private final String publishKey;

    public CpuMetricsPublisher(final String subscribeKey, final String publishKey) {
        this.subscribeKey = subscribeKey;
        this.publishKey = publishKey;
    }

    @Override
    public void run() {

    }
}
