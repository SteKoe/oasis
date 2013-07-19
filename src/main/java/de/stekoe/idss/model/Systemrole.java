package de.stekoe.idss.model;

import java.io.Serializable;

import javax.persistence.Transient;

import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("serial")
public class Systemrole implements Serializable {

    @Transient
    public static final String USER = "user";
    @Transient
    public static final String ADMIN = "administrator";

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
