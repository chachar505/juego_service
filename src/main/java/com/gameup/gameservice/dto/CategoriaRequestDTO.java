package com.gameup.gameservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequestDTO {

    @NotBlank(message = "El nombre de la categoria no puede estar vacio")
    @Size(max = 100)
    private String nombre;

    @Size(max = 255)
    private String descripcion;
}