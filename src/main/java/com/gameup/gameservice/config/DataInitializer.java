package com.gameup.gameservice.config;

import com.gameup.gameservice.model.Categoria;
import com.gameup.gameservice.model.Juego;
import com.gameup.gameservice.repository.CategoriaRepository;
import com.gameup.gameservice.repository.JuegoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final JuegoRepository juegoRepository;

    @Override
    public void run(String... args) {
        if (categoriaRepository.count() > 0 || juegoRepository.count() > 0) {
            log.info(">>> Categorías y juegos ya cargados. Se omite la inicialización.");
            return;
        }

        Faker faker = new Faker();

        List<Categoria> categorias = List.of(
                Categoria.builder().nombre("Acción").descripcion("Juegos de acción y aventura").build(),
                Categoria.builder().nombre("RPG").descripcion("Juegos de rol y estrategia").build(),
                Categoria.builder().nombre("Deportes").descripcion("Juegos deportivos y simulación").build(),
                Categoria.builder().nombre("Terror").descripcion("Juegos de terror y suspenso").build(),
                Categoria.builder().nombre("Estrategia").descripcion("Juegos de estrategia y puzzle").build()
        );

        List<Categoria> categoriasGuardadas = categoriaRepository.saveAll(categorias);
        log.info(">>> 5 categorías cargadas OK.");

        for (int i = 0; i < 10; i++) {
            Categoria categoriaAleatoria = categoriasGuardadas.get(
                    faker.number().numberBetween(0, categoriasGuardadas.size())
            );

            Juego juego = Juego.builder()
                    .nombrejuego(faker.videoGame().title() + " " + faker.number().numberBetween(1, 100))
                    .descripcion(faker.lorem().sentence(8))
                    .precio(BigDecimal.valueOf(faker.number().randomDouble(2, 5, 60))
                            .setScale(2, RoundingMode.HALF_UP))
                    .stock(faker.number().numberBetween(0, 100))
                    .activo(true)
                    .categoria(categoriaAleatoria)
                    .build();

            juegoRepository.save(juego);
        }

        log.info(">>> 10 juegos generados con DataFaker OK.");
    }
}