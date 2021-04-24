package org.vescm.yerbamate.mailer.streaming;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CheckoutProcessedSink {
    String INPUT = "checkout-processed-input";

    @Input(INPUT)
    SubscribableChannel input();
}
