package com.remdesk.api.api.poc.annotation;

import com.remdesk.api.api.crud.Update;
import com.remdesk.api.configuration.poc.TriggerIdentifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface UpdateTrigger {

    TriggerIdentifier id();


    String[] fields() default {"*"};


    Trigger[] triggers() default {};


    Class< ? extends Update< ? > > executor() default DefaultUpdate.class;
}
