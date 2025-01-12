package org.acme.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.services.Interfaces.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.NoSuchElementException;

@ApplicationScoped
public class CountryService implements ICountryService {

    @Inject
    @RestClient
    IClientService clientService;

    @Override
    public String getDemonymByCountry(String countryCode) {
        try {
            String countryJson = clientService.getCountryByCode(countryCode);

            if (countryJson == null || countryJson.isEmpty()) {
                throw new NoSuchElementException("No country found for code: " + countryCode);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(countryJson);

            if (!rootNode.isArray()) {
                throw new IllegalArgumentException("Unexpected JSON format");
            }

            JsonNode countryNode = rootNode.get(0);
            JsonNode demonymsNode = countryNode.path("demonyms").path("eng").path("f");

            if (demonymsNode.isMissingNode()) {
                throw new NoSuchElementException("Demonym not found in response");
            }

            return demonymsNode.asText();
        } catch (Exception e) {
            return "Error retrieving demonym: " + e.getMessage();
        }
    }
}
