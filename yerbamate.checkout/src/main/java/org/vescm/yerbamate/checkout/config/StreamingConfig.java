package org.vescm.yerbamate.checkout.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.vescm.yerbamate.checkout.streaming.CheckoutCreatedSource;
import org.vescm.yerbamate.checkout.streaming.CheckoutProcessedSource;
import org.vescm.yerbamate.checkout.streaming.PaymentPaidSink;

@Configuration
@EnableBinding(value = {
        CheckoutCreatedSource.class,
        PaymentPaidSink.class,
        CheckoutProcessedSource.class
})
public class StreamingConfig { }
