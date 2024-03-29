package com.remdesk.api.util;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class Cast {

    public static Long getLong( Object object ) {
        if ( object != null ) {
            return Long.valueOf( object.toString() );
        }

        return null;
    }


    public static Integer getInt( Object object ) {
        if ( object != null ) {
            return Integer.valueOf( object.toString() );
        }

        return null;
    }


    public static Short getShort( Object object ) {
        if ( object != null ) {
            return Short.valueOf( object.toString() );
        }

        return null;
    }


    public static Byte getByte( Object object ) {
        if ( object != null ) {
            return Byte.valueOf( object.toString() );
        }

        return null;
    }


    public static Double getDouble( Object object ) {
        if ( object != null ) {
            return Double.valueOf( object.toString() );
        }

        return null;
    }
}
