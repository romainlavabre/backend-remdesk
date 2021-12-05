package com.remdesk.api.entity;

import com.remdesk.api.api.json.annotation.Group;
import com.remdesk.api.api.json.annotation.Json;
import com.remdesk.api.api.poc.annotation.*;
import com.remdesk.api.configuration.json.GroupType;
import com.remdesk.api.configuration.response.Message;
import com.remdesk.api.exception.HttpUnprocessableEntityException;
import com.remdesk.api.repository.CardRepository;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@PocEnabled( repository = CardRepository.class )
@Entity
public class Card {

    @EntryPoint(
            getOne = @GetOne( enabled = true, authenticated = false ),
            getAll = @GetAll( enabled = true, authenticated = false ),
            post = @Post( fields = {"name"}, authenticated = false ),
            delete = @Delete( authenticated = false )
    )
    @Json( groups = {
            @Group( name = GroupType.GUEST )
    } )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @EntryPoint(
            patch = @Patch( authenticated = false )
    )
    @Json( groups = {
            @Group( name = GroupType.GUEST )
    } )
    @Column( nullable = false )
    private String name;

    @Json( groups = {
            @Group( name = GroupType.GUEST )
    } )
    @Column( name = "created_at", nullable = false )
    private final ZonedDateTime createdAt;

    @Json( groups = {
            @Group( name = GroupType.GUEST, object = true, key = "credentials_id" )
    } )
    @OneToMany( cascade = {CascadeType.PERSIST}, mappedBy = "card" )
    private final List< Credential > credentials;


    public Card() {
        credentials = new ArrayList<>();
        createdAt   = ZonedDateTime.now( ZoneId.of( "UTC" ) );
    }


    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public Card setName( String name ) {
        if ( name == null || name.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.CARD_NAME_REQUIRED );
        }

        if ( name.length() > 255 ) {
            throw new HttpUnprocessableEntityException( Message.CARD_NAME_TOO_LONG );
        }

        this.name = name;

        return this;
    }


    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }


    public List< Credential > getCredentials() {
        return credentials;
    }


    public Card addCredential( Credential credential ) {
        if ( !credentials.contains( credential ) ) {
            credentials.add( credential );

            if ( credential.getCard() != this ) {
                credential.setCard( this );
            }
        }

        return this;
    }
}
