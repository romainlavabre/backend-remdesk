package com.replace.replace.api.dynamic.api;

import com.replace.replace.api.request.Request;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface DeleteEntry {
    void delete( Request request, Object entity );
}
