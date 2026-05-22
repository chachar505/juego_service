package com.gameup.gameservice.service;

import com.gameup.gameservice.dto.JuegoRequestDTO;
import com.gameup.gameservice.dto.JuegoResponseDTO;
import com.gameup.gameservice.exception.BusinessException;
import com.gameup.gameservice.exception.ResourceNotFoundException;
import com.gameup.gameservice.model.Categoria;
import com.gameup.gameservice.model.Juego;
import com.gameup.gameservice.repository.CategoriaRepository;
import com.gameup.gameservice.repository.JuegoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JuegoService {

    private final JuegoRepository juegoRepository;
    private final CategoriaRepository categoriaRepository;

    public List<JuegoResponseDTO> obtenerTodos() {

        return juegoRepository.findAll()
                .stream()
                .map(this::mapearAResponseDTO)
                .toList();
    }

    public List<JuegoResponseDTO> obtenerActivos() {

        return juegoRepository.findByActivoTrue()
                .stream()
                .map(this::mapearAResponseDTO)
                .toList();
    }

    public JuegoResponseDTO obtenerPorId(Long id) {

        Juego juego = juegoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Juego no encontrado con id: " + id
                        )
                );

        return mapearAResponseDTO(juego);
    }

    public List<JuegoResponseDTO> obtenerPorCategoria(Long categoriaId) {

        if (!categoriaRepository.existsById(categoriaId)) {

            throw new ResourceNotFoundException(
                    "Categoria no encontrada con id: " + categoriaId
            );
        }

        return juegoRepository
                .findByCategoriaIdCategoriaAndActivoTrue(categoriaId)
                .stream()
                .map(this::mapearAResponseDTO)
                .toList();
    }

    @Transactional
    public JuegoResponseDTO crearJuego(JuegoRequestDTO dto) {

        if (juegoRepository.existsByNombrejuego(dto.getNombreJuego())) {

            throw new BusinessException(
                    "Ya existe un juego con el nombre: "
                            + dto.getNombreJuego()
            );
        }

        Categoria categoria = categoriaRepository
                .findById(dto.getCategoriaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria no encontrada con id: "
                                        + dto.getCategoriaId()
                        )
                );

        Juego juego = Juego.builder()
                .nombrejuego(dto.getNombreJuego())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .activo(true)
                .categoria(categoria)
                .build();

        Juego guardado = juegoRepository.save(juego);

        return mapearAResponseDTO(guardado);
    }

    @Transactional
    public JuegoResponseDTO actualizarJuego(Long id, JuegoRequestDTO dto) {

        Juego juego = juegoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Juego no encontrado con id: " + id
                        )
                );

        if (!juego.getNombrejuego().equalsIgnoreCase(dto.getNombreJuego())
                && juegoRepository.existsByNombrejuego(dto.getNombreJuego())) {

            throw new BusinessException(
                    "Ya existe un juego con el nombre: "
                            + dto.getNombreJuego()
            );
        }

        Categoria categoria = categoriaRepository
                .findById(dto.getCategoriaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria no encontrada con id: "
                                        + dto.getCategoriaId()
                        )
                );

        juego.setNombrejuego(dto.getNombreJuego());
        juego.setDescripcion(dto.getDescripcion());
        juego.setPrecio(dto.getPrecio());
        juego.setStock(dto.getStock());
        juego.setCategoria(categoria);

        Juego actualizado = juegoRepository.save(juego);

        return mapearAResponseDTO(actualizado);
    }

    @Transactional
    public void desactivarJuego(Long id) {

        Juego juego = juegoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Juego no encontrado con id: " + id
                        )
                );

        if (!juego.getActivo()) {

            throw new BusinessException(
                    "El juego ya se encuentra inactivo"
            );
        }

        juego.setActivo(false);

        juegoRepository.save(juego);
    }

    @Transactional
    public void eliminarJuego(Long id) {

        if (!juegoRepository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Juego no encontrado con id: " + id
            );
        }

        juegoRepository.deleteById(id);
    }

    private JuegoResponseDTO mapearAResponseDTO(Juego juego) {

        return JuegoResponseDTO.builder()
                .idJuego(juego.getIdJuego())
                .nombreJuego(juego.getNombrejuego())
                .descripcion(juego.getDescripcion())
                .precio(juego.getPrecio())
                .stock(juego.getStock())
                .activo(juego.getActivo())
                .categoriaId(juego.getCategoria().getIdCategoria())
                .categoriaNombre(juego.getCategoria().getNombre())
                .build();
    }
}