package org.assignment.dto;

public record StudentDTO(
        Long id,
        String name,
        int age,
        int standards,
        String email
) { }
