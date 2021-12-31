package com.remdesk.api.module.configuration;

import com.remdesk.api.configuration.response.Message;
import com.remdesk.api.exception.HttpUnprocessableEntityException;
import com.remdesk.api.module.fs.OsResolver;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class FileStorageConfiguration {

    private static FileStorageConfiguration fileStorageConfiguration;

    private String clientId;

    private String clientSecret;

    private String zone;

    private String compartment;

    private String defaultCommand;

    private Map< String, Object > customCommands;


    private FileStorageConfiguration() {
        customCommands           = new HashMap<>();
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


    public String getDefaultCommand() {
        return defaultCommand;
    }


    public FileStorageConfiguration setDefaultCommand( String defaultCommand ) {
        if ( defaultCommand == null || defaultCommand.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.STORAGE_CONFIGURATION_DEFAULT_COMMAND_REQUIRED );
        }

        this.defaultCommand = defaultCommand;

        return this;
    }


    public Map< String, Object > getCustomCommands() {
        return customCommands;
    }


    public FileStorageConfiguration addCustomCommand( String contentType, String command ) {
        if ( contentType == null || contentType.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.STORAGE_CONFIGURATION_CONTENT_TYPE_REQUIRED );
        }

        if ( command == null || command.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.STORAGE_CONFIGURATION_COMMAND_REQUIRED );
        }

        customCommands.put( contentType, command );

        return this;
    }


    public boolean hasCredentialsConfiguration() {
        return clientId != null
                && clientSecret != null
                && zone != null
                && compartment != null;
    }


    public boolean hasSoftwareConfiguration() {
        return defaultCommand != null;
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
        jsonObject.put( "clientSecret", clientSecret );
        jsonObject.put( "compartment", compartment );
        jsonObject.put( "zone", zone );
        jsonObject.put( "defaultCommand", defaultCommand );
        jsonObject.put( "customCommands", customCommands );

        FileWriter.writeStorageFile( jsonObject.toString() );

        return this;
    }


    private void loadConfig() {
        File file = FileReader.getStorageFile();

        if ( file != null ) {
            try {
                String content = Files.readString( Path.of( file.getPath() ) );

                JSONObject jsonObject = new JSONObject( content );
                clientId       = jsonObject.getString( "clientId" );
                clientSecret   = jsonObject.getString( "clientSecret" );
                zone           = jsonObject.getString( "zone" );
                compartment    = jsonObject.getString( "compartment" );
                defaultCommand = jsonObject.has( "defaultCommand" ) ? jsonObject.getString( "defaultCommand" ) : null;
                customCommands = jsonObject.has( "customCommands" ) ? jsonObject.getJSONObject( "customCommands" ).toMap() : new HashMap<>();
            } catch ( IOException e ) {
                e.printStackTrace();
            }

            if ( defaultCommand == null ) {
                initDefaultConfiguration();
            }

            return;
        }

        initDefaultConfiguration();
    }


    private void initDefaultConfiguration() {
        if ( OsResolver.isUnix() ) {
            defaultCommand = "xdg-open {file}";
        }

        writeNewConfig();
    }
}
