package org.vescm.yerbamate.mailer.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.vescm.yerbamate.mailer.streaming.CheckoutProcessedSink;

@Configuration
@EnableBinding(value = {
        CheckoutProcessedSink.class
})
public class StreamingConfig { }
