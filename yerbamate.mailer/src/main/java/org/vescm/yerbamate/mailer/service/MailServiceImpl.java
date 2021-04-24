package org.vescm.yerbamate.mailer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vescm.yerbamate.mailer.entity.MailEntity;
import org.vescm.yerbamate.mailer.repository.MailRepository;
import org.vescm.yerbamate.mailer.util.UUIDUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final MailRepository mailRepository;
    private final UUIDUtil uuidUtil;

    @Override
    public Optional<MailEntity> create(String checkoutCode) {
        log.info("MailService={}", checkoutCode);

        final MailEntity mail = MailEntity.builder()
                .code(uuidUtil.createUUID().toString())
                .checkoutCode(checkoutCode)
                .build();

        sendMail(checkoutCode);

        return Optional.of(mailRepository.save(mail));
    }

    @Override
    public void sendMail(String checkoutCode) {
        log.info("Mail sent to the owner of checkout code={}", checkoutCode);
    }
}
