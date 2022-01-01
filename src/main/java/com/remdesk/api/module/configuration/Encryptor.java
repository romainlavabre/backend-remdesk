package com.remdesk.api.module.configuration;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface Encryptor {
    byte[] encrypt( byte[] content );


    String encrypt( String content );


    byte[] decrypt( byte[] content );


    String decrypt( String content );
}
