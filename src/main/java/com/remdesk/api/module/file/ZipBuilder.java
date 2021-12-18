package com.remdesk.api.module.file;

import com.remdesk.api.api.storage.document.DocumentStorageHandler;
import com.remdesk.api.entity.Folder;
import com.remdesk.api.module.fs.FileSystemHandler;
import com.remdesk.api.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class ZipBuilder {

    protected final FileRepository         fileRepository;
    protected final DocumentStorageHandler documentStorageHandler;


    public ZipBuilder(
            FileRepository fileRepository,
            DocumentStorageHandler documentStorageHandler ) {
        this.fileRepository         = fileRepository;
        this.documentStorageHandler = documentStorageHandler;
    }


    public File getZip( Folder folder ) {
        FileSystemHandler.createDirectory( FileSystemHandler.buildPath( List.of( "tmp" ) ) );

        String path = FileSystemHandler.getRealPath( FileSystemHandler.buildPath( List.of( "tmp", UUID.randomUUID().toString() ) ) );

        try ( ZipOutputStream zipOutputStream = new ZipOutputStream( new FileOutputStream( path ) ) ) {
            for ( com.remdesk.api.entity.File file : fileRepository.findAllByFolder( folder ) ) {

                File created = FileSystemHandler.writeFile(
                        FileSystemHandler.buildPath( List.of( "tmp", UUID.randomUUID().toString() ) ),
                        documentStorageHandler.getContent( file.getPath() )
                );

                zipOutputStream.putNextEntry( new ZipEntry( file.getName() ) );
                Files.copy( created.toPath(), zipOutputStream );
                zipOutputStream.closeEntry();
                created.delete();
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return new File( path );
    }
}
