package com.remdesk.api.module.fs;

import com.remdesk.api.module.configuration.ConfigurationHandler;
import com.remdesk.api.module.configuration.FileStorageConfiguration;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.File;
import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class Clearer {

    protected final ConfigurationHandler configurationHandler;


    public Clearer( ConfigurationHandler configurationHandler ) {
        this.configurationHandler = configurationHandler;
    }


    @PreDestroy
    public void clearTemporary() {
        File directory = FileSystemHandler.getFile( FileSystemHandler.buildPath( List.of( "tmp" ) ) );

        if ( directory == null ) {
            return;
        }

        clear( directory );
    }


    @PreDestroy
    public void clearOpen() {
        File directory = FileSystemHandler.getFile( FileSystemHandler.buildPath( List.of( "open" ) ) );

        if ( directory == null ) {
            return;
        }

        for ( File children : directory.listFiles() ) {
            if ( configurationHandler.getFileStorageConfig().getPreserveNetworkLevel() == FileStorageConfiguration.PRESERVE_NETWORK_LEVEL_0
                    || configurationHandler.getFileStorageConfig().getPreserveNetworkLevel() == FileStorageConfiguration.PRESERVE_NETWORK_LEVEL_1
                    || children.getName().matches( "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}\\..*" ) ) {
                children.delete();
            }
        }
    }


    public void purgeOpen() {
        File directory = FileSystemHandler.getFile( FileSystemHandler.buildPath( List.of( "open" ) ) );

        if ( directory == null ) {
            return;
        }

        for ( File children : directory.listFiles() ) {
            children.delete();
        }
    }


    private void clear( File directory ) {
        for ( File children : directory.listFiles() ) {
            children.delete();
        }
    }
}
