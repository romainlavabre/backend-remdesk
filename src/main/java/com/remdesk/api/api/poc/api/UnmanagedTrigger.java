package com.remdesk.api.api.poc.api;

import com.remdesk.api.api.request.Request;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface UnmanagedTrigger {

    void handle( Request request, Object resource );
}
