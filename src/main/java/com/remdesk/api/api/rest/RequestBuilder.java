package com.remdesk.api.api.rest;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface RequestBuilder {
    String JSON             = "application/json";
    String FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    String FORM_DATA        = "multipart/form-data";
    String GET              = "GET";
    String POST             = "POST";
    String PUT              = "PUT";
    String PATCH            = "PATCH";
    String DELETE           = "DELETE";
    String OPTIONS          = "OPTIONS";


    RequestBuilder init( String method, String url );


    RequestBuilder routeParam( String param, String value );


    RequestBuilder queryString( String key, String value );


    RequestBuilder field( String key, String value );


    RequestBuilder field( String key, File value );


    RequestBuilder field( String key, Collection value );


    RequestBuilder jsonBody( Map< String, Object > json );


    RequestBuilder jsonBody( List< Object > json );


    RequestBuilder inContentType( String contentType );


    RequestBuilder addHeader( String header, String value );


    RequestBuilder addHeader( String header, Integer value );


    Response buildAndSend();
}
