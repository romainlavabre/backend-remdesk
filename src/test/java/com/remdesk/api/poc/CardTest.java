package com.remdesk.api.poc;

import com.remdesk.api.api.poc.client.PocClient;
import com.remdesk.api.entity.Card;
import com.remdesk.api.repository.CardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class CardTest {

    @Test
    public void getAll() {
        CardRepository cardRepository = PocClient.getMocker().getMock( CardRepository.class );

        Mockito.when( cardRepository.findAll() ).thenReturn( List.of( new Card() ) );

        PocClient.getClient()
                 .get( "/guest/cards" )
                 .execute()
                 .is2xxCode();
    }


    @Test
    public void getOne() {
        CardRepository cardRepository = PocClient.getMocker().getMock( CardRepository.class );

        Mockito.when( cardRepository.findOrFail( Mockito.anyLong() ) ).thenReturn( new Card() );

        PocClient.getClient()
                 .get( "/guest/cards/1" )
                 .execute()
                 .is2xxCode();
    }


    @Test
    public void create() {
        Map< String, Object > payload =
                PocClient.getClient()
                         .post( "/guest/cards" )
                         .parameters( Map.of(
                                 "card_name", "Remdesk"
                         ) )
                         .execute()
                         .is2xxCode()
                         .getBodyAsMap();

        Assertions.assertTrue( payload.containsKey( "name" ) );
        Assertions.assertEquals( payload.get( "name" ), "Remdesk" );
    }


    @Test
    public void updateName() {
        Card card = new Card();
        card.setName( "Remdesk" );

        CardRepository cardRepository = PocClient.getMocker().getMock( CardRepository.class );
        Mockito.when( cardRepository.findOrFail( 1L ) ).thenReturn( card );

        PocClient.getClient()
                 .patch( "/guest/cards/1/name" )
                 .parameters( Map.of(
                         "card_name", "update"
                 ) )
                 .execute( true )
                 .is2xxCode();

        Assertions.assertEquals( card.getName(), "update" );
    }
}
