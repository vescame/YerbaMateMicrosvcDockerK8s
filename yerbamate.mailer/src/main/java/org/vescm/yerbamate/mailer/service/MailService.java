package org.vescm.yerbamate.mailer.service;

import org.vescm.yerbamate.mailer.entity.MailEntity;

import java.util.Optional;

public interface MailService {
    Optional<MailEntity> create(String  checkoutCode);
    void sendMail(String checkoutCode);
}
