package com.remdesk.api.controller;

import com.remdesk.api.api.json.Encoder;
import com.remdesk.api.api.storage.document.DocumentStorageHandler;
import com.remdesk.api.configuration.json.GroupType;
import com.remdesk.api.entity.File;
import com.remdesk.api.module.file.reader.ReaderHandler;
import com.remdesk.api.module.fs.CacheMetric;
import com.remdesk.api.module.fs.Clearer;
import com.remdesk.api.repository.FileRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@RestController( "GuestFileController" )
@RequestMapping( path = "/guest/files" )
public class FileController {

    protected final FileRepository         fileRepository;
    protected final DocumentStorageHandler documentStorageHandler;
    protected final ReaderHandler          readerHandler;
    protected final Clearer                clearer;


    public FileController(
            FileRepository fileRepository,
            DocumentStorageHandler documentStorageHandler,
            ReaderHandler readerHandler,
            Clearer clearer ) {
        this.fileRepository         = fileRepository;
        this.documentStorageHandler = documentStorageHandler;
        this.readerHandler          = readerHandler;
        this.clearer                = clearer;
    }


    @GetMapping( path = "/by/root" )
    public ResponseEntity< List< Map< String, Object > > > getFoldersByRoot() {
        List< File > files = fileRepository.findAllByRoot();

        return ResponseEntity
                .ok( Encoder.encode( files, GroupType.GUEST ) );
    }


    @GetMapping( path = "/{id:[0-9]+}/base_64" )
    public ResponseEntity< String > getBase64( @PathVariable( "id" ) long id ) {
        File   file    = fileRepository.findOrFail( id );
        byte[] content = documentStorageHandler.getContent( file.getPath() );

        return ResponseEntity.ok( Base64.encodeBase64String( content ) );
    }


    @GetMapping( path = "/{id:[0-9]+}/content" )
    public ResponseEntity< byte[] > getContent( @PathVariable( "id" ) long id ) {
        File   file    = fileRepository.findOrFail( id );
        byte[] content = documentStorageHandler.getContent( file.getPath() );

        return ResponseEntity.ok( content );
    }


    @GetMapping( path = "/cache/metric" )
    public ResponseEntity< Map< String, Object > > getCacheMetric() {
        return ResponseEntity.ok( Map.of(
                "size", CacheMetric.getSize()
        ) );
    }


    @PostMapping( path = "/{id:[0-9]+}/open" )
    public ResponseEntity< byte[] > openFile( @PathVariable( "id" ) long id ) {
        File file = fileRepository.findOrFail( id );

        readerHandler.openFile( file );

        return ResponseEntity.noContent().build();
    }


    @PostMapping( path = "/cache/clear" )
    public ResponseEntity< Void > clearCache() {
        clearer.purgeOpen();

        return ResponseEntity.noContent().build();
    }
}
