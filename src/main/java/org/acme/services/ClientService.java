package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.entity.ClientEntity;
import org.acme.repository.ClientRepository;

import java.util.List;

@ApplicationScoped
public class ClientService {

    @Inject
    ClientRepository clientRepository;

    public List<ClientEntity> getClients() {
        return clientRepository.listAll();
    }

    @Transactional
    public ClientEntity addClient(ClientEntity client) {
        if (client.id != null && clientRepository.findById(client.id) != null) {
            return clientRepository.getEntityManager().merge(client);
        } else {
            clientRepository.persistAndFlush(client);
            return client;
        }
    }
}
