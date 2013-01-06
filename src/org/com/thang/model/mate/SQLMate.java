package org.com.thang.model.mate;

import org.com.thang.model.sql.Link;

public @interface SQLMate {

	public Link Mate() default Link.EQUAL;
	
}
