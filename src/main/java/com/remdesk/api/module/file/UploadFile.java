package com.remdesk.api.module.file;

import com.remdesk.api.api.crud.Create;
import com.remdesk.api.api.request.Request;
import com.remdesk.api.api.storage.document.DocumentStorageHandler;
import com.remdesk.api.api.upload.UploadedFile;
import com.remdesk.api.configuration.response.Message;
import com.remdesk.api.entity.File;
import com.remdesk.api.exception.HttpInternalServerErrorException;
import com.remdesk.api.exception.HttpUnprocessableEntityException;
import org.springframework.stereotype.Service;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class UploadFile implements Create< File > {

    protected final DocumentStorageHandler documentStorageHandler;


    public UploadFile( DocumentStorageHandler documentStorageHandler ) {
        this.documentStorageHandler = documentStorageHandler;
    }


    @Override
    public void create( Request request, File file ) {
        UploadedFile uploadedFile = request.getFile( "file_file" );

        if ( uploadedFile == null ) {
            throw new HttpUnprocessableEntityException( Message.FILE_FILE_REQUIRED );
        }

        boolean result = documentStorageHandler.create( file.getPath(), uploadedFile.getContent() );

        if ( !result ) {
            throw new HttpInternalServerErrorException( Message.FILE_UNABLE_TO_UPLOAD );
        }

        file.setContentType( uploadedFile.getContentType() )
            .setSize( ( long ) uploadedFile.getContent().length )
            .setPath( PathResolver.getPath( file ) );
    }
}
