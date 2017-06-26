package com.github.gregwhitaker.pubnub.example.publishers;

import com.google.gson.JsonObject;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.sun.management.OperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;

/**
 * Publishes memory usage metrics to PubNub.
 */
public class MemoryMetricsPublisher implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(MemoryMetricsPublisher.class);

    private final PubNub pubNub;

    public MemoryMetricsPublisher(final String subscribeKey, final String publishKey) {
        // Create PubNub configuration
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey(subscribeKey);
        pnConfiguration.setPublishKey(publishKey);

        this.pubNub = new PubNub(pnConfiguration);
    }

    @Override
    public void run() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        for (int i=0; i < Integer.MAX_VALUE; i++) {
            double totalMemory = osBean.getTotalPhysicalMemorySize() / 1024 / 1024;
            double freeMemory = osBean.getFreePhysicalMemorySize() / 1024 / 1024;

            JsonObject message = new JsonObject();
            message.addProperty("type", "memory");
            message.addProperty("totalMemory", totalMemory);
            message.addProperty("freeMemory", freeMemory);

            // Publish the metric to PubNub
            pubNub.publish()
                    .channel("metrics_memory")
                    .message(message)
                    .async(new PNCallback<PNPublishResult>() {
                        @Override
                        public void onResponse(PNPublishResult result, PNStatus status) {
                            if (status.isError()) {
                                LOG.error(status.getErrorData().getInformation());
                            }
                        }
                    });

            // Wait for 1 second before sending the next metric
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
