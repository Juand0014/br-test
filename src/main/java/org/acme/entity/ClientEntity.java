package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.smallrye.common.constraint.*;
import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class ClientEntity extends PanacheEntity {

    @Column(nullable = false)
    @NotNull
    public String firstName;

    @Nullable
    public String middleName;


    @NotNull
    public String firstLastName;

    @Nullable
    public String secondLastName;

    @NotNull
    public String email;

    @NotNull
    @Column(unique = true)
    public String address;

    @NotNull
    public String phoneNumber;

    @NotNull
    public String countryCode;

    public String demonym;
}
