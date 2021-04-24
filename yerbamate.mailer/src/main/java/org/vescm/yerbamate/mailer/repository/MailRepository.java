package org.vescm.yerbamate.mailer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vescm.yerbamate.mailer.entity.MailEntity;

@Repository
public interface MailRepository extends JpaRepository<MailEntity, Long> {
}
