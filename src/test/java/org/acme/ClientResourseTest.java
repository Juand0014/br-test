package org.acme;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.acme.dto.*;
import org.acme.entity.ClientEntity;
import org.acme.services.ClientService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import jakarta.ws.rs.NotFoundException;


@QuarkusTest
public class ClientResourseTest {

    @InjectMock
    ClientService clientService;

    @BeforeEach
    void setUp() {
        Mockito.reset(clientService);
    }

    @Test
    public void testGetAllClients(){
        given().when()
                .get("/clients")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testCreateClient_Success() {
        CreateClientDto dto = new CreateClientDto("Juan", "David", "Matos", null,"Juanmatos4433@gmail.com","dddddd", "123456789", "US");

        when(clientService.addClient(any(CreateClientDto.class))).thenReturn(null);

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/clients")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateClient_Success() {

        UpdateEntityDto dto = new UpdateEntityDto("Juanmatos@gmail.com", "SAN JUAN", "123456789", "DO");

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setEmail("Juanmatos@gmail.com");
        clientEntity.setAddress("SAN JUAN");
        clientEntity.setPhoneNumber("123456789");
        clientEntity.setCountryCode("DO");

        when(clientService.updateClient(eq(451L), any(UpdateEntityDto.class))).thenReturn(clientEntity);

        given()
        .contentType(ContentType.JSON)
                .body(dto)
                .pathParam("id", 451)
                .when().put("/clients/{id}")
                .then()
                .statusCode(200)
                .assertThat().body("countryCode", equalTo("DO"));
    }

    @Test
    public void testGetClientById_NotFound() {
        when(clientService.getClientById(anyLong())).thenThrow(new NotFoundException("Client not found"));

        given()
                .pathParam("id", 1234567890L)
                .when().get("/clients/{id}")
                .then()
                .statusCode(404);
    }

}
