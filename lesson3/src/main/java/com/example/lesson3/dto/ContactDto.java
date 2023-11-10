package com.example.lesson3.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactDto {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 255)
    private String lastname;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$")
    private String phone;
}
