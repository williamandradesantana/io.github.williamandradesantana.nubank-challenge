package io.github.williamandradesantana.nubank_challenge.repositories;

import io.github.williamandradesantana.nubank_challenge.domain.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("""
        select distinct client
        from Client client
        left join fetch client.contacts
        order by client.fullName
    """)
    List<Client> allClientsWithContact();
}
