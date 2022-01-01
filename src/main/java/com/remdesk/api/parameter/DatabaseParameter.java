package com.remdesk.api.parameter;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface DatabaseParameter {
    String PREFIX         = "database_";
    String HOST           = PREFIX + "host";
    String SOFTWARE       = PREFIX + "software";
    String USERNAME       = PREFIX + "username";
    String PASSWORD       = PREFIX + "password";
    String PORT           = PREFIX + "port";
    String ENCRYPTION_KEY = PREFIX + "encryption_key";
}
