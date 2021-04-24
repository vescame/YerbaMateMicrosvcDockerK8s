package org.vescm.yerbamate.payment.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.vescm.yerbamate.payment.streaming.CheckoutCreatedSink;
import org.vescm.yerbamate.payment.streaming.PaymentVerifiedSource;

@Configuration
@EnableBinding(value = {
        PaymentVerifiedSource.class,
        CheckoutCreatedSink.class
})
public class StreamingConfig { }
