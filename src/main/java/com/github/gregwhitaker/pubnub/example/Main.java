package com.github.gregwhitaker.pubnub.example;

import com.github.gregwhitaker.pubnub.example.publishers.CpuMetricsPublisher;
import com.github.gregwhitaker.pubnub.example.publishers.MemoryMetricsPublisher;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Starts the PubNub Channel Groups example.
 */
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    /**
     * Main entry-point of the application.
     */
    public static void main(String... args) {
        String subscribeKey = System.getProperty("subscribeKey");
        if (subscribeKey == null || subscribeKey.isEmpty()) {
            throw new IllegalArgumentException("System property 'subscribeKey' is required!");
        }

        String publishKey = System.getProperty("publishKey");
        if (publishKey == null || publishKey.isEmpty()) {
            throw new IllegalArgumentException("System property 'publishKey' is required!");
        }

        LOG.info("Creating metrics publishers...");

        // Create metrics publishers
        CpuMetricsPublisher cpuPublisher = new CpuMetricsPublisher(subscribeKey, publishKey);
        MemoryMetricsPublisher memoryPublisher = new MemoryMetricsPublisher(subscribeKey, publishKey);

        LOG.info("Starting metrics publishers...");

        // Execute the metrics publishers
        EXECUTOR.submit(cpuPublisher);
        EXECUTOR.submit(memoryPublisher);

        // Create PubNub configuration
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(subscribeKey);
        pnConfiguration.setPublishKey(publishKey);

        PubNub pubnub = new PubNub(pnConfiguration);

        // Create listener for the "metrics.*" channel group
        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {

            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {

            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        });

        LOG.info("Subscribing to 'metrics.*' channel group...");

        // Subscribe to the "metrics.*" channel group
        pubnub.subscribe()
                .channelGroups(Arrays.asList("metrics.*"))
                .execute();
    }
}
