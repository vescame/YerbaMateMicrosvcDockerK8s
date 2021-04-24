package org.vescm.yerbamate.checkout.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.vescm.yerbamate.checkout.entity.CheckoutEntity;
import org.vescm.yerbamate.checkout.event.CheckoutProcessedEvent;
import org.vescm.yerbamate.checkout.service.CheckoutService;
import org.vescm.yerbamate.checkout.streaming.CheckoutProcessedSource;
import org.vescm.yerbamate.checkout.streaming.PaymentPaidSink;
import org.vescm.yerbamate.payment.event.PaymentCreatedEvent;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentVerifiedListener {
    private final CheckoutService checkoutService;
    private final CheckoutProcessedSource checkoutProcessedSource;

    @StreamListener(PaymentPaidSink.INPUT)
    public void handler(PaymentCreatedEvent paymentCreatedEvent) {
        log.info("PaymentVerifiedListener={}", paymentCreatedEvent);

        CheckoutEntity checkoutEntity = checkoutService
                .updateStatus(
                        String.valueOf(paymentCreatedEvent.getCheckoutCode()),
                        String.valueOf(paymentCreatedEvent.getPaymentCode())).orElseThrow();

        CheckoutProcessedEvent checkoutProcessedEvent = CheckoutProcessedEvent.newBuilder()
                .setCheckoutCode(checkoutEntity.getCode())
                .build();

        checkoutProcessedSource.output()
                .send(MessageBuilder.withPayload(checkoutProcessedEvent).build());
    }
}
