package com.gameup.gameservice.service;

import com.gameup.gameservice.dto.CategoriaRequestDTO;
import com.gameup.gameservice.dto.CategoriaResponseDTO;
import com.gameup.gameservice.exception.BusinessException;
import com.gameup.gameservice.exception.ResourceNotFoundException;
import com.gameup.gameservice.model.Categoria;
import com.gameup.gameservice.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<CategoriaResponseDTO> obtenerTodas() {

        return categoriaRepository.findAll()
                .stream()
                .map(this::mapearAResponseDTO)
                .toList();
    }

    public CategoriaResponseDTO obtenerPorId(Long id) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria no encontrada con id: " + id
                        )
                );

        return mapearAResponseDTO(categoria);
    }

    @Transactional
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO dto) {

        if (categoriaRepository.existsByNombre(dto.getNombre())) {

            throw new BusinessException(
                    "Ya existe una categoria con el nombre: " + dto.getNombre()
            );
        }

        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();

        Categoria guardada = categoriaRepository.save(categoria);

        return mapearAResponseDTO(guardada);
    }

    @Transactional
    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO dto) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria no encontrada con id: " + id
                        )
                );

        if (!categoria.getNombre().equalsIgnoreCase(dto.getNombre())
                && categoriaRepository.existsByNombre(dto.getNombre())) {

            throw new BusinessException(
                    "Ya existe una categoria con el nombre: " + dto.getNombre()
            );
        }

        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        Categoria actualizada = categoriaRepository.save(categoria);

        return mapearAResponseDTO(actualizada);
    }

    @Transactional
    public void eliminarCategoria(Long id) {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria no encontrada con id: " + id
                        )
                );

        if (categoria.getJuegos() != null
                && !categoria.getJuegos().isEmpty()) {

            throw new BusinessException(
                    "No se puede eliminar una categoria con juegos asociados"
            );
        }

        categoriaRepository.deleteById(id);
    }

    private CategoriaResponseDTO mapearAResponseDTO(Categoria categoria) {

        return CategoriaResponseDTO.builder()
                .idCategoria(categoria.getIdCategoria())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .build();
    }
}