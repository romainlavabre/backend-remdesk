package com.remdesk.api.module.file.reader;

import com.remdesk.api.api.storage.document.DocumentStorageHandler;
import com.remdesk.api.api.upload.ContentTypeResolver;
import com.remdesk.api.entity.File;
import com.remdesk.api.module.configuration.ConfigurationHandler;
import com.remdesk.api.module.configuration.FileStorageConfiguration;
import com.remdesk.api.module.fs.FileSystemHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
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


    public ReaderHandlerImpl(
            DocumentStorageHandler documentStorageHandler,
            ConfigurationHandler configurationHandler ) {
        this.documentStorageHandler = documentStorageHandler;
        this.configurationHandler   = configurationHandler;
    }


    @Override
    public void openFile( File file ) {
        FileSystemHandler.createDirectory( FileSystemHandler.buildPath( List.of( "open" ) ) );

        java.io.File temporary = FileSystemHandler.writeFile(
                FileSystemHandler.buildPath( List.of( "open", UUID.randomUUID().toString() + "." + ContentTypeResolver.getExtension( file.getContentType() ) ) ),
                documentStorageHandler.getContent( file.getPath() )
        );

        String command = getCommand( temporary, file );

        final String[] cmdline = {"sh", "-c", command};

        final Runtime runtime = Runtime.getRuntime();

        try {
            runtime.exec( cmdline );
        } catch ( final IOException e ) {
            e.printStackTrace();
        }
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
