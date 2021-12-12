package com.remdesk.api.module.configuration;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class OsResolver {
    public static boolean isUnix() {
        return System.getProperty( "os.name" ).startsWith( "Linux" );
    }
}
