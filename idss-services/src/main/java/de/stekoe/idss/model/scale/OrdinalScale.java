package de.stekoe.idss.model.scale;

import de.stekoe.idss.model.scale.value.OrdinalValue;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@PrimaryKeyJoinColumn(name = "ordinal_scale_id", referencedColumnName = "scale_id")
public class OrdinalScale extends Scale<OrdinalValue> implements Serializable {

}
