package com.gameup.gameservice.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaResponseDTO {

    private Long idCategoria;
    private String nombre;
    private String descripcion;
}