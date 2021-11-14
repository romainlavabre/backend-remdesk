package com.replace.replace.api.top.kernel.entry;

import com.replace.replace.api.request.Request;
import com.replace.replace.api.top.kernel.router.RouteHandler;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface DeleteEntry {
    void delete( Request request, Object entity, RouteHandler.Route route ) throws Throwable;
}