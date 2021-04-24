package org.vescm.yerbamate.payment.streaming;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CheckoutCreatedSink {
    String INPUT = "checkout-created-input";

    @Input(INPUT)
    SubscribableChannel input();
}
