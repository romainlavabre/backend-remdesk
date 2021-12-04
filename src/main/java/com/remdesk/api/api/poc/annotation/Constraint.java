package com.remdesk.api.api.poc.annotation;

import com.remdesk.api.api.poc.api.CustomConstraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
public @interface Constraint {
    Class< ? extends CustomConstraint >[] value();
}
