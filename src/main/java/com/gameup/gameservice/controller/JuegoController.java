package com.gameup.gameservice.controller;

import com.gameup.gameservice.dto.*;
import com.gameup.gameservice.service.JuegoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/juegos")
@RequiredArgsConstructor
public class JuegoController {

    private final JuegoService juegoService;

    @GetMapping
    public ResponseEntity<List<JuegoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(juegoService.obtenerTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<JuegoResponseDTO>> listarActivos() {
        return ResponseEntity.ok(juegoService.obtenerActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JuegoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(juegoService.obtenerPorId(id));
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<JuegoResponseDTO>> obtenerPorCategoria(
            @PathVariable Long categoriaId) {

        return ResponseEntity.ok(juegoService.obtenerPorCategoria(categoriaId));
    }

    @PostMapping
    public ResponseEntity<JuegoResponseDTO> crear(
            @Valid @RequestBody JuegoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(juegoService.crearJuego(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JuegoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody JuegoRequestDTO dto) {

        return ResponseEntity.ok(juegoService.actualizarJuego(id, dto));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {

        juegoService.desactivarJuego(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        juegoService.eliminarJuego(id);

        return ResponseEntity.noContent().build();
    }
}