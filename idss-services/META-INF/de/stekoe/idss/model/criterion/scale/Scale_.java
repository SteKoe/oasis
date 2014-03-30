package de.stekoe.idss.model.criterion.scale;

import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.value.MeasurementValue;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-30T12:22:21.145+0200")
@StaticMetamodel(Scale.class)
public class Scale_ {
	public static volatile SingularAttribute<Scale, ScaleId> id;
	public static volatile ListAttribute<Scale, MeasurementValue> values;
	public static volatile SingularAttribute<Scale, SingleScaledCriterion> criterion;
	public static volatile SingularAttribute<Scale, String> name;
	public static volatile SingularAttribute<Scale, String> description;
}
