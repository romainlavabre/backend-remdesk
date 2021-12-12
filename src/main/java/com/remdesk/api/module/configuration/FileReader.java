package com.remdesk.api.module.configuration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class FileReader {
    private static final String HIDDEN_DIRECTORY = ".remdesk";
    private static final String DATABASE_FILE    = "database.json";
    private static final String STORAGE_FILE     = "storage.json";


    public static File getDatabaseFile() {
        String path;

        if ( OsResolver.isUnix() ) {
            path = System.getProperty( "user.home" ) + "/" + HIDDEN_DIRECTORY + "/" + DATABASE_FILE;
        } else {
            path = System.getProperty( "user.home" ) + "\\" + HIDDEN_DIRECTORY + "\\" + DATABASE_FILE;
        }

        return getFile( path );
    }


    public static File getStorageFile() {
        String path;

        if ( OsResolver.isUnix() ) {
            path = System.getProperty( "user.home" ) + "/" + HIDDEN_DIRECTORY + "/" + STORAGE_FILE;
        } else {
            path = System.getProperty( "user.home" ) + "\\" + HIDDEN_DIRECTORY + "\\" + STORAGE_FILE;
        }

        return getFile( path );
    }


    private static File getFile( String path ) {
        if ( !Files.exists( Path.of( path ) ) ) {
            return null;
        }

        File file = new File( path );

        return file;
    }
}
