package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
public class ClientEntity extends PanacheEntity {

    @Column(nullable = false, length = 15)
    public String firstName;

    @Column(length = 10)
    public String middleName;

    @Column(nullable = false, length = 10)
    public String firstLastName;

    @Column(length = 10)
    public String secondLastName;

    @Column(nullable = false, length = 30)
    @Email(
        message = "must be a well-formed email address",
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    )
    public String email;

    @Column(nullable = false, length = 20)
    public String address;

    @Column(nullable = false, length = 10)
    public String phoneNumber;

    @Column(nullable = false, length = 2)
    public String countryCode;

    @Column(nullable = false, length = 10)
    public String demonym;
}
