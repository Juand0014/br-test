package org.acme.resource;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.ClientEntity;
import org.acme.services.ClientService;
import org.acme.services.Interfaces.ICountryService;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {

    @Inject
    ClientService clientService;

    @Inject
    ICountryService countryService;

    @GET
    public Response getClients() {
        return Response.ok(clientService.getClients()).build();
    }

    @POST
    public Response createClient(@Valid ClientEntity clientEntity) {
        clientEntity.demonym = countryService.getDemonymByCountry(clientEntity.countryCode);
        ClientEntity newClient = clientService.addClient(clientEntity);
        return Response.status(Response.Status.CREATED).entity(newClient).build();
    }
}
