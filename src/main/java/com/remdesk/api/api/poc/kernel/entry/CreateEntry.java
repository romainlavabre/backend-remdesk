package com.remdesk.api.api.poc.kernel.entry;

import com.remdesk.api.api.poc.kernel.router.RouteHandler;
import com.remdesk.api.api.request.Request;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface CreateEntry {
    void create( Request request, Object entity, RouteHandler.Route route )
            throws Throwable;
}
