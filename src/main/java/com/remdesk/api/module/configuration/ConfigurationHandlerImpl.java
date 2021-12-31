package com.remdesk.api.module.configuration;

import com.remdesk.api.api.request.Request;
import com.remdesk.api.configuration.response.Message;
import com.remdesk.api.exception.HttpUnprocessableEntityException;
import com.remdesk.api.parameter.DatabaseParameter;
import com.remdesk.api.parameter.FileSoftwareUsageParameter;
import com.remdesk.api.parameter.StorageParameter;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class ConfigurationHandlerImpl implements ConfigurationHandler {
    @Override
    public void insertDatabaseConfig( Request request ) {
        String software = ( String ) request.getParameter( DatabaseParameter.SOFTWARE );
        String host     = ( String ) request.getParameter( DatabaseParameter.HOST );
        String port     = ( String ) request.getParameter( DatabaseParameter.PORT );
        String username = ( String ) request.getParameter( DatabaseParameter.USERNAME );
        String password = ( String ) request.getParameter( DatabaseParameter.PASSWORD );


        DatabaseConfiguration databaseConfiguration = getDatabaseConfig();
        databaseConfiguration.setSoftware( software )
                             .setHost( host )
                             .setPort( port )
                             .setUsername( username )
                             .setPassword( password );

        if ( !testConnection( databaseConfiguration ) ) {
            databaseConfiguration.clear();
            throw new HttpUnprocessableEntityException( Message.DATABASE_CONFIGURATION_NO_CONNECTION_ESTABLISHED );
        }

        databaseConfiguration.writeNewConfig();
    }


    @Override
    public void insertFileStorageConfig( Request request ) {
        String clientId     = ( String ) request.getParameter( StorageParameter.CLIENT_ID );
        String clientSecret = ( String ) request.getParameter( StorageParameter.CLIENT_SECRET );
        String compartment  = ( String ) request.getParameter( StorageParameter.COMPARTMENT );
        String zone         = ( String ) request.getParameter( StorageParameter.ZONE );

        FileStorageConfiguration fileStorageConfiguration = getFileStorageConfig();
        fileStorageConfiguration
                .setClientId( clientId )
                .setClientSecret( clientSecret )
                .setCompartment( compartment )
                .setZone( zone )
                .writeNewConfig();
    }


    @Override
    public void insertFileSoftwareUsage( Request request ) {
        String                defaultCommand = ( String ) request.getParameter( FileSoftwareUsageParameter.DEFAULT_COMMAND );
        Map< String, Object > customCommand  = ( Map< String, Object > ) request.getParameter( FileSoftwareUsageParameter.CUSTOM_COMMANDS );

        FileStorageConfiguration fileStorageConfiguration = getFileStorageConfig();
        fileStorageConfiguration
                .setDefaultCommand( defaultCommand );

        fileStorageConfiguration.getCustomCommands().clear();

        for ( Map.Entry< String, Object > entry : customCommand.entrySet() ) {
            fileStorageConfiguration.addCustomCommand( entry.getKey(), entry.getValue() != null ? entry.getValue().toString() : null );
        }

        fileStorageConfiguration.writeNewConfig();
    }


    @Override
    public DatabaseConfiguration getDatabaseConfig() {
        return DatabaseConfiguration.getInstance();
    }


    @Override
    public FileStorageConfiguration getFileStorageConfig() {
        return FileStorageConfiguration.getInstance();
    }


    protected boolean testConnection( DatabaseConfiguration databaseConfiguration ) {
        try {
            Connection connection = DriverManager.getConnection( databaseConfiguration.getUrl(), databaseConfiguration.getUsername(), databaseConfiguration.getPassword() );
            connection.close();
            return true;
        } catch ( SQLException exception ) {
            return false;
        }
    }


    @Bean
    @DependsOn( {"FileSystemHandler"} )
    public DataSource getDataSource() {
        DatabaseConfiguration databaseConfiguration = getDatabaseConfig();

        if ( !databaseConfiguration.hasConfiguration() ) {
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.driverClassName( "org.h2.Driver" );
            dataSourceBuilder.url( "jdbc:h2:mem:test" );
            dataSourceBuilder.username( "root" );
            dataSourceBuilder.password( "root" );
            return dataSourceBuilder.build();
        }

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName( databaseConfiguration.getDriver() );
        dataSourceBuilder.url( databaseConfiguration.getUrl() );
        dataSourceBuilder.username( databaseConfiguration.getUsername() );
        dataSourceBuilder.password( databaseConfiguration.getPassword() );
        return dataSourceBuilder.build();
    }
}
