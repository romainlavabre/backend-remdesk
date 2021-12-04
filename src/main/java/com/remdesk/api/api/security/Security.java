package com.remdesk.api.api.security;

import java.util.Set;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface Security {
    long getId();

    String getUsername();

    Set< String > getRoles();

    boolean hasRole( String role );

    boolean hasUserConnected();

}
