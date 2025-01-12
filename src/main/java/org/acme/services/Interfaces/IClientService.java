package org.acme.services.Interfaces;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "rest-countries-api")
@Path("/alpha")
public interface IClientService {

    @GET
    @Path("/{countryCode}")
    @Produces(MediaType.APPLICATION_JSON)
    String getCountryByCode(String countryCode);
}
