package de.stekoe.idss.model.scale;

import de.stekoe.idss.IDGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Scale<T extends MeasurementValue> implements Serializable {

    private String id = IDGenerator.createId();
    private String name;
    private String description;
    private List<T> values = new ArrayList<T>();

    @Id
    @Column(name = "scale_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(targetEntity = MeasurementValue.class, cascade = CascadeType.ALL)
    public List<T> getValues() {
        final List<T> values = this.values;
        Collections.sort(values, new Comparator<MeasurementValue>() {
            @Override
            public int compare(MeasurementValue o1, MeasurementValue o2) {
                return Integer.compare(o1.getOrder(), o2.getOrder());
            }
        });
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    public boolean isValid(MeasurementValue value) {
        return values.contains(value);
    }
}