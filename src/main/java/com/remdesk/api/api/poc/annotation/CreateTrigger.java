package com.remdesk.api.api.poc.annotation;

import com.remdesk.api.api.crud.Create;
import com.remdesk.api.configuration.poc.TriggerIdentifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface CreateTrigger {

    TriggerIdentifier id();


    String[] fields() default {"*"};


    boolean setByArray() default true;


    Trigger[] triggers() default {};


    Class< ? extends Create< ? > > executor() default DefaultCreate.class;
}
