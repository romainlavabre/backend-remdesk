package com.remdesk.api.module.file;

import com.remdesk.api.api.poc.api.UnmanagedTrigger;
import com.remdesk.api.api.request.Request;
import com.remdesk.api.api.storage.document.DocumentStorageHandler;
import com.remdesk.api.configuration.response.Message;
import com.remdesk.api.entity.File;
import com.remdesk.api.exception.HttpInternalServerErrorException;
import org.springframework.stereotype.Service;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class MoveFile implements UnmanagedTrigger {

    protected final DocumentStorageHandler documentStorageHandler;


    public MoveFile( DocumentStorageHandler documentStorageHandler ) {
        this.documentStorageHandler = documentStorageHandler;
    }


    @Override
    public void handle( Request request, Object resource ) {
        File file = ( File ) resource;

        String newPath = PathResolver.getPath( file );

        boolean result = documentStorageHandler.move( file.getPath(), newPath );

        if ( !result ) {
            throw new HttpInternalServerErrorException( Message.FILE_UNABLE_TO_MOVE );
        }

        file.setPath( newPath );
    }
}
