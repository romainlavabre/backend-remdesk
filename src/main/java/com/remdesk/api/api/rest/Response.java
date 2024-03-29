package com.remdesk.api.api.rest;

import java.util.List;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface Response {

    int status();


    boolean isSuccess();


    boolean hasHeader( String header );


    String getHeader( String header );


    Map< String, String > getHeaders();


    Map< String, Object > getBodyAsMap();


    List< Object > getBodyAsList();
}
