package org.vescm.yerbamate.checkout.service;

import org.vescm.yerbamate.checkout.entity.CheckoutEntity;
import org.vescm.yerbamate.checkout.resource.checkout.CheckoutRequest;

import java.util.Optional;

public interface CheckoutService {
    Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);
    Optional<CheckoutEntity> updateStatus(String checkoutCode, String paymentCode);
}
