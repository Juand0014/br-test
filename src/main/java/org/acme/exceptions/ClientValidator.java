package org.acme.exceptions;

import jakarta.ws.rs.BadRequestException;
import org.acme.dto.CreateClientDto;
import org.acme.dto.UpdateEntityDto;

public class ClientValidator {
    public static void validateClientData(Object client) {
        if (client instanceof CreateClientDto createClientDto) {
            if (createClientDto.firstName() == null || createClientDto.firstName().isBlank()) {
                throw new BadRequestException("First name is required");
            }
            if (createClientDto.email() == null || createClientDto.email().isBlank()) {
                throw new BadRequestException("Email is required");
            }
            if (createClientDto.countryCode() == null || createClientDto.countryCode().isBlank()) {
                throw new BadRequestException("Country code is required");
            }
        } else if (client instanceof UpdateEntityDto updateClientDto) {
            if (updateClientDto.email() == null || updateClientDto.email().isBlank()) {
                throw new BadRequestException("Email is required");
            }
            if (updateClientDto.countryCode() == null || updateClientDto.countryCode().isBlank()) {
                throw new BadRequestException("Country code is required");
            }
        }
    }
}
