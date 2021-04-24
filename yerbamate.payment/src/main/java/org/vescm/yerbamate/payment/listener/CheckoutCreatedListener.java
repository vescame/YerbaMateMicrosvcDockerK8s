package org.vescm.yerbamate.payment.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.vescm.yerbamate.checkout.event.CheckoutCreatedEvent;
import org.vescm.yerbamate.payment.entity.PaymentEntity;
import org.vescm.yerbamate.payment.event.PaymentCreatedEvent;
import org.vescm.yerbamate.payment.service.PaymentService;
import org.vescm.yerbamate.payment.streaming.CheckoutCreatedSink;
import org.vescm.yerbamate.payment.streaming.PaymentVerifiedSource;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckoutCreatedListener {
    private final PaymentVerifiedSource paymentVerifiedSource;
    private final PaymentService paymentService;

    @StreamListener(CheckoutCreatedSink.INPUT)
    public void handler(CheckoutCreatedEvent checkoutCreatedEvent) {
        log.info("CheckoutCreatedListener={}", checkoutCreatedEvent);

        final PaymentEntity paymentEntity = paymentService.create(checkoutCreatedEvent).orElseThrow();

        final PaymentCreatedEvent paymentCreatedEvent = PaymentCreatedEvent.newBuilder()
                .setCheckoutCode(paymentEntity.getCheckoutCode())
                .setPaymentCode(paymentEntity.getCode())
                .build();

        paymentVerifiedSource.output().send(MessageBuilder.withPayload(paymentCreatedEvent).build());
    }
}
