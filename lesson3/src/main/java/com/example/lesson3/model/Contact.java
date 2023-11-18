package com.example.lesson3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private UUID id;
    private String name;
    private String lastname;
    private String email;
    private String phone;
}
