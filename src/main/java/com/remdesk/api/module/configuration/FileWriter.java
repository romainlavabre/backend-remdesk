package com.remdesk.api.module.configuration;

import com.remdesk.api.module.fs.FileSystemHandler;

import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class FileWriter {
    private static final String DATABASE_FILE            = "database.json";
    private static final String STORAGE_FILE             = "storage.json";
    private static final String FILE_SOFTWARE_USAGE_FILE = "file_software_usage.json";


    public static void writeDatabaseFile( String data ) {
        FileSystemHandler.writeFile( FileSystemHandler.buildPath( List.of( DATABASE_FILE ) ), data );
    }


    public static void writeStorageFile( String data ) {
        FileSystemHandler.writeFile( FileSystemHandler.buildPath( List.of( STORAGE_FILE ) ), data );
    }


    public static void writeFileSoftwareUsageFile( String data ) {
        FileSystemHandler.writeFile( FileSystemHandler.buildPath( List.of( FILE_SOFTWARE_USAGE_FILE ) ), data );
    }
}
