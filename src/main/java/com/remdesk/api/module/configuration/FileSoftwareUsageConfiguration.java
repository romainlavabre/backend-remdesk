package com.remdesk.api.module.configuration;

import com.remdesk.api.api.json.annotation.Group;
import com.remdesk.api.api.json.annotation.Json;
import com.remdesk.api.configuration.json.GroupType;
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
public class FileSoftwareUsageConfiguration {
    private static FileSoftwareUsageConfiguration fileSoftwareUsageConfiguration;

    @Json( groups = {
            @Group( name = GroupType.GUEST, key = "default_command" )
    } )
    private String defaultCommand;

    @Json( groups = {
            @Group( name = GroupType.GUEST, key = "custom_commands" )
    } )
    private Map< String, Object > customCommands;


    private FileSoftwareUsageConfiguration() {
        fileSoftwareUsageConfiguration = this;
        customCommands                 = new HashMap<>();
        loadConfig();
    }


    public String getDefaultCommand() {
        return defaultCommand;
    }


    public FileSoftwareUsageConfiguration setDefaultCommand( String defaultCommand ) {
        if ( defaultCommand == null || defaultCommand.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.FILE_SOFTWARE_USER_CONFIGURATION_DEFAULT_COMMAND_REQUIRED );
        }

        this.defaultCommand = defaultCommand;

        return this;
    }


    public Map< String, Object > getCustomCommands() {
        return customCommands;
    }


    public FileSoftwareUsageConfiguration addCustomCommand( String contentType, String command ) {
        if ( contentType == null || contentType.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.FILE_SOFTWARE_USER_CONFIGURATION_CONTENT_TYPE_REQUIRED );
        }

        if ( command == null || command.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.FILE_SOFTWARE_USER_CONFIGURATION_COMMAND_REQUIRED );
        }

        customCommands.put( contentType, command );

        return this;
    }


    public FileSoftwareUsageConfiguration writeNewConfig() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "defaultCommand", defaultCommand );
        jsonObject.put( "customCommands", customCommands );

        FileWriter.writeFileSoftwareUsageFile( jsonObject.toString() );

        return this;
    }


    private void loadConfig() {
        File file = FileReader.getFileSoftwareUsageFile();

        if ( file != null ) {
            try {
                String content = Files.readString( Path.of( file.getPath() ) );

                JSONObject jsonObject = new JSONObject( content );
                defaultCommand = jsonObject.getString( "defaultCommand" );
                customCommands = jsonObject.getJSONObject( "customCommands" ).toMap();
            } catch ( IOException e ) {
                e.printStackTrace();
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


    public static FileSoftwareUsageConfiguration getInstance() {
        if ( fileSoftwareUsageConfiguration == null ) {
            new FileSoftwareUsageConfiguration();
        }

        return fileSoftwareUsageConfiguration;
    }
}
