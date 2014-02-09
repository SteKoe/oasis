package de.stekoe.idss.model.scale;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@PrimaryKeyJoinColumn(name = "ordinal_value_id", referencedColumnName = "measurement_value_id")
public class OrdinalValue extends MeasurementValue {
    private int rank;

    public OrdinalValue(int rank, String value) {
        this(rank, rank, value);
    }

    public OrdinalValue(int order, int rank, String value) {
        super(order, value);
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
