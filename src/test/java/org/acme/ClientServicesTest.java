package org.acme;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.acme.dto.CreateClientDto;
import org.acme.dto.UpdateEntityDto;
import org.acme.entity.ClientEntity;
import org.acme.repository.ClientRepository;
import org.acme.services.Interfaces.IClientService;
import org.acme.services.Interfaces.ICountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.wildfly.common.Assert.assertNotNull;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
public class ClientServicesTest {

    @Inject
    IClientService clientService;

    @InjectMock
    ClientRepository clientRepository;

    @InjectMock
    ICountryService countryService;

    @InjectMock
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
        Mockito.reset(clientRepository, countryService, entityManager);

        doNothing().when(clientRepository).persist(any(ClientEntity.class));

        when(countryService.getDemonymByCountryCode(anyString())).thenReturn("American");

        when(clientRepository.getEntityManager()).thenReturn(entityManager);
        when(entityManager.merge(any(ClientEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));  // ✅ Return the same entity
    }

    @Test
    void testAddClient_Success() {
        CreateClientDto dto = new CreateClientDto(
                "Juan", "David", "Matos", null,
                "Juanmatos4", "dddddd", "123456789", "DO"
        );

        var demonym = countryService.getDemonymByCountryCode(dto.countryCode());

        ClientEntity mockClient = new ClientEntity();
        mockClient.setFirstName(dto.firstName());
        mockClient.setDemonym(demonym);

        doNothing().when(clientRepository).persist(any(ClientEntity.class));
        when(clientRepository.getEntityManager().merge(any(ClientEntity.class))).thenReturn(mockClient);

        ClientEntity result = clientService.addClient(dto);

        assertNotNull(result);
        assertEquals(demonym, result.getDemonym());
        assertEquals("Juan", result.getFirstName());
    }

    @Test
    void testAddClient_NullDto_ThrowsException() {
        assertThrows(BadRequestException.class, () -> clientService.addClient(null));
    }

    @Test
    void testGetClients() {
        when(clientRepository.listAll()).thenReturn(List.of(new ClientEntity()));

        List<ClientEntity> result = clientService.getClients();

        System.out.println(result.size());

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetClientById_Success() {
        ClientEntity client = new ClientEntity();
        client.id = 1L;
        client.setFirstName("Maria");

        when(clientRepository.findByIdOptional(1L)).thenReturn(java.util.Optional.of(client));

        ClientEntity result = clientService.getClientById(1L);

        assertNotNull(result);
        assertEquals("Maria", result.getFirstName());
    }

    @Test
    void testGetClientById_NotFound() {
        when(clientRepository.findByIdOptional(99L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.getClientById(99L));
    }

    @Test
    void testUpdateClient_Success() {
        UpdateEntityDto dto = new UpdateEntityDto("newemail@gmail.com", "New Address", "987654321", "FR");
        ClientEntity existingClient = new ClientEntity();
        existingClient.id = 1L;
        existingClient.setEmail("oldemail@gmail.com");

        when(clientRepository.findByIdOptional(1L)).thenReturn(java.util.Optional.of(existingClient));
        when(countryService.getDemonymByCountryCode("FR")).thenReturn("French");

        ClientEntity updatedClient = clientService.updateClient(1L, dto);

        assertNotNull(updatedClient);
        assertEquals("newemail@gmail.com", updatedClient.getEmail());
        assertEquals("French", updatedClient.getDemonym());
        verify(clientRepository, times(1)).persist(any(ClientEntity.class));
    }

    @Test
    void testUpdateClient_NotFound() {
        UpdateEntityDto dto = new UpdateEntityDto("newemail@gmail.com", "New Address", "987654321", "FR");

        when(clientRepository.findByIdOptional(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.updateClient(1L, dto));
    }

    @Test
    void testDeleteClient_Success() {
        doNothing().when(clientRepository).deleteById(1L);

        clientService.deleteClient(1L);

        verify(clientRepository, times(2)).deleteById(1L);
    }

    @Test
    void testAddClient_InvalidEmail_ShouldThrowValidationError() {
        CreateClientDto dto = new CreateClientDto(
                "Juan", "David", "Matos", null,
                "invalid-email",
                "Street XYZ", "123456789", "US"
        );

        when(countryService.getDemonymByCountryCode("US")).thenReturn("American");

        assertThrows(ConstraintViolationException.class, () -> clientService.addClient(dto));
    }
}
