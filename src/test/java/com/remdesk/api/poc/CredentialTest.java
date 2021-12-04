package com.remdesk.api.poc;

import com.remdesk.api.api.poc.client.PocClient;
import com.remdesk.api.api.poc.exception.HttpNotFoundException;
import com.remdesk.api.entity.Card;
import com.remdesk.api.entity.Credential;
import com.remdesk.api.repository.CardRepository;
import com.remdesk.api.repository.CredentialRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class CredentialTest {

    @Test
    public void getOne() {

        Mockito.when( PocClient.getMocker().getMock( CredentialRepository.class ).findOrFail( 1L ) ).thenReturn( new Credential() );

        PocClient.getClient()
                 .get( "/guest/credentials/1" )
                 .execute()
                 .is2xxCode();
    }


    @Test
    public void getAllByCard() {

        Mockito.when( PocClient.getMocker().getMock( CardRepository.class ).findOrFail( 1L ) )
               .thenReturn( new Card() );
        Mockito.when( PocClient.getMocker().getMock( CredentialRepository.class ).findAllByCard( Mockito.any( Card.class ) ) )
               .thenReturn( List.of( new Credential() ) );

        PocClient.getClient()
                 .get( "/guest/credentials/by/card/1" )
                 .execute()
                 .is2xxCode();
    }


    @Test
    public void create_success() {

        Mockito.when( PocClient.getMocker().getMock( CardRepository.class ).findOrFail( 1L ) ).thenReturn( new Card() );

        for ( Map< String, Object > payload : dp_create_success() ) {
            Map< String, Object > body =
                    PocClient.getClient()
                             .post( "/guest/credentials" )
                             .parameters( payload )
                             .execute()
                             .is2xxCode()
                             .getBodyAsMap();

            Assertions.assertEquals( body.get( "name" ), payload.get( "credential_name" ) );
            Assertions.assertEquals( body.get( "username" ), payload.get( "credential_username" ) );
            Assertions.assertEquals( body.get( "password" ), payload.get( "credential_password" ) );
            Assertions.assertNotNull( body.get( "card_id" ) );
        }
    }


    @Test
    public void create_fail() {

        CardRepository cardRepository = PocClient.getMocker().getMock( CardRepository.class );

        Mockito.when( cardRepository.findOrFail( 1L ) ).thenReturn( new Card() );
        Mockito.when( cardRepository.findOrFail( Mockito.isNull() ) ).thenThrow( HttpNotFoundException.class );

        for ( Map< String, Object > payload : dp_create_fail() ) {
            PocClient.getClient()
                     .post( "/guest/credentials" )
                     .parameters( payload )
                     .execute()
                     .is4xxCode();
        }
    }


    private List< Map< String, Object > > dp_create_success() {
        return List.of(
                Map.of(
                        "credential_name", "remdesk",
                        "credential_username", "remdesk",
                        "credential_password", "0000",
                        "credential_card_id", 1
                )
        );
    }


    private List< Map< String, Object > > dp_create_fail() {
        return List.of(
                Map.of(
                        "credential_username", "remdesk",
                        "credential_password", "0000",
                        "credential_card_id", 1
                ),
                Map.of(
                        "credential_name", "  ",
                        "credential_username", "remdesk",
                        "credential_password", "0000",
                        "credential_card_id", 1
                ),
                Map.of(
                        "credential_name", "remdesk",
                        "credential_password", "0000",
                        "credential_card_id", 1
                ),
                Map.of(
                        "credential_name", "remdesk",
                        "credential_username", "  ",
                        "credential_password", "0000",
                        "credential_card_id", 1
                ),
                Map.of(
                        "credential_name", "remdesk",
                        "credential_username", "remdesk",
                        "credential_card_id", 1
                ),
                Map.of(
                        "credential_name", "remdesk",
                        "credential_username", "remdesk",
                        "credential_password", "    ",
                        "credential_card_id", 1
                ),
                Map.of(
                        "credential_name", "remdesk",
                        "credential_username", "remdesk",
                        "credential_password", "0000"
                )
        );
    }
}
