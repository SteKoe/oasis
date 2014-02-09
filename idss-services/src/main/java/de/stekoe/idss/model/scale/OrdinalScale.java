package de.stekoe.idss.model.scale;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@PrimaryKeyJoinColumn(name = "ordinal_scale_id", referencedColumnName = "scale_id")
public class OrdinalScale extends Scale<OrdinalValue> implements Serializable {
    /**
     * @param val1
     * @param val2
     * @return true if val1 > val2, false otherwise
     */
    public boolean isGreater(OrdinalValue val1, OrdinalValue val2) {
        return val1.getRank() > val2.getRank();
    }

    /**
     * @param val1
     * @param val2
     * @return true if val1 < val2, false otherwise
     */
    public boolean isLower(OrdinalValue val1, OrdinalValue val2) {
        return val1.getRank() < val2.getRank();
    }

}
