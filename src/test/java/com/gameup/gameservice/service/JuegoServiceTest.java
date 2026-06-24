package com.gameup.gameservice.service;

import com.gameup.gameservice.dto.JuegoRequestDTO;
import com.gameup.gameservice.dto.JuegoResponseDTO;
import com.gameup.gameservice.exception.BusinessException;
import com.gameup.gameservice.exception.ResourceNotFoundException;
import com.gameup.gameservice.model.Categoria;
import com.gameup.gameservice.model.Juego;
import com.gameup.gameservice.repository.CategoriaRepository;
import com.gameup.gameservice.repository.JuegoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del JuegoService")
class JuegoServiceTest {

    @Mock private JuegoRepository juegoRepository;
    @Mock private CategoriaRepository categoriaRepository;

    @InjectMocks
    private JuegoService juegoService;

    private Categoria categoriaMock;
    private Juego juegoMock;

    @BeforeEach
    void setUp() {
        categoriaMock = Categoria.builder()
                .idCategoria(1L).nombre("Acción").descripcion("desc").build();

        juegoMock = Juego.builder()
                .idJuego(1L).nombrejuego("God of War")
                .descripcion("Aventura épica").precio(new BigDecimal("29.99"))
                .stock(50).activo(true).categoria(categoriaMock).build();
    }

    @Test
    @DisplayName("Obtener todos los juegos retorna lista")
    void obtenerTodos_retornaLista() {
        when(juegoRepository.findAll()).thenReturn(List.of(juegoMock));
        List<JuegoResponseDTO> resultado = juegoService.obtenerTodos();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombreJuego()).isEqualTo("God of War");
    }

    @Test
    @DisplayName("Obtener por ID existente retorna juego")
    void obtenerPorId_existente_retornaJuego() {
        when(juegoRepository.findById(1L)).thenReturn(Optional.of(juegoMock));
        JuegoResponseDTO resultado = juegoService.obtenerPorId(1L);
        assertThat(resultado.getIdJuego()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Obtener por ID inexistente lanza excepción")
    void obtenerPorId_inexistente_lanzaExcepcion() {
        when(juegoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> juegoService.obtenerPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("Crear juego con nombre duplicado lanza excepción")
    void crearJuego_nombreDuplicado_lanzaExcepcion() {
        JuegoRequestDTO dto = JuegoRequestDTO.builder()
                .nombreJuego("God of War").precio(new BigDecimal("29.99"))
                .stock(10).categoriaId(1L).build();
        when(juegoRepository.existsByNombrejuego("God of War")).thenReturn(true);
        assertThatThrownBy(() -> juegoService.crearJuego(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("God of War");
    }

    @Test
    @DisplayName("Crear juego exitosamente")
    void crearJuego_exitoso() {
        JuegoRequestDTO dto = JuegoRequestDTO.builder()
                .nombreJuego("Nuevo Juego").descripcion("desc")
                .precio(new BigDecimal("19.99")).stock(10).categoriaId(1L).build();
        when(juegoRepository.existsByNombrejuego("Nuevo Juego")).thenReturn(false);
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaMock));
        when(juegoRepository.save(any(Juego.class))).thenReturn(juegoMock);
        JuegoResponseDTO resultado = juegoService.crearJuego(dto);
        assertThat(resultado).isNotNull();
        verify(juegoRepository).save(any(Juego.class));
    }

    @Test
    @DisplayName("Desactivar juego ya inactivo lanza excepción")
    void desactivarJuego_yaInactivo_lanzaExcepcion() {
        juegoMock.setActivo(false);
        when(juegoRepository.findById(1L)).thenReturn(Optional.of(juegoMock));
        assertThatThrownBy(() -> juegoService.desactivarJuego(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("inactivo");
    }
}