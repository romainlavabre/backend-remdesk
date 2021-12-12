package com.remdesk.api.module.configuration;

import com.remdesk.api.configuration.response.Message;
import com.remdesk.api.exception.HttpUnprocessableEntityException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class FileStorageConfiguration {

    private static FileStorageConfiguration fileStorageConfiguration;

    private String clientId;

    private String clientSecret;

    private String zone;

    private String compartment;


    private FileStorageConfiguration() {
        fileStorageConfiguration = this;
        loadConfig();
    }


    public String getClientId() {
        return clientId;
    }


    public String getClientSecret() {
        return clientSecret;
    }


    public String getZone() {
        return zone;
    }


    public String getCompartment() {
        return compartment;
    }


    public boolean hasConfiguration() {
        return clientId != null
                && clientSecret != null
                && zone != null
                && compartment != null;
    }


    public FileStorageConfiguration setClientId( String clientId ) {
        if ( clientId == null ) {
            throw new HttpUnprocessableEntityException( Message.STORAGE_CONFIGURATION_CLIENT_ID_REQUIRED );
        }

        this.clientId = clientId;

        return this;
    }


    public FileStorageConfiguration setClientSecret( String clientSecret ) {
        if ( clientSecret == null ) {
            throw new HttpUnprocessableEntityException( Message.STORAGE_CONFIGURATION_CLIENT_SECRET_REQUIRED );
        }

        this.clientSecret = clientSecret;

        return this;
    }


    public FileStorageConfiguration setCompartment( String compartment ) {
        if ( compartment == null ) {
            throw new HttpUnprocessableEntityException( Message.STORAGE_CONFIGURATION_COMPARTMENT_REQUIRED );
        }

        this.compartment = compartment;

        return this;
    }


    public FileStorageConfiguration setZone( String zone ) {
        if ( zone == null ) {
            throw new HttpUnprocessableEntityException( Message.STORAGE_CONFIGURATION_ZONE_REQUIRED );
        }

        this.zone = zone;

        return this;
    }


    public static FileStorageConfiguration getInstance() {
        if ( fileStorageConfiguration == null ) {
            new FileStorageConfiguration();
        }

        return fileStorageConfiguration;
    }


    public FileStorageConfiguration writeNewConfig() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "clientId", clientId );
        jsonObject.put( "clientSecret", clientId );
        jsonObject.put( "compartment", compartment );
        jsonObject.put( "zone", zone );

        FileWriter.writeStorageFile( jsonObject.toString() );

        return this;
    }


    private void loadConfig() {
        File file = FileReader.getStorageFile();

        if ( file != null ) {
            try {
                String content = Files.readString( Path.of( file.getPath() ) );
                
                JSONObject jsonObject = new JSONObject( content );
                clientId     = jsonObject.getString( "clientId" );
                clientSecret = jsonObject.getString( "clientSecret" );
                zone         = jsonObject.getString( "zone" );
                compartment  = jsonObject.getString( "compartment" );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
}
