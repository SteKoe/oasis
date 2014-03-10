package de.stekoe.idss.model.criterion.scale.value;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@PrimaryKeyJoinColumn(name = "ordinal_value_id", referencedColumnName = "measurement_value_id")
public class OrdinalValue extends MeasurementValue {

    public OrdinalValue() {
        // NOP
    }

    public OrdinalValue(int rank, String value) {
        super(rank, value);
    }

    public int getRank() {
        return getOrdering();
    }

    public void setRank(int rank) {
        setOrdering(rank);
    }

    public boolean isGreaterThan(OrdinalValue aVal) {
        return getRank() > aVal.getRank();
    }

    public boolean isLowerThan(OrdinalValue aVal) {
        return this.getRank() < aVal.getRank();
    }
}
