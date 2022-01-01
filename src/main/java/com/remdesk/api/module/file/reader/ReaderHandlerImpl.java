package com.remdesk.api.module.file.reader;

import com.remdesk.api.api.storage.document.DocumentStorageHandler;
import com.remdesk.api.api.upload.ContentTypeResolver;
import com.remdesk.api.entity.File;
import com.remdesk.api.module.configuration.ConfigurationHandler;
import com.remdesk.api.module.configuration.Encryptor;
import com.remdesk.api.module.configuration.FileStorageConfiguration;
import com.remdesk.api.module.fs.FileSystemHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class ReaderHandlerImpl implements ReaderHandler {

    protected final DocumentStorageHandler documentStorageHandler;
    protected final ConfigurationHandler   configurationHandler;
    protected final Encryptor              encryptor;


    public ReaderHandlerImpl(
            DocumentStorageHandler documentStorageHandler,
            ConfigurationHandler configurationHandler,
            Encryptor encryptor ) {
        this.documentStorageHandler = documentStorageHandler;
        this.configurationHandler   = configurationHandler;
        this.encryptor              = encryptor;
    }


    @Override
    public void openFile( File file ) {
        String command = getCommand( createFile( file ), file );

        final String[] cmdline = {"sh", "-c", command};

        final Runtime runtime = Runtime.getRuntime();

        try {
            runtime.exec( cmdline );
        } catch ( final IOException e ) {
            e.printStackTrace();
        }
    }


    protected java.io.File createFile( File file ) {
        FileSystemHandler.createDirectory( FileSystemHandler.buildPath( List.of( "open" ) ) );

        int    preserveNetworkLevel = configurationHandler.getFileStorageConfig().getPreserveNetworkLevel();
        String path;

        switch ( preserveNetworkLevel ) {
            case FileStorageConfiguration.PRESERVE_NETWORK_LEVEL_0:
            default:
                path = FileSystemHandler.buildPath( List.of( "open", UUID.randomUUID().toString() + "." + ContentTypeResolver.getExtension( file.getContentType() ) ) );
                break;
            case FileStorageConfiguration.PRESERVE_NETWORK_LEVEL_1:
            case FileStorageConfiguration.PRESERVE_NETWORK_LEVEL_2:
                path = FileSystemHandler.buildPath( List.of( "open", file.getId() + "." + ContentTypeResolver.getExtension( file.getContentType() ) ) );
                break;
        }

        java.io.File temporary;

        if ( Files.exists( Path.of( path ) ) && preserveNetworkLevel != FileStorageConfiguration.PRESERVE_NETWORK_LEVEL_0 ) {
            temporary = new java.io.File( path );
        } else {
            temporary = FileSystemHandler.writeFile(
                    path,
                    file.isEncrypted()
                            ? encryptor.decrypt( documentStorageHandler.getContent( file.getPath() ) )
                            : documentStorageHandler.getContent( file.getPath() )
            );
        }

        return temporary;
    }


    protected String getCommand( java.io.File temporary, File origin ) {
        FileStorageConfiguration fileStorageConfiguration = configurationHandler.getFileStorageConfig();
        Map< String, Object >    customCommands           = fileStorageConfiguration.getCustomCommands();

        if ( customCommands.containsKey( origin.getContentType() ) ) {
            return customCommands
                    .get( origin.getContentType() )
                    .toString()
                    .replace( "{file}", temporary.getAbsolutePath() );
        }

        return fileStorageConfiguration
                .getDefaultCommand()
                .replace( "{file}", temporary.getAbsolutePath() );
    }
}
