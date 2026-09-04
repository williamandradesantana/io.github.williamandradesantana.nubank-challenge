package io.github.williamandradesantana.nubank_challenge.domain.contact;

import io.github.williamandradesantana.nubank_challenge.domain.client.Client;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_contacts")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone", nullable = false, unique = true, length = 20)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public Contact(String phone, Client client) {
        this.phone = phone;
        this.client = client;
    }
}
