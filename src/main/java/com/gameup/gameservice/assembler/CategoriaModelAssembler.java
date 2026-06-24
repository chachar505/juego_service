package com.gameup.gameservice.assembler;

import com.gameup.gameservice.controller.CategoriaController;
import com.gameup.gameservice.dto.CategoriaResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<CategoriaResponseDTO, EntityModel<CategoriaResponseDTO>> {

    @Override
    public EntityModel<CategoriaResponseDTO> toModel(CategoriaResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(CategoriaController.class).obtenerPorId(dto.getIdCategoria())).withSelfRel(),
                Link.of("/api/categorias").withRel("categorias")
        );
    }
}