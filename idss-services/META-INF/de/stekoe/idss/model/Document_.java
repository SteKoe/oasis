package de.stekoe.idss.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-29T20:47:28.041+0100")
@StaticMetamodel(Document.class)
public class Document_ {
	public static volatile SingularAttribute<Document, DocumentId> id;
	public static volatile SingularAttribute<Document, Long> size;
	public static volatile SingularAttribute<Document, String> name;
	public static volatile SingularAttribute<Document, User> user;
	public static volatile SingularAttribute<Document, Date> created;
	public static volatile SingularAttribute<Document, String> contentType;
}
