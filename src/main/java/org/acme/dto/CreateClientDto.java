package org.acme.dto;

import jakarta.validation.constraints.*;

public record CreateClientDto(
        @NotBlank
        @Size(max = 15)
        String firstName,

        @Size(max = 10)
        String middleName,

        @NotBlank
        @Size(max = 10)
        String firstLastName,

        @Size(max = 10)
        String secondLastName,

        @NotBlank(message = "Email is required")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "must be a well-formed email address"
        )
        @Email
        @Size(max = 20)
        String email,

        @NotBlank
        @Size(max = 20)
        String address,

        @NotBlank
        @Size(max = 10)
        String phoneNumber,

        @NotBlank
        @Size(max = 5)
        String countryCode
) {
}
