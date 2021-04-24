package org.vescm.yerbamate.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vescm.yerbamate.checkout.event.CheckoutCreatedEvent;
import org.vescm.yerbamate.payment.entity.PaymentEntity;
import org.vescm.yerbamate.payment.repository.PaymentRepository;
import org.vescm.yerbamate.payment.util.UUIDUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final UUIDUtil uuidUtil;

    @Override
    public Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent) {
        final PaymentEntity paymentEntity  = PaymentEntity.builder()
                .code(uuidUtil.createUUID().toString())
                .checkoutCode(String.valueOf(checkoutCreatedEvent.getCheckoutCode()))
                .build();

       if (verifyPayment(paymentEntity.getCheckoutCode())) {
           paymentRepository.save(paymentEntity);
           return Optional.of(paymentEntity);
       }

       return Optional.empty();
    }

    private boolean verifyPayment(String checkoutCode) {
        if ((checkoutCode.length() % 2) == 0) {
            return checkoutCode.indexOf("a") > 0 & !checkoutCode.contains("z");
        }
        return false;
    }
}
