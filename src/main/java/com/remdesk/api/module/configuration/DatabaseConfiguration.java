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
public class DatabaseConfiguration {

    private static DatabaseConfiguration databaseConfiguration;

    private String software;

    private String port;

    private String host;

    private String username;

    private String password;

    private String driver;


    private DatabaseConfiguration() {
        databaseConfiguration = this;
        loadConfig();
    }


    public String getSoftware() {
        return software;
    }


    public String getPort() {
        return port;
    }


    public String getHost() {
        return host;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public String getDriver() {
        String url = getUrl();

        if ( url.startsWith( "jdbc:mysql" ) ) {
            return "com.mysql.jdbc.Driver";
        }

        if ( url.startsWith( "jdbc:postgresql" ) ) {
            return "org.postgresql.Driver";
        }

        return null;
    }


    public String getUrl() {
        return "jdbc:" + software + "://" + host + ":" + port + "/remdesk";
    }


    public DatabaseConfiguration setHost( String host ) {
        if ( host == null ) {
            throw new HttpUnprocessableEntityException( Message.DATABASE_CONFIGURATION_HOST_REQUIRED );
        }

        this.host = host;

        return this;
    }


    public DatabaseConfiguration setPassword( String password ) {
        this.password = password;

        return this;
    }


    public DatabaseConfiguration setPort( String port ) {
        if ( port == null ) {
            throw new HttpUnprocessableEntityException( Message.DATABASE_CONFIGURATION_PORT_REQUIRED );
        }

        this.port = port;

        return this;
    }


    public DatabaseConfiguration setSoftware( String software ) {
        if ( software == null ) {
            throw new HttpUnprocessableEntityException( Message.DATABASE_CONFIGURATION_SOFTWARE_REQUIRED );
        }

        this.software = software;

        return this;
    }


    public DatabaseConfiguration setUsername( String username ) {
        if ( username == null ) {
            throw new HttpUnprocessableEntityException( Message.DATABASE_CONFIGURATION_USERNAME_REQUIRED );
        }

        this.username = username;

        return this;
    }


    public boolean hasConfiguration() {
        return software != null
                && port != null
                && host != null
                && username != null
                && password != null;
    }


    public static DatabaseConfiguration getInstance() {
        if ( databaseConfiguration == null ) {
            new DatabaseConfiguration();
        }

        return databaseConfiguration;
    }


    public void clear() {
        software = null;
        port     = null;
        host     = null;
        username = null;
        password = null;
        loadConfig();
    }


    public DatabaseConfiguration writeNewConfig() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "software", software );
        jsonObject.put( "port", port );
        jsonObject.put( "host", host );
        jsonObject.put( "username", username );
        jsonObject.put( "password", password );

        FileWriter.writeDatabaseFile( jsonObject.toString() );

        return this;
    }


    private void loadConfig() {
        File file = FileReader.getDatabaseFile();

        if ( file != null ) {
            try {
                String content = Files.readString( Path.of( file.getPath() ) );

                JSONObject jsonObject = new JSONObject( content );
                software = jsonObject.getString( "software" );
                port     = jsonObject.getString( "port" );
                host     = jsonObject.getString( "host" );
                username = jsonObject.getString( "username" );
                password = jsonObject.getString( "password" );
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
}
