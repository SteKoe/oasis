package de.stekoe.idss.model.criterion.scale.value;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@PrimaryKeyJoinColumn(name = "ordinal_value_id", referencedColumnName = "measurement_value_id")
public class OrdinalValue extends MeasurementValue {
    private int rank;

    public OrdinalValue() {
        // NOP
    }

    public OrdinalValue(int rank, String value) {
        super(value);
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isGreaterThan(OrdinalValue aVal) {
        return this.rank > aVal.rank;
    }

    public boolean isLowerThan(OrdinalValue aVal) {
        return this.rank < aVal.rank;
    }
}
