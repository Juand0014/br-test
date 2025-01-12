package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.acme.entity.ClientEntity;
import org.acme.services.ClientService;
import java.util.List;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.acme.dto.*;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {

    @Inject
    ClientService clientService;

    @GET
    public List<ClientEntity> getClients() {
        return clientService.getClients();
    }

    @POST
    @Transactional
    public Response createClient(@Valid CreateClientDto clientEntity) {
        ClientEntity newClient = clientService.addClient(clientEntity);
        return Response.status(Response.Status.CREATED).entity(newClient).build();
    }

    @GET
    @Path("/{id}")
    public Response getClientById(@PathParam("id") Long id) {
        var clientFound = clientService.getClientById(id);
        return Response.status(Response.Status.OK).entity(clientFound).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public ClientEntity updateClient(@PathParam("id") Long id, UpdateEntityDto clientEntity) {
        return clientService.updateClient(id, clientEntity);
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteClient(@PathParam("id") Long id) {
        clientService.deleteClient(id);
        return Response.noContent().build();
    }
}
