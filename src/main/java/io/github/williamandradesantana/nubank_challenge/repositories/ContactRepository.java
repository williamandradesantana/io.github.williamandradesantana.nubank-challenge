package io.github.williamandradesantana.nubank_challenge.repositories;

import io.github.williamandradesantana.nubank_challenge.domain.contact.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    boolean existsByPhone(String phone);
}
