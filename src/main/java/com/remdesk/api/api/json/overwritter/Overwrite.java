package com.remdesk.api.api.json.overwritter;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface Overwrite< T > {

    Object overwrite( T data );
}
