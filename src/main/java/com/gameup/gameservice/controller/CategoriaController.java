package com.gameup.gameservice.controller;

import com.gameup.gameservice.assembler.CategoriaModelAssembler;
import com.gameup.gameservice.dto.CategoriaRequestDTO;
import com.gameup.gameservice.dto.CategoriaResponseDTO;
import com.gameup.gameservice.service.CategoriaService;
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
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "Métodos del microservicio de categorías de juegos")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar todas las categorías")
    public CollectionModel<EntityModel<CategoriaResponseDTO>> listarTodas() {
        List<EntityModel<CategoriaResponseDTO>> categorias = categoriaService.obtenerTodas()
                .stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(categorias, Link.of("/api/categorias").withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener categoría por ID")
    public EntityModel<CategoriaResponseDTO> obtenerPorId(
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable Long id) {
        return assembler.toModel(categoriaService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear una categoría")
    public ResponseEntity<EntityModel<CategoriaResponseDTO>> crear(
            @Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assembler.toModel(categoriaService.crearCategoria(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una categoría")
    public EntityModel<CategoriaResponseDTO> actualizar(
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO dto) {
        return assembler.toModel(categoriaService.actualizarCategoria(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una categoría")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}