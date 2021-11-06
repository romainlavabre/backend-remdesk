package com.replace.replace.api.dynamic.annotation;

import com.replace.replace.api.dynamic.api.TriggerResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Post {
    String route() default "";


    String[] roles() default {"*"};


    boolean authenticated() default true;


    String[] fields() default {"*"};


    Class< ? extends TriggerResolver< ? > >[] triggers() default {};


    Class< ? extends com.replace.replace.api.crud.Create< ? > > executor() default DefaultCreate.class;
}
