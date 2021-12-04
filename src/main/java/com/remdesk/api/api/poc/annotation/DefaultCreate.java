package com.remdesk.api.api.poc.annotation;

import com.remdesk.api.api.crud.Create;
import com.remdesk.api.api.request.Request;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class DefaultCreate implements Create< Object > {

    @Override
    public void create( Request request, Object entity ) {

    }
}
