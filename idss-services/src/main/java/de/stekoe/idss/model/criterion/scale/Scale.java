package de.stekoe.idss.model.criterion.scale;

import de.stekoe.idss.IDGenerator;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.value.MeasurementValue;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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
    private SingleScaledCriterion criterion;

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

    @OrderBy(value = "ordering")
    @OneToMany(targetEntity = MeasurementValue.class, cascade = CascadeType.ALL)
    public List<T> getValues() {
        return this.values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    public boolean isValid(MeasurementValue value) {
        return values.contains(value);
    }

    @OneToOne(targetEntity = SingleScaledCriterion.class)
    public SingleScaledCriterion getCriterion() {
        return criterion;
    }

    public void setCriterion(SingleScaledCriterion criterion) {
        this.criterion = criterion;
    }
}
