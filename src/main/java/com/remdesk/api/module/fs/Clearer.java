package com.remdesk.api.module.fs;

import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.File;
import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class Clearer {

    @PreDestroy
    public void clearTemporary() {
        File directory = FileSystemHandler.getFile( FileSystemHandler.buildPath( List.of( "tmp" ) ) );

        if ( directory == null ) {
            return;
        }
        
        clear( directory );
    }


    @PreDestroy
    public void clearOpen() {
        File directory = FileSystemHandler.getFile( FileSystemHandler.buildPath( List.of( "open" ) ) );

        if ( directory == null ) {
            return;
        }

        clear( directory );
    }


    private void clear( File directory ) {
        for ( File children : directory.listFiles() ) {
            children.delete();
        }
    }
}
