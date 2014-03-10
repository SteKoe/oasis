package de.stekoe.idss.model.criterion.scale.value;

import de.stekoe.idss.IDGenerator;
import de.stekoe.idss.model.criterion.scale.Scale;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MeasurementValue implements Serializable {

    private String id = IDGenerator.createId();
    private String value;
    private Scale scale;
    private int ordering;

    protected MeasurementValue() {
        // NOP
    }

    protected MeasurementValue(int ordering, String value) {
        this.ordering = ordering;
        this.value = value;
    }

    @Id
    @Column(name = "measurement_value_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Column(nullable = false)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    @ManyToOne(targetEntity = Scale.class)
    public Scale getScale() {
        return scale;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    @Override
    public int hashCode() {
        if(value == null) {
            return 0;
        } else {
            return value.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        MeasurementValue rhs = (MeasurementValue) obj;

        return new EqualsBuilder()
                .append(getValue(), rhs.value)
                .isEquals();
    }

}
