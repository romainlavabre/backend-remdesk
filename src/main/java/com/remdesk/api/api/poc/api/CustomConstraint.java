package com.remdesk.api.api.poc.api;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface CustomConstraint {

    void check( Object entity, Object newValue )
            throws RuntimeException;
}
