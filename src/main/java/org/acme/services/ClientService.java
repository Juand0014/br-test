package org.acme.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.acme.dto.CreateClientDto;
import org.acme.dto.UpdateEntityDto;
import org.acme.entity.ClientEntity;
import org.acme.exceptions.ClientValidator;
import org.acme.repository.ClientRepository;
import org.acme.services.Interfaces.IClientService;
import org.acme.services.Interfaces.ICountryService;

import java.util.List;

@ApplicationScoped
public class ClientService implements IClientService {

    @Inject
    ClientRepository clientRepository;

    @Inject
    ICountryService countryService;

    public List<ClientEntity> getClients() {
        return clientRepository.listAll();
    }

    public ClientEntity addClient(CreateClientDto client) {
        if(client == null) {
            throw new BadRequestException("client must not be null");
        }

        ClientValidator.validateClientData(client);

        ClientEntity clientEntity = new ClientEntity();

        clientEntity.setFirstName(client.firstName());
        clientEntity.setFirstLastName(client.firstLastName());
        clientEntity.setEmail(client.email());
        clientEntity.setAddress(client.address());
        clientEntity.setPhoneNumber(client.phoneNumber());
        clientEntity.setCountryCode(client.countryCode());

        clientEntity.demonym = countryService.getDemonymByCountryCode(clientEntity.countryCode);

        clientRepository.persist(clientEntity);

        return clientEntity;
    }

    public ClientEntity getClientById(Long id) {
        return clientRepository.findByIdOptional(id).orElseThrow(() -> new NotFoundException("Client not found"));
    }

    public ClientEntity updateClient(Long Id, UpdateEntityDto client) {
        if(client == null) {
            throw new BadRequestException("client must not be null");
        }

        ClientValidator.validateClientData(client);

        ClientEntity clientEntity = getClientById(Id);

        clientEntity.setAddress(client.address());
        clientEntity.setEmail(client.email());
        clientEntity.setPhoneNumber(client.phoneNumber());
        clientEntity.setCountryCode(client.countryCode());

        clientEntity.demonym = countryService.getDemonymByCountryCode(client.countryCode());

        clientRepository.persist(clientEntity);

        return clientEntity;
    }

    public void deleteClient(Long Id) {
        clientRepository.deleteById(Id);
    }
}
