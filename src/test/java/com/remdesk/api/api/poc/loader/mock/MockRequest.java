package com.remdesk.api.api.poc.loader.mock;

import com.remdesk.api.api.request.UploadedFile;

import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class MockRequest extends com.remdesk.api.api.request.MockRequest {

    private String uri;


    public void setUri( String uri ) {
        this.uri = uri;
    }


    @Override
    public String getUri() {
        return uri;
    }


    public void addValues( Map< String, Object > parameters, Map< String, UploadedFile > files ) {
        super.parameters.clear();
        super.files.clear();

        if ( parameters != null ) {
            super.parameters.putAll( parameters );
        }

        if ( files != null ) {
            super.files.putAll( files );
        }
    }
}
