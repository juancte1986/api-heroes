package com.w2m.examen.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.w2m.examen.dto.HeroeDTO;
import com.w2m.examen.exceptions.NotFoundException;
import com.w2m.examen.mappers.HeroeMapper;
import com.w2m.examen.models.Heroe;
import com.w2m.examen.repositories.HeroeRepository;

import static java.lang.String.format;

@Service
@Transactional
public class HeroeService {

	 private final static Logger logger = LoggerFactory.getLogger(HeroeService.class);

	  @Autowired
	  private HeroeRepository repository;

	  @Autowired
	  private HeroeMapper mapper;

	  @Caching(evict = {
	    @CacheEvict(value = "AllHeroesByName", allEntries = true),
	    @CacheEvict(value = "allHeroes", allEntries = true)
	  })
	  public HeroeDTO save(HeroeDTO request) {
	    logger.debug("Entering the method HeroeService.save {}", request);
	    return mapper.toDTO(repository.save(mapper.toEntity(request)));
	  }

	  @Caching(put = {
	    @CachePut(value = "heroesById", key = "#request.id")
	  }, evict = {
	    @CacheEvict(value = "AllHeroesByName", allEntries = true),
	    @CacheEvict(value = "allHeroes", allEntries = true)
	  })
	  public HeroeDTO update(HeroeDTO request) {
	    logger.debug("Entering the method HeroeService.update {}", request);
	    final Long objectId = request.getId();
	    repository.findById(objectId).orElseThrow(() -> new NotFoundException(format("No se encontro el super heroe con id %s", objectId)));
	    return mapper.toDTO(repository.save(mapper.toEntity(request)));
	  }

	  @Caching(evict = {
	    @CacheEvict(value = "heroesById", key = "#objectId"),
	    @CacheEvict(value = "AllHeroesByName", allEntries = true),
	    @CacheEvict(value = "allHeroes", allEntries = true)
	  })
	  public void delete(Long objectId) {
	    logger.debug("Entering the method HeroeService.delete {}", objectId);
	    Heroe heroe = repository.findById(objectId).orElseThrow(() -> new NotFoundException(format("No se encontro el super heroe con id %s", objectId)));
	    repository.delete(heroe);
	  }

	  @Cacheable(value = "allHeroes")
	  @Transactional(readOnly = true)
	  public List < HeroeDTO > findAll() {
	    logger.debug("Entering the method HeroeService.findAll");
	    return mapper.toDTO(repository.findAll());
	  }

	  @Cacheable(value = "heroesById", key = "#objectId")
	  @Transactional(readOnly = true)
	  public HeroeDTO findById(Long objectId) {
	    logger.debug("Entering the method HeroeService.findById {}", objectId);
	    Heroe heroe = repository.findById(objectId).orElseThrow(() -> new NotFoundException(format("No se encontro el super heroe con id %s", objectId)));
	    return mapper.toDTO(heroe);
	  }

	  @Cacheable(value = "AllHeroesByName", key = "#searchText")
	  @Transactional(readOnly = true)
	  public List < HeroeDTO > findByName(String searchText) {
	    logger.debug("Entering the method HeroeService.findByName {}", searchText);
	    return mapper.toDTO(repository.findByNameContainingIgnoreCase(searchText));
	  }
}
