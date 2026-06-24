package com.gameup.gameservice.assembler;

import com.gameup.gameservice.controller.JuegoController;
import com.gameup.gameservice.dto.JuegoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class JuegoModelAssembler implements RepresentationModelAssembler<JuegoResponseDTO, EntityModel<JuegoResponseDTO>> {

    @Override
    public EntityModel<JuegoResponseDTO> toModel(JuegoResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(JuegoController.class).obtenerPorId(dto.getIdJuego())).withSelfRel(),
                Link.of("/api/juegos").withRel("juegos"),
                Link.of("/api/juegos/activos").withRel("juegos-activos")
        );
    }
}