package de.stekoe.idss.model;

import de.stekoe.idss.IDGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "Document")
public class Document implements Serializable {

    private String id = IDGenerator.createId();
    private long size;
    private String name;
    private User user;
    private Date created = new Date();
    private String contentType;

    @Id
    @Column(name = "file_id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @NotNull
    @Basic(optional = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @ManyToOne()
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
