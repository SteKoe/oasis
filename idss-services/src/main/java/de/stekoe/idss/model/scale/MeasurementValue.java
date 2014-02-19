package de.stekoe.idss.model.scale;

import de.stekoe.idss.IDGenerator;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MeasurementValue implements Serializable {

    private String id = IDGenerator.createId();
    private String value;
    private int order;

    protected MeasurementValue(String value) {
        this.order = 0;
        this.value = value;
    }

    protected MeasurementValue(int order, String value) {
        this.order = order;
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

    @Column(name = "ordering")
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
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
