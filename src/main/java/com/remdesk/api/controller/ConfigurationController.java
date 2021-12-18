package com.remdesk.api.controller;

import com.remdesk.api.api.json.Encoder;
import com.remdesk.api.api.request.Request;
import com.remdesk.api.configuration.json.GroupType;
import com.remdesk.api.module.configuration.ConfigurationHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@RestController( "GuestConfigurationController" )
@RequestMapping( path = "/guest/configuration" )
public class ConfigurationController {

    protected final Request              request;
    protected final ConfigurationHandler configurationHandler;


    public ConfigurationController(
            Request request,
            ConfigurationHandler configurationHandler ) {
        this.request              = request;
        this.configurationHandler = configurationHandler;
    }


    @GetMapping( path = "/loaded" )
    public ResponseEntity< Map< String, Boolean > > getLoadedConfig() {
        return ResponseEntity.ok( Map.of(
                "database", configurationHandler.getDatabaseConfig().hasConfiguration(),
                "storage", configurationHandler.getFileStorageConfig().hasConfiguration()
        ) );
    }


    @GetMapping( path = "/file_software_usage" )
    public ResponseEntity< Map< String, Object > > getFileSoftwareUsage() {

        return ResponseEntity
                .ok( Encoder.encode( configurationHandler.getFileSoftwareUsageConfiguration(), GroupType.GUEST ) );
    }


    @PostMapping( path = "/database" )
    public ResponseEntity< Void > database() {
        configurationHandler.insertDatabaseConfig( request );

        return ResponseEntity
                .noContent()
                .build();
    }


    @PostMapping( path = "/storage" )
    public ResponseEntity< Void > storage() {
        configurationHandler.insertFileStorageConfig( request );

        return ResponseEntity
                .noContent()
                .build();
    }


    @PostMapping( path = "/file_software_usage" )
    public ResponseEntity< Void > fileSoftwareUsage() {
        configurationHandler.insertFileSoftwareUsage( request );

        return ResponseEntity
                .noContent()
                .build();
    }
}
