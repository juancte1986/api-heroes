package com.w2m.examen.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class HeroeDTO {
	
	private Long id;
	
	@NotNull
	@NotEmpty
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "HeroeDTO [id=" + id + ", name=" + name + "]";
	}
}
