package com.remdesk.api.module.file;

import com.remdesk.api.api.crud.Delete;
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
public class DeleteFile implements Delete< File > {

    protected final DocumentStorageHandler documentStorageHandler;


    public DeleteFile( DocumentStorageHandler documentStorageHandler ) {
        this.documentStorageHandler = documentStorageHandler;
    }


    @Override
    public void delete( Request request, File file ) {
        boolean result = documentStorageHandler.remove( file.getPath() );

        if ( !result ) {
            throw new HttpInternalServerErrorException( Message.FILE_UNABLE_TO_REMOVE );
        }
    }
}
