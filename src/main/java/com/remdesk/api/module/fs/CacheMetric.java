package com.remdesk.api.module.fs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class CacheMetric {

    public static long getSize() {
        File directory = FileSystemHandler.getFile( FileSystemHandler.buildPath( List.of( "open" ) ) );

        if ( directory == null ) {
            return 0;
        }

        long size = 0;

        for ( File children : directory.listFiles() ) {
            try {
                size += Files.size( children.toPath() );
            } catch ( IOException e ) {
                
            }
        }

        return size;
    }
}
