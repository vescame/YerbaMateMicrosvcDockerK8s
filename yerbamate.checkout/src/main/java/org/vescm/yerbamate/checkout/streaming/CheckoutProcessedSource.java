package org.vescm.yerbamate.checkout.streaming;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CheckoutProcessedSource {
    String OUTPUT = "checkout-processed-output";

    @Output(OUTPUT)
    MessageChannel output();
}
