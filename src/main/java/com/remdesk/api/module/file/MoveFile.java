package com.remdesk.api.module.file;

import com.remdesk.api.api.crud.Update;
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
public class MoveFile implements Update< File > {

    protected final DocumentStorageHandler documentStorageHandler;


    public MoveFile( DocumentStorageHandler documentStorageHandler ) {
        this.documentStorageHandler = documentStorageHandler;
    }


    @Override
    public void update( Request request, File file ) {
        boolean result = documentStorageHandler.move( file.getPath(), PathResolver.getPath( file ) );

        if ( !result ) {
            throw new HttpInternalServerErrorException( Message.FILE_UNABLE_TO_MOVE );
        }

        file.setPath( PathResolver.getPath( file ) );
    }
}
