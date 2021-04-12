package com.w2m.examen.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.w2m.examen.dto.HeroeDTO;
import com.w2m.examen.services.HeroeService;

import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/heroes")
public class HeroeController {

	private static final String OBJECT_ID_PATH = "/{objectId}";
	
	private static final String FIND_BY_NAME_PATH = "/findByName";

	@Autowired
	private HeroeService service;
	
	@PostMapping()
	public ResponseEntity<HeroeDTO> save(@RequestBody @Validated HeroeDTO request) {
		return status(CREATED)
				.body(service.save(request));
	}
	
	@PutMapping()
	public ResponseEntity<HeroeDTO> update(@RequestBody @Validated HeroeDTO request) {
		return status(OK)
				.body(service.update(request));
	}
	
	@DeleteMapping(OBJECT_ID_PATH)
	public ResponseEntity<Void> delete(@PathVariable Long objectId) {
		service.delete(objectId);
		return status(OK)
				.build();
	}
	
	@GetMapping
	public ResponseEntity<List<HeroeDTO>> findAll() {
		return status(OK)
				.body(service.findAll());
	}

	@GetMapping(OBJECT_ID_PATH)
	public ResponseEntity<HeroeDTO> findById(@PathVariable Long objectId) {
		return status(OK)
				.body(service.findById(objectId));
	}
	
	@GetMapping(FIND_BY_NAME_PATH)
	public ResponseEntity<List<HeroeDTO>> findByName(@RequestParam String searchText) {
		return status(OK)
				.body(service.findByName(searchText));
	}
}
