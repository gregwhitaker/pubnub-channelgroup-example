package com.github.gregwhitaker.pubnub.example.publishers;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Publishes CPU usage metrics to PubNub.
 */
public class CpuMetricsPublisher implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(CpuMetricsPublisher.class);

    private final PubNub pubNub;

    public CpuMetricsPublisher(final String subscribeKey, final String publishKey) {
        // Create PubNub configuration
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(subscribeKey);
        pnConfiguration.setPublishKey(publishKey);

        this.pubNub = new PubNub(pnConfiguration);
    }

    @Override
    public void run() {
        LOG.info("CpuMetricsPublisher started!");
    }
}
