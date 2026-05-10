package com.gameup.gameservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JuegoRequestDTO {

    @NotBlank
    @Size(max = 150)
    private String nombreJuego;

    @Size(max = 500)
    private String descripcion;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal precio;

    @Min(0)
    private Integer stock = 0;

    @NotNull
    @Positive
    private Long categoriaId;
}