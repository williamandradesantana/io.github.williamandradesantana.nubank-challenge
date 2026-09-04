package io.github.williamandradesantana.nubank_challenge.domain.client;

import io.github.williamandradesantana.nubank_challenge.domain.contact.Contact;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_clients")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contact> contacts = new HashSet<>();

    public Client(String fullName) {
        this.fullName = fullName;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        contact.setClient(this);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }
}
