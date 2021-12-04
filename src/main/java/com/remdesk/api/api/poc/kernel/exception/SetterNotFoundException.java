package com.remdesk.api.api.poc.kernel.exception;

import com.remdesk.api.api.poc.annotation.Setter;

import java.lang.reflect.Field;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class SetterNotFoundException extends Throwable {

    public SetterNotFoundException( Field field ) {
        super( "Unable to resolve setter for field " + field.getName() + " in class " + field.getDeclaringClass().getSimpleName() + ". Define setter with field annotation " + Setter.class + " ( @Setter({\"\"}) )" );
    }
}
