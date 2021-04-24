package org.vescm.yerbamate.checkout.streaming;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface PaymentPaidSink {
    String INPUT = "payment-verified-input";

    @Input(INPUT)
    SubscribableChannel input();
}
