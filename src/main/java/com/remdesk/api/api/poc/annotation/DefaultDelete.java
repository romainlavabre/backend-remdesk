package com.remdesk.api.api.poc.annotation;

import com.remdesk.api.api.crud.Delete;
import com.remdesk.api.api.request.Request;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class DefaultDelete implements Delete< Object > {


    @Override
    public void delete( Request request, Object entity ) {

    }
}
