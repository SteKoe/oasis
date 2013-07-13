package de.stekoe.idss.model;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class Systemrole {
	private Long id;
	private String name;

	public Systemrole() {
	}
	
	public Systemrole(String name) {
		setName(name);
	}

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
}
