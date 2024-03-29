package com.remdesk.api.api.crud;

import com.remdesk.api.api.request.Request;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface Create< E > {
    void create( Request request, E entity );
}
