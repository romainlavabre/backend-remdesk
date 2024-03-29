package com.remdesk.api.api.poc.annotation;

import com.remdesk.api.api.crud.Update;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Patch {
    String route() default "";


    String[] roles() default {"*"};


    boolean authenticated() default true;


    Trigger[] triggers() default {};


    Class< ? extends Update< ? > > executor() default DefaultUpdate.class;
}
