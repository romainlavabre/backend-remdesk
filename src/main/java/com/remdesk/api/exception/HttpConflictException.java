package com.remdesk.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@ResponseStatus( code = HttpStatus.CONFLICT )
public class HttpConflictException extends RuntimeException {

    public HttpConflictException( String message ) {
        super( message );
    }
}
