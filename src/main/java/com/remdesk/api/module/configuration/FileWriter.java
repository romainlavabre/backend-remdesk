package com.remdesk.api.module.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class FileWriter {
    private static final String HIDDEN_DIRECTORY = ".remdesk";
    private static final String DATABASE_FILE    = "database.json";
    private static final String STORAGE_FILE     = "storage.json";
    

    public static void writeDatabaseFile( String data ) {
        String path;

        if ( OsResolver.isUnix() ) {
            path = System.getProperty( "user.home" ) + "/" + HIDDEN_DIRECTORY + "/" + DATABASE_FILE;
        } else {
            path = System.getProperty( "user.home" ) + "\\" + HIDDEN_DIRECTORY + "\\" + DATABASE_FILE;
        }

        createDirectory();
        writeFile( path, data );
    }


    public static void writeStorageFile( String data ) {
        String path;

        if ( OsResolver.isUnix() ) {
            path = System.getProperty( "user.home" ) + "/" + HIDDEN_DIRECTORY + "/" + STORAGE_FILE;
        } else {
            path = System.getProperty( "user.home" ) + "\\" + HIDDEN_DIRECTORY + "\\" + STORAGE_FILE;
        }

        createDirectory();
        writeFile( path, data );
    }


    private static void createDirectory() {
        String path;

        if ( OsResolver.isUnix() ) {
            path = System.getProperty( "user.home" ) + "/" + HIDDEN_DIRECTORY;
        } else {
            path = System.getProperty( "user.home" ) + "\\" + HIDDEN_DIRECTORY;
        }

        if ( Files.exists( Path.of( path ) ) ) {
            return;
        }

        try {
            Files.createDirectory( Path.of( path ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }


    private static void writeFile( String path, String data ) {
        if ( Files.exists( Path.of( path ) ) ) {
            try {
                Files.delete( Path.of( path ) );
            } catch ( IOException e ) {
                e.printStackTrace();
                return;
            }
        }

        try {
            Files.write( Path.of( path ), data.getBytes() );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
