package org.acme.services.Interfaces;

import org.acme.dto.CreateClientDto;
import org.acme.dto.UpdateEntityDto;
import org.acme.entity.ClientEntity;

import java.util.List;

public interface IClientService {
    List<ClientEntity> getClients();

    ClientEntity addClient(CreateClientDto client);

    ClientEntity getClientById(Long id);

    ClientEntity updateClient(Long Id, UpdateEntityDto client);

    void deleteClient(Long Id);

    List<ClientEntity> getClientsByCountry(String countryCode);
}
