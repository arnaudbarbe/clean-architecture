package fr.arnaud.cleanarchitecture.infrastructure.delivery.controller.v1;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public abstract class LinkController {
	
    //HATEOAS relation name
    private static final String CREATE_RELATION = "create";
    private static final String UPDATE_RELATION = "update";
    private static final String GETALL_RELATION = "getAll";
    private static final String DELETE_RELATION = "delete";

	protected Link getSelfLink(@NotNull UUID id) {
		return WebMvcLinkBuilder.linkTo(this.getClass()).slash(id).withSelfRel();
	}

	protected Link getCreateLink() {
		return WebMvcLinkBuilder.linkTo(this.getClass()).withRel(CREATE_RELATION);
	}

	protected Link getUpdateLink(@NotNull UUID id) {
		return WebMvcLinkBuilder.linkTo(this.getClass()).slash(id).withRel(UPDATE_RELATION);
	}

	protected Link getDeleteLink(@NotNull UUID id) {
		return WebMvcLinkBuilder.linkTo(this.getClass()).slash(id).withRel(DELETE_RELATION);
	}

	protected Link getGetAllLink() {
		return WebMvcLinkBuilder.linkTo(this.getClass()).withRel(GETALL_RELATION);
	}
}
