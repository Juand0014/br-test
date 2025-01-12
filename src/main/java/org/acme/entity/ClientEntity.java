package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.smallrye.common.constraint.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    public String address;

    @NotNull
    public String phoneNumber;

    @NotNull
    @Size(min = 2, max = 10)
    public String countryCode;

    public String demonym;
}
