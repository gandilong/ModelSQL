package org.com.thang.model.mate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Foreign {

    public String foreignKey();
	
	public Class<?> foreignClass();
	
}
