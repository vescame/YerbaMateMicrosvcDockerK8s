package org.vescm.yerbamate.mailer.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import org.vescm.yerbamate.checkout.event.CheckoutProcessedEvent;
import org.vescm.yerbamate.mailer.service.MailService;
import org.vescm.yerbamate.mailer.streaming.CheckoutProcessedSink;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckoutProcessedListener {
    private final MailService mailService;

    @StreamListener(CheckoutProcessedSink.INPUT)
    public void handler(CheckoutProcessedEvent checkoutProcessedEvent) {
        log.info("CheckoutProcessedListener={}", checkoutProcessedEvent);
        mailService.create(String.valueOf(checkoutProcessedEvent.getCheckoutCode()));
    }
}
