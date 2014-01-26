 

package com.kamike.db.generic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 
 * @author brin
 *  hubinix@gmail.com
 */
@Target(ElementType.FIELD)   
@Retention(RetentionPolicy.RUNTIME)   
public @interface FieldName {   
    String value() default ""; 
}  