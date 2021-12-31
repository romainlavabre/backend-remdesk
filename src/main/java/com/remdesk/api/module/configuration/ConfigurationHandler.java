package com.remdesk.api.module.configuration;

import com.remdesk.api.api.request.Request;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface ConfigurationHandler {
    void insertDatabaseConfig( Request request );


    void insertFileStorageConfig( Request request );


    void insertFileSoftwareUsage( Request request );


    DatabaseConfiguration getDatabaseConfig();


    FileStorageConfiguration getFileStorageConfig();
}
