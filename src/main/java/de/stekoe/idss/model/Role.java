package de.stekoe.idss.model;

import java.io.Serializable;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class Role implements Serializable {
    private Long id;
    private String roleName;

    /**
     * Construct.
     */
    public Role() {

    }

    /**
     * Construct.
     *
     * @param roleName The role name
     */
    public Role(String roleName) {
        setRoleName(roleName);
    }

    /**
     * @return The id of the Role
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the role.
     *
     * @param id Id of role
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return The role name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the role name.
     *
     * @param roleName The role name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
