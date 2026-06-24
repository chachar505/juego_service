package com.gameup.gameservice.controller;

import com.gameup.gameservice.assembler.JuegoModelAssembler;
import com.gameup.gameservice.dto.JuegoRequestDTO;
import com.gameup.gameservice.dto.JuegoResponseDTO;
import com.gameup.gameservice.service.JuegoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/juegos")
@RequiredArgsConstructor
@Tag(name = "Juegos", description = "Métodos del microservicio de juegos")
public class JuegoController {

    private final JuegoService juegoService;
    private final JuegoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todos los juegos")
    public CollectionModel<EntityModel<JuegoResponseDTO>> listarTodos() {
        List<EntityModel<JuegoResponseDTO>> juegos = juegoService.obtenerTodos()
                .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(juegos, Link.of("/api/juegos").withSelfRel());
    }

    @GetMapping(value = "/activos", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar juegos activos")
    public CollectionModel<EntityModel<JuegoResponseDTO>> listarActivos() {
        List<EntityModel<JuegoResponseDTO>> juegos = juegoService.obtenerActivos()
                .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(juegos, Link.of("/api/juegos/activos").withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener juego por ID")
    public EntityModel<JuegoResponseDTO> obtenerPorId(
            @Parameter(description = "ID del juego", required = true)
            @PathVariable Long id) {
        return assembler.toModel(juegoService.obtenerPorId(id));
    }

    @GetMapping(value = "/categoria/{categoriaId}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar juegos por categoría")
    public CollectionModel<EntityModel<JuegoResponseDTO>> obtenerPorCategoria(
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable Long categoriaId) {
        List<EntityModel<JuegoResponseDTO>> juegos = juegoService.obtenerPorCategoria(categoriaId)
                .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(juegos, Link.of("/api/juegos/categoria/" + categoriaId).withSelfRel());
    }

    @PostMapping
    @Operation(summary = "Crear un juego")
    public ResponseEntity<EntityModel<JuegoResponseDTO>> crear(
            @Valid @RequestBody JuegoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assembler.toModel(juegoService.crearJuego(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un juego")
    public EntityModel<JuegoResponseDTO> actualizar(
            @Parameter(description = "ID del juego", required = true)
            @PathVariable Long id,
            @Valid @RequestBody JuegoRequestDTO dto) {
        return assembler.toModel(juegoService.actualizarJuego(id, dto));
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar un juego")
    public ResponseEntity<Void> desactivar(
            @Parameter(description = "ID del juego", required = true)
            @PathVariable Long id) {
        juegoService.desactivarJuego(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un juego")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del juego", required = true)
            @PathVariable Long id) {
        juegoService.eliminarJuego(id);
        return ResponseEntity.noContent().build();
    }
}