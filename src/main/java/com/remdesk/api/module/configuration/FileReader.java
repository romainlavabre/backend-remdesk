package com.remdesk.api.module.configuration;

import com.remdesk.api.module.fs.FileSystemHandler;

import java.io.File;
import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class FileReader {
    private static final String DATABASE_FILE            = "database.json";
    private static final String STORAGE_FILE             = "storage.json";
    private static final String FILE_SOFTWARE_USAGE_FILE = "file_software_usage.json";


    public static File getDatabaseFile() {
        return FileSystemHandler.getFile( FileSystemHandler.buildPath( List.of( DATABASE_FILE ) ) );
    }


    public static File getStorageFile() {
        return FileSystemHandler.getFile( FileSystemHandler.buildPath( List.of( STORAGE_FILE ) ) );
    }
}
