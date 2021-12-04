package com.remdesk.api.api.json.formatter;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface Formatter< T > {
    T format( T data );
}
