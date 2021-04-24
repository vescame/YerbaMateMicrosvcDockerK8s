package org.vescm.yerbamate.payment.service;

import org.vescm.yerbamate.checkout.event.CheckoutCreatedEvent;
import org.vescm.yerbamate.payment.entity.PaymentEntity;

import java.util.Optional;

public interface PaymentService {
    Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent);
}
