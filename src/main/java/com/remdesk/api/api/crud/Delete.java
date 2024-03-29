package com.remdesk.api.api.crud;

import com.remdesk.api.api.request.Request;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface Delete< E > {
    void delete( Request request, E entity );
}
