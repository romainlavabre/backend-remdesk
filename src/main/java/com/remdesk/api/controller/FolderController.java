package com.remdesk.api.controller;

import com.remdesk.api.api.json.Encoder;
import com.remdesk.api.configuration.json.GroupType;
import com.remdesk.api.entity.Folder;
import com.remdesk.api.module.file.PathResolver;
import com.remdesk.api.module.file.ZipBuilder;
import com.remdesk.api.repository.FolderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@RestController( "GuestFolderController" )
@RequestMapping( path = "/guest/folders" )
public class FolderController {
    protected final FolderRepository folderRepository;
    protected final ZipBuilder       zipBuilder;


    public FolderController(
            FolderRepository folderRepository,
            ZipBuilder zipBuilder ) {
        this.folderRepository = folderRepository;
        this.zipBuilder       = zipBuilder;
    }


    @GetMapping( path = "/by/root" )
    public ResponseEntity< List< Map< String, Object > > > getFoldersByRoot() {
        List< Folder > folders = folderRepository.findAllByRoot();

        return ResponseEntity
                .ok( Encoder.encode( folders, GroupType.GUEST ) );
    }


    @GetMapping( path = "/parents/{id:[0-9]+}" )
    public ResponseEntity< List< Map< String, Object > > > getFoldersPath( @PathVariable( "id" ) long id ) {
        Folder folder = folderRepository.findOrFail( id );

        return ResponseEntity
                .ok( Encoder.encode( PathResolver.getHumanPath( folder ), GroupType.GUEST ) );
    }


    @GetMapping( path = "/{id:[0-9]+}/to_zip" )
    public ResponseEntity< byte[] > getFoldersToZip( @PathVariable( "id" ) long id )
            throws IOException {
        Folder folder = folderRepository.findOrFail( id );

        return ResponseEntity
                .ok( Files.readAllBytes( zipBuilder.getZip( folder ).toPath() ) );
    }
}
