package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.ClientEntity;
import java.util.List;

@ApplicationScoped
public class ClientRepository implements PanacheRepository<ClientEntity> {

    public List<ClientEntity> findByCountry(String countryCode) {
        return find("countryCode", countryCode).list();
    }
}
