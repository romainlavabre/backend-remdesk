package com.remdesk.api.controller;

import com.remdesk.api.api.json.Encoder;
import com.remdesk.api.configuration.json.GroupType;
import com.remdesk.api.entity.File;
import com.remdesk.api.repository.FileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@RestController( "GuestFileController" )
@RequestMapping( path = "/guest/files" )
public class FileController {

    protected final FileRepository fileRepository;


    public FileController( FileRepository fileRepository ) {
        this.fileRepository = fileRepository;
    }


    @GetMapping( path = "/by/root" )
    public ResponseEntity< List< Map< String, Object > > > getFoldersByRoot() {
        List< File > files = fileRepository.findAllByRoot();

        return ResponseEntity
                .ok( Encoder.encode( files, GroupType.GUEST ) );
    }
}
