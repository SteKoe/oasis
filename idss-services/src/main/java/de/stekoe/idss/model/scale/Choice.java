package de.stekoe.idss.model.scale;

import de.stekoe.idss.IDGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
public class Choice implements Serializable {
    private String id = IDGenerator.createId();
    private Scale scale;
    private MeasurementValue measurementValue;

    @Id
    @Column(name = "choice_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @OneToOne(targetEntity = Scale.class)
    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    @OneToOne(targetEntity = MeasurementValue.class)
    public MeasurementValue getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(MeasurementValue measurementValue) {
        this.measurementValue = measurementValue;
    }
}
