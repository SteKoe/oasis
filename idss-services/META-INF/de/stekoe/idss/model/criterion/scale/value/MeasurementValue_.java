package de.stekoe.idss.model.criterion.scale.value;

import de.stekoe.idss.model.criterion.scale.Scale;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-29T20:47:28.031+0100")
@StaticMetamodel(MeasurementValue.class)
public class MeasurementValue_ {
	public static volatile SingularAttribute<MeasurementValue, MeasurementValueId> id;
	public static volatile SingularAttribute<MeasurementValue, String> value;
	public static volatile SingularAttribute<MeasurementValue, Scale> scale;
	public static volatile SingularAttribute<MeasurementValue, Integer> ordering;
}
