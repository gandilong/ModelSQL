package org.com.thang.model.mate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.com.thang.model.sql.OrderBy;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {

	public OrderBy order() default OrderBy.DOWN;
	
}
