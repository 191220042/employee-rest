/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.hateoas.examples;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.examples.projo.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Spring Web {@link RestController} used to generate a REST API.
 *
 * @author Greg Turnquist
 */
@RestController
@CrossOrigin
class PlayerController {

	private final PlayerRepository repository;

	PlayerController(PlayerRepository repository) {
		this.repository = repository;
	}

	/**
	 * Look up all players, and transform them into a REST collection resource.
	 * Then return them through Spring Web's {@link ResponseEntity} fluent API.
	 */
	@GetMapping("/players")
	ResponseEntity<CollectionModel<EntityModel<Player>>> findAll() {

		List<EntityModel<Player>> players = StreamSupport.stream(repository.findAll().spliterator(), false)
				.map(player -> new EntityModel<>(player, //
						linkTo(methodOn(PlayerController.class).findOne(player.getId())).withSelfRel(), //
						linkTo(methodOn(PlayerController.class).findAll()).withRel("players"))) //
				.collect(Collectors.toList());

		return ResponseEntity.ok( //
				new CollectionModel<>(players, //
						linkTo(methodOn(PlayerController.class).findAll()).withSelfRel()));
	}

	@PostMapping("/players")
	ResponseEntity<?> newPlayer(@RequestBody Player player) {

		try {

			Player savedPlayer = repository.save(player);

			EntityModel<Player> PlayerResource = new EntityModel<>(savedPlayer, //
					linkTo(methodOn(PlayerController.class).findOne(savedPlayer.getId())).withSelfRel());

			return ResponseEntity //
					.created(new URI(PlayerResource.getRequiredLink(IanaLinkRelations.SELF).getHref())) //
					.body(PlayerResource);
		} catch (URISyntaxException e) {
			return ResponseEntity.badRequest().body("Unable to create " + player);
		}
	}

	/**
	 * Look up a single {@link Player} and transform it into a REST resource. Then
	 * return it through Spring Web's {@link ResponseEntity} fluent API.
	 *
	 * @param id
	 */
	@GetMapping("/players/{id}")
	ResponseEntity<EntityModel<Player>> findOne(@PathVariable long id) {

		return repository.findById(id) //
				.map(player -> new EntityModel<>(player, //
						linkTo(methodOn(PlayerController.class).findOne(player.getId())).withSelfRel(), //
						linkTo(methodOn(PlayerController.class).findAll()).withRel("players"))) //
				.map(ResponseEntity::ok) //
				.orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Update existing player then return a Location header.
	 * 
	 * @param player
	 * @param id
	 * @return
	 */

	@PutMapping("/players/{id}")
	ResponseEntity<?> updatePlayer(@RequestBody Player player, @PathVariable long id) {

		Player playerToUpdate = player;
		playerToUpdate.setId(id);
		repository.save(playerToUpdate);

		Link newlyCreatedLink = linkTo(methodOn(PlayerController.class).findOne(id)).withSelfRel();

		try {
			return ResponseEntity.noContent().location(new URI(newlyCreatedLink.getHref())).build();
		} catch (URISyntaxException e) {
			return ResponseEntity.badRequest().body("Unable to update " + playerToUpdate);
		}
	}

	@DeleteMapping("/players/delete/{id}")
	void deletePlayerById(@PathVariable long id){
		repository.deleteById(id);
	}



}
