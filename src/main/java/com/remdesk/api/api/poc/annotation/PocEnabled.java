package com.remdesk.api.api.poc.annotation;

import com.remdesk.api.repository.DefaultRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( {ElementType.TYPE} )
public @interface PocEnabled {
    Class< ? extends DefaultRepository< ? > > repository();


    String suffixPlural() default "s";
}
