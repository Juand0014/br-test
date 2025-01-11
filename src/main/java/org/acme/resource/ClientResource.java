package org.acme.controller;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.ClientEntity;
import org.acme.services.ClientService;

import java.awt.*;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientController {

    @Inject
    ClientService clientService;

    @GET
    public Response getClients() {
        return Response.ok(clientService.getClients()).build();
    }

    @POST
    public Response createClient(ClientEntity clientEntity) {
        ClientEntity newClient = clientService.addClient(clientEntity);
        return Response.status(Response.Status.CREATED).entity(newClient).build();
    }
}
