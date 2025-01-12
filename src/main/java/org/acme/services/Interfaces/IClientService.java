package org.acme.services.Interfaces;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "rest-countries-api")
@ApplicationScoped
public interface IClientService {

    @Path("/alpha/{countryCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    String getCountryByCode(String countryCode);
}
