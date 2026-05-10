package com.gameup.gameservice.repository;

import com.gameup.gameservice.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {

    List<Juego> findByCategoriaIdCategoria(Long idCategoria);

    List<Juego> findByActivoTrue();

    List<Juego> findByCategoriaIdCategoriaAndActivoTrue(Long idCategoria);

    List<Juego> findByNombreJuegoContainingIgnoreCase(String nombre);

    boolean existsByNombreJuego(String nombreJuego);

    Optional<Juego> findByNombreJuegoIgnoreCase(String nombreJuego);
}