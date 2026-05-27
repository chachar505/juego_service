package com.gameup.gameservice.config;

import com.gameup.gameservice.model.Categoria;
import com.gameup.gameservice.model.Juego;
import com.gameup.gameservice.repository.CategoriaRepository;
import com.gameup.gameservice.repository.JuegoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final JuegoRepository juegoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (categoriaRepository.count() > 0 || juegoRepository.count() > 0) {
            log.info(">>> Categorías y juegos ya cargados. Se omite la inicialización.");
            return;
        }


        Categoria accion = new Categoria();
        accion.setNombre("Acción");
        accion.setDescripcion("Juegos de acción y aventura");

        Categoria rpg = new Categoria();
        rpg.setNombre("RPG");
        rpg.setDescripcion("Juegos de rol y estrategia");

        Categoria deportes = new Categoria();
        deportes.setNombre("Deportes");
        deportes.setDescripcion("Juegos deportivos y simulación");

        Categoria terror = new Categoria();
        terror.setNombre("Terror");
        terror.setDescripcion("Juegos de terror y suspenso");

        Categoria estrategia = new Categoria();
        estrategia.setNombre("Estrategia");
        estrategia.setDescripcion("Juegos de estrategia y puzzle");

        categoriaRepository.save(accion);
        categoriaRepository.save(rpg);
        categoriaRepository.save(deportes);
        categoriaRepository.save(terror);
        categoriaRepository.save(estrategia);

        log.info(">>> 5 categorías cargadas OK.");


        Juego juego1 = new Juego();
        juego1.setNombrejuego("God of War");
        juego1.setDescripcion("Aventura épica nórdica");
        juego1.setPrecio(new BigDecimal("29990.00"));
        juego1.setStock(50);
        juego1.setActivo(true);
        juego1.setCategoria(accion);

        Juego juego2 = new Juego();
        juego2.setNombrejuego("Elden Ring");
        juego2.setDescripcion("RPG de mundo abierto");
        juego2.setPrecio(new BigDecimal("39990.00"));
        juego2.setStock(30);
        juego2.setActivo(true);
        juego2.setCategoria(rpg);

        Juego juego3 = new Juego();
        juego3.setNombrejuego("FIFA 25");
        juego3.setDescripcion("Simulador de fútbol");
        juego3.setPrecio(new BigDecimal("34990.00"));
        juego3.setStock(100);
        juego3.setActivo(true);
        juego3.setCategoria(deportes);

        Juego juego4 = new Juego();
        juego4.setNombrejuego("Resident Evil 4");
        juego4.setDescripcion("Horror y acción en tercera persona");
        juego4.setPrecio(new BigDecimal("24990.00"));
        juego4.setStock(40);
        juego4.setActivo(true);
        juego4.setCategoria(terror);

        Juego juego5 = new Juego();
        juego5.setNombrejuego("Civilization VII");
        juego5.setDescripcion("Estrategia por turnos");
        juego5.setPrecio(new BigDecimal("44990.00"));
        juego5.setStock(20);
        juego5.setActivo(true);
        juego5.setCategoria(estrategia);

        juegoRepository.save(juego1);
        juegoRepository.save(juego2);
        juegoRepository.save(juego3);
        juegoRepository.save(juego4);
        juegoRepository.save(juego5);

        log.info(">>> 5 juegos cargados OK.");
    }
}