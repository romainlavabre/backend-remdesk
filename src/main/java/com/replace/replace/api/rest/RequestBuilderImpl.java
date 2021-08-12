package com.replace.replace.api.rest;

import com.google.gson.Gson;
import kong.unirest.*;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class RequestBuilderImpl implements RequestBuilder {

    protected HttpRequestWithBody requestWithBody;
    protected RequestBodyEntity   requestBodyEntity;
    protected MultipartBody       multipartBody;


    @Override
    public RequestBuilder init( final String method, final String url ) {
        assert method != null && !method.isBlank() : "variable method should not be null or blank";
        assert url != null && !url.isBlank() : "variable url should not be null or blank";

        this.requestWithBody = Unirest.request( method, url );

        return this;
    }


    @Override
    public RequestBuilder routeParam( final String param, final String value ) {
        assert param != null && !param.isBlank() : "variable param should not be null or blank";
        assert value != null && !value.isBlank() : "variable value should not be null or blank";

        this.requestWithBody.routeParam( param, value );

        return this;
    }


    @Override
    public RequestBuilder queryString( final String key, final String value ) {
        assert key != null && !key.isBlank() : "variable key should not be null or blank";

        this.requestWithBody.queryString( key, value );

        return this;
    }


    @Override
    public RequestBuilder field( final String key, final String value ) {
        assert key != null && !key.isBlank() : "variable key should not be null or blank";

        if ( this.multipartBody == null ) {
            this.multipartBody = this.requestWithBody.field( key, value );
        } else {
            this.multipartBody.field( key, value );
        }

        return this;
    }


    @Override
    public RequestBuilder field( final String key, final File value ) {
        assert key != null && !key.isBlank() : "variable key should not be null or blank";

        if ( this.multipartBody == null ) {
            this.multipartBody = this.requestWithBody.field( key, value );
        } else {
            this.multipartBody.field( key, value );
        }

        return this;
    }


    @Override
    public RequestBuilder field( final String key, final Collection value ) {
        assert key != null && !key.isBlank() : "variable key should not be null or blank";

        if ( this.multipartBody == null ) {
            this.multipartBody = this.requestWithBody.field( key, value );
        } else {
            this.multipartBody.field( key, value );
        }

        return this;
    }


    @Override
    public RequestBuilder jsonBody( final Map< String, Object > json ) {
        this.requestBodyEntity = this.requestWithBody.body( (new Gson()).toJson( json ) );

        this.inContentType( RequestBuilder.JSON );

        return this;
    }


    @Override
    public RequestBuilder inContentType( final String contentType ) {
        assert contentType != null && !contentType.isBlank() : "variable contentType should not be null or blank";

        if ( this.multipartBody != null ) {
            this.multipartBody.contentType( contentType );
        } else if ( this.requestBodyEntity != null ) {
            this.requestBodyEntity.contentType( contentType );
        } else {
            this.requestBodyEntity.contentType( contentType );
        }

        return this;
    }


    @Override
    public RequestBuilder addHeader( final String header, final String value ) {
        assert header != null && !header.isBlank() : "variable header should not be null or blank";

        if ( this.multipartBody != null ) {
            this.multipartBody.header( header, value );
        } else if ( this.requestBodyEntity != null ) {
            this.requestBodyEntity.header( header, value );
        } else {
            this.requestWithBody.header( header, value );
        }

        return this;
    }


    @Override
    public RequestBuilder addHeader( final String header, final Integer value ) {
        assert header != null && !header.isBlank() : "variable header should not be null or blank";

        if ( this.multipartBody != null ) {
            this.multipartBody.header( header, value.toString() );
        } else if ( this.requestBodyEntity != null ) {
            this.requestBodyEntity.header( header, value.toString() );
        } else {
            this.requestWithBody.header( header, value.toString() );
        }

        return this;
    }


    @Override
    public Response buildAndSend() {
        HttpResponse< JsonNode > response = null;

        try {
            if ( this.multipartBody != null ) {
                response = this.multipartBody.asJson();
            } else if ( this.requestBodyEntity != null ) {
                response = this.requestBodyEntity.asJson();
            } else {
                response = this.requestWithBody.asJson();
            }
        } catch ( final Throwable ignored ) {
        }

        this.requestWithBody   = null;
        this.requestBodyEntity = null;
        this.multipartBody     = null;

        if ( response != null ) {
            return (new ResponseImpl()).supply( response );
        }

        return (new ResponseImpl()).supply();
    }
}
