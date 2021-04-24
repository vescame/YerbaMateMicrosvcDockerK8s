package org.vescm.yerbamate.payment.streaming;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PaymentVerifiedSource {
    String OUTPUT = "payment-verified-output";

    @Output(OUTPUT)
    MessageChannel output();
}
