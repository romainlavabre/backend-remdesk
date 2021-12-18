package com.remdesk.api.module.fs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class FileSystemHandler {
    private static final String HIDDEN_DIRECTORY = ".remdesk";
    private static       String ROOT;


    public static File writeFile( String path, String data ) {
        return writeFile( path, data.getBytes() );
    }


    public static File writeFile( String path, byte[] data ) {
        resolveRoot();

        path = getRealPath( path );

        if ( Files.exists( Path.of( path ) ) ) {
            try {
                Files.delete( Path.of( path ) );
            } catch ( IOException e ) {
                e.printStackTrace();
                return null;
            }
        }

        try {
            Files.write( Path.of( path ), data );

            return new File( path );
        } catch ( IOException e ) {
            e.printStackTrace();

            return null;
        }
    }


    public static File getFile( String path ) {
        resolveRoot();

        path = getRealPath( path );

        if ( !Files.exists( Path.of( path ) ) ) {
            return null;
        }

        File file = new File( path );

        return file;
    }


    public static void createDirectory( String path ) {
        resolveRoot();

        path = getRealPath( path );

        if ( Files.exists( Path.of( path ) ) ) {
            return;
        }

        try {
            Files.createDirectory( Path.of( path ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }


    public static String buildPath( List< String > sections ) {
        StringJoiner stringJoiner = new StringJoiner( getDirectorySeparator() );

        sections.forEach( stringJoiner::add );

        return "/" + stringJoiner.toString();
    }


    private static void resolveRoot() {
        if ( ROOT != null ) {
            return;
        }

        if ( OsResolver.isUnix() ) {
            ROOT = System.getProperty( "user.home" ) + "/" + HIDDEN_DIRECTORY;
        } else {
            ROOT = System.getProperty( "user.home" ) + "\\" + HIDDEN_DIRECTORY;
        }

        createDirectory( "" );
    }


    public static String getRealPath( String relativePath ) {
        return ROOT + relativePath;
    }


    private static String getDirectorySeparator() {
        return OsResolver.isUnix() ? "/" : "\\";
    }
}
