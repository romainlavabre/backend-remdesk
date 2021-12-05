package com.remdesk.api.poc;

import com.remdesk.api.api.poc.client.PocClient;
import com.remdesk.api.entity.Folder;
import com.remdesk.api.repository.FolderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class FolderTest {

    @Test
    public void getOne() {
        FolderRepository folderRepository = PocClient.getMocker().getMock( FolderRepository.class );

        Mockito.when( folderRepository.findOrFail( Mockito.anyLong() ) ).thenReturn( new Folder() );

        PocClient.getClient()
                 .get( "/guest/folders/1" )
                 .execute()
                 .is2xxCode();
    }


    @Test
    public void getAllByParent() {
        FolderRepository folderRepository = PocClient.getMocker().getMock( FolderRepository.class );

        Mockito.when( folderRepository.findOrFail( Mockito.anyLong() ) ).thenReturn( new Folder() );
        Mockito.when( folderRepository.findAllByParent( Mockito.any( Folder.class ) ) ).thenReturn( List.of( new Folder() ) );

        PocClient.getClient()
                 .get( "/guest/folders/by/parent/1" )
                 .execute()
                 .is2xxCode();
    }


    @Test
    public void create_success() {

        Mockito.when( PocClient.getMocker().getMock( FolderRepository.class ).findOrFail( 1L ) ).thenReturn( new Folder() );

        for ( Map< String, Object > payload : dp_create_success() ) {
            Map< String, Object > body =
                    PocClient.getClient()
                             .post( "/guest/folders" )
                             .parameters( payload )
                             .execute()
                             .is2xxCode()
                             .getBodyAsMap();

            Assertions.assertEquals( body.get( "name" ), payload.get( "folder_name" ) );

            if ( payload.get( "folder_parent_id" ) != null ) {
                Assertions.assertNotNull( body.get( "parent_id" ) );
            }
        }
    }


    @Test
    public void create_fail() {

        Mockito.when( PocClient.getMocker().getMock( FolderRepository.class ).findOrFail( 1L ) ).thenReturn( new Folder() );

        for ( Map< String, Object > payload : dp_create_fail() ) {
            PocClient.getClient()
                     .post( "/guest/folders" )
                     .parameters( payload )
                     .execute()
                     .is4xxCode();
        }
    }


    private List< Map< String, Object > > dp_create_success() {
        return List.of(
                Map.of(
                        "folder_name", "Folder 1"
                ),
                Map.of(
                        "folder_name", "Folder 1",
                        "folder_parent_id", 1
                )
        );
    }


    private List< Map< String, Object > > dp_create_fail() {
        return List.of(
                Map.of(),
                Map.of(
                        "folder_name", "    "
                )
        );
    }
}
