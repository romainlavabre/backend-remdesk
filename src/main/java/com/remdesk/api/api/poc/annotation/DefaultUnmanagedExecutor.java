package com.remdesk.api.api.poc.annotation;

import com.remdesk.api.api.poc.api.UnmanagedTrigger;
import com.remdesk.api.api.request.Request;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class DefaultUnmanagedExecutor implements UnmanagedTrigger {

    @Override
    public void handle( Request request, Object resource ) {
        
    }
}
