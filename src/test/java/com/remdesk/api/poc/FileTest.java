package com.remdesk.api.poc;

import com.remdesk.api.api.poc.client.PocClient;
import com.remdesk.api.api.poc.client.PocMock;
import com.remdesk.api.api.request.UploadedFile;
import com.remdesk.api.api.request.UploadedFileImpl;
import com.remdesk.api.api.storage.document.DocumentStorageHandler;
import com.remdesk.api.entity.File;
import com.remdesk.api.entity.Folder;
import com.remdesk.api.module.configuration.Encryptor;
import com.remdesk.api.repository.FileRepository;
import com.remdesk.api.repository.FolderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class FileTest {

    @Test
    public void getOne() {
        FileRepository fileRepository = PocClient.getMocker().getMock( FileRepository.class );

        Mockito.when( fileRepository.findOrFail( Mockito.anyLong() ) ).thenReturn( new File() );

        PocClient.getClient()
                 .get( "/guest/files/1" )
                 .execute()
                 .is2xxCode();
    }


    @Test
    public void getAllByFolder() {
        PocMock          pocMock          = PocClient.getMocker();
        FolderRepository folderRepository = pocMock.getMock( FolderRepository.class );
        FileRepository   fileRepository   = pocMock.getMock( FileRepository.class );

        Mockito.when( folderRepository.findOrFail( Mockito.anyLong() ) ).thenReturn( new Folder() );
        Mockito.when( fileRepository.findAllByFolder( Mockito.any( Folder.class ) ) ).thenReturn( List.of( new File() ) );

        PocClient.getClient()
                 .get( "/guest/files/by/folder/1" )
                 .execute()
                 .is2xxCode();
    }


    @Test
    public void create_success() {

        PocMock pocMock = PocClient.getMocker();
        Mockito.when( pocMock.getMock( FolderRepository.class ).findOrFail( 1L ) )
               .thenReturn( new Folder() );
        Mockito.when( pocMock.getMock( DocumentStorageHandler.class ).create( Mockito.anyString(), Mockito.any( byte[].class ) ) )
               .thenReturn( true );
        Mockito.when( pocMock.getMock( Encryptor.class ).encrypt( Mockito.any( byte[].class ) ) ).thenReturn( new byte[]{1, 2, 3} );

        for ( Map< String, Map< String, Object > > payload : dp_create_success() ) {
            UploadedFile uploadedFile = null;

            if ( payload.get( "file" ).values().size() > 0 ) {
                uploadedFile = ( UploadedFile ) payload.get( "file" ).values().toArray()[ 0 ];
            }

            Map< String, UploadedFile > files = new HashMap<>();
            files.put( "file_file", uploadedFile );

            Map< String, Object > body =
                    PocClient.getClient()
                             .post( "/guest/files" )
                             .parameters( payload.get( "param" ) )
                             .files( files )
                             .execute( true )
                             .is2xxCode()
                             .getBodyAsMap();

            Assertions.assertEquals( body.get( "name" ), payload.get( "param" ).get( "file_name" ) );
            Assertions.assertEquals( 0L, body.get( "size" ) );
            Assertions.assertEquals( "application/pdf", body.get( "content_type" ) );

            if ( payload.get( "param" ).get( "file_folder_id" ) != null ) {
                Assertions.assertNotNull( body.get( "folder_id" ) );
            }
        }
    }


    @Test
    public void create_fail() {

        PocMock pocMock = PocClient.getMocker();

        Mockito.when( pocMock.getMock( FolderRepository.class ).findOrFail( 1L ) ).thenReturn( new Folder() );
        Mockito.when( pocMock.getMock( DocumentStorageHandler.class ).create( Mockito.anyString(), Mockito.any( byte[].class ) ) )
               .thenReturn( true );

        for ( Map< String, Map< String, Object > > payload : dp_create_fail() ) {
            UploadedFile uploadedFile = null;

            if ( payload.get( "file" ).values().size() > 0 ) {
                uploadedFile = ( UploadedFile ) payload.get( "file" ).values().toArray()[ 0 ];
            }

            Map< String, Object > body =
                    PocClient.getClient()
                             .post( "/guest/files" )
                             .parameters( payload.get( "param" ) )
                             .files( Map.of( "file_file", uploadedFile ) )
                             .execute( true )
                             .is4xxCode()
                             .getBodyAsMap();
        }
    }


    private List< Map< String, Map< String, Object > > > dp_create_success() {
        UploadedFile uploadedFile = new UploadedFileImpl();
        uploadedFile.setContent( new byte[]{1, 4, 2, 5, 1} );
        uploadedFile.setContentType( "application/pdf" );
        uploadedFile.setName( "name" );

        return List.of(
                Map.of(
                        "param", Map.of(
                                "file_name", "File 1",
                                "file_encrypted", true,
                                "file_folder_id", 1
                        ),
                        "file", Map.of(
                                "file_file", uploadedFile
                        )
                ),
                Map.of(
                        "param", Map.of(
                                "file_name", "File 2",
                                "file_encrypted", true
                        ),
                        "file", Map.of(
                                "file_file", uploadedFile
                        )
                )
        );
    }


    private List< Map< String, Map< String, Object > > > dp_create_fail() {
        UploadedFile uploadedFile = new UploadedFileImpl();
        uploadedFile.setContent( new byte[]{1, 4, 2, 5, 1} );
        uploadedFile.setContentType( "application/pdf" );
        uploadedFile.setName( "name" );

        return List.of(
                Map.of(
                        "param", Map.of(
                                "file_folder_id", 1,
                                "file_encrypted", true
                        ),
                        "file", Map.of(
                                "file_file", uploadedFile
                        )
                ),
                Map.of(
                        "param", Map.of(
                                "file_name", "",
                                "file_encrypted", true,
                                "file_folder_id", 1
                        ),
                        "file", Map.of(
                                "file_file", uploadedFile
                        )
                ),
                Map.of(
                        "param", Map.of(
                                "file_name", "  ",
                                "file_encrypted", true,
                                "file_folder_id", 1
                        ),
                        "file", Map.of(
                                "file_file", uploadedFile
                        )
                )
        );
    }
}
