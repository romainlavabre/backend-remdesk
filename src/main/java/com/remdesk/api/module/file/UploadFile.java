package com.remdesk.api.module.file;

import com.remdesk.api.api.poc.api.UnmanagedTrigger;
import com.remdesk.api.api.request.Request;
import com.remdesk.api.api.storage.document.DocumentStorageHandler;
import com.remdesk.api.api.upload.UploadedFile;
import com.remdesk.api.configuration.response.Message;
import com.remdesk.api.entity.File;
import com.remdesk.api.exception.HttpConflictException;
import com.remdesk.api.exception.HttpInternalServerErrorException;
import com.remdesk.api.exception.HttpUnprocessableEntityException;
import com.remdesk.api.repository.FileRepository;
import org.springframework.stereotype.Service;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class UploadFile implements UnmanagedTrigger {

    protected final DocumentStorageHandler documentStorageHandler;
    protected final FileRepository         fileRepository;


    public UploadFile(
            DocumentStorageHandler documentStorageHandler,
            FileRepository fileRepository ) {
        this.documentStorageHandler = documentStorageHandler;
        this.fileRepository         = fileRepository;
    }


    @Override
    public void handle( Request request, Object resource ) {
        File file = ( File ) resource;

        UploadedFile uploadedFile = request.getFile( "file_file" );

        if ( uploadedFile == null ) {
            throw new HttpUnprocessableEntityException( Message.FILE_FILE_REQUIRED );
        }

        file.setPath( PathResolver.getPath( file ) );

        assertNotDuplication( file.getPath() );
        
        boolean result = documentStorageHandler.create( file.getPath(), uploadedFile.getContent() );

        if ( !result ) {
            throw new HttpInternalServerErrorException( Message.FILE_UNABLE_TO_UPLOAD );
        }

        file.setContentType( uploadedFile.getContentType() )
            .setSize( ( long ) uploadedFile.getSize() );
    }


    protected void assertNotDuplication( String path ) {
        if ( fileRepository.findByPath( path ).isPresent() ) {
            throw new HttpConflictException( Message.FILE_ALREADY_EXISTS );
        }
    }
}
