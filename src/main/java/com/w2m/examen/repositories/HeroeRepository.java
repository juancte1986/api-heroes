package com.w2m.examen.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.w2m.examen.models.Heroe;

public interface HeroeRepository extends JpaRepository<Heroe, Long>{

	List<Heroe> findByNameContainingIgnoreCase(String name);
}
