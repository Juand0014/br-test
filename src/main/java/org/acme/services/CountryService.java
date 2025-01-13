package org.acme.services;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    ICountryCodeService countryCodeService;

    @Override
    public String getDemonymByCountryCode(String countryCode) {
        try {
            String country = countryCodeService.getCountryByCode(countryCode);

            if(country == null){
                throw new NoSuchElementException("No fue encontrado el pais con el siguiente codigo: " + countryCode);
            }

            ObjectMapper mapper = new ObjectMapper();

            JsonNode rootNode = mapper.readTree(country);

            JsonNode demonymsNode = rootNode.get(0).get("demonyms");

            return demonymsNode.get("eng").get("f").asText();

        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
