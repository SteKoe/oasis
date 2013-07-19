package de.stekoe.idss.model;

import java.io.Serializable;

import javax.persistence.Transient;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class Systemrole implements Serializable {

    /** @see Roles#USER */
    @Transient
    public static final String USER = Roles.USER;
    @Transient
    /** @see Roles#ADMIN */
    public static final String ADMIN = Roles.ADMIN;

    private Long id;
    private String name;

    /**
     * Construct.
     */
    public Systemrole() {
    }
    

    /**
     * @return id of the Systemrole
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id of the Systemrole
     * 
     * @param id of Systemrole
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return name of Systemrole
     */
    public String getName() {
        return name;
    }

    /**
     * Set Systemrole name
     * 
     * @param name of Systemrole
     */
    public void setName(String name) {
        this.name = name;
    }
}
