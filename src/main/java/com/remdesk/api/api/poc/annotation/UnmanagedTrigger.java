package com.remdesk.api.api.poc.annotation;

import com.remdesk.api.api.crud.Create;
import com.remdesk.api.api.crud.Delete;
import com.remdesk.api.api.crud.Update;
import com.remdesk.api.configuration.poc.TriggerIdentifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface UnmanagedTrigger {

    TriggerIdentifier id();


    Trigger[] triggers() default {};


    Class< ? extends Create< ? > > createExecutor() default DefaultCreate.class;


    Class< ? extends Update< ? > > updateExecutor() default DefaultUpdate.class;


    Class< ? extends Delete< ? > > deleteExecutor() default DefaultDelete.class;


    Class< ? extends com.remdesk.api.api.poc.api.UnmanagedTrigger > unmanagedExecutor() default DefaultUnmanagedExecutor.class;
}
