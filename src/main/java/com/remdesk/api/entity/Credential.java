package com.remdesk.api.entity;

import com.remdesk.api.api.json.annotation.Group;
import com.remdesk.api.api.json.annotation.Json;
import com.remdesk.api.api.poc.annotation.*;
import com.remdesk.api.configuration.json.GroupType;
import com.remdesk.api.configuration.response.Message;
import com.remdesk.api.exception.HttpUnprocessableEntityException;
import com.remdesk.api.repository.CredentialRepository;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@PocEnabled( repository = CredentialRepository.class )
@Entity
public class Credential {

    @EntryPoint(
            getOne = @GetOne( enabled = true, authenticated = false ),
            getAllBy = {@GetAllBy( entity = Card.class, authenticated = false )},
            post = @Post( fields = {"name", "username", "password", "link", "card"}, authenticated = false ),
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

    @EntryPoint(
            patch = @Patch( authenticated = false )
    )
    @Json( groups = {
            @Group( name = GroupType.GUEST )
    } )
    @Column( nullable = false, length = 600 )
    private String username;

    @EntryPoint(
            patch = @Patch( authenticated = false )
    )
    @Json( groups = {
            @Group( name = GroupType.GUEST )
    } )
    @Column( nullable = false, columnDefinition = "TEXT" )
    private String password;

    @EntryPoint(
            patch = @Patch( authenticated = false )
    )
    @Json( groups = {
            @Group( name = GroupType.GUEST )
    } )
    private String link;

    @Json( groups = {
            @Group( name = GroupType.GUEST, key = "created_at" )
    } )
    @Column( name = "created_at", nullable = false )
    private final ZonedDateTime createdAt;

    @EntryPoint(
            patch = @Patch( authenticated = false )
    )
    @Json( groups = {
            @Group( name = GroupType.GUEST, object = true, key = "card_id" )
    } )
    @ManyToOne( cascade = {CascadeType.PERSIST} )
    @JoinColumn( name = "card_id", nullable = false )
    private Card card;


    public Credential() {
        createdAt = ZonedDateTime.now( ZoneId.of( "UTC" ) );
    }


    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public Credential setName( String name ) {
        if ( name == null || name.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.CREDENTIAL_NAME_REQUIRED );
        }

        if ( name.length() > 255 ) {
            throw new HttpUnprocessableEntityException( Message.CREDENTIAL_NAME_TOO_LONG );
        }

        this.name = name;

        return this;
    }


    public String getUsername() {
        return username;
    }


    public Credential setUsername( String username ) {
        if ( username == null || username.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.CREDENTIAL_USERNAME_REQUIRED );
        }

        if ( username.length() > 600 ) {
            throw new HttpUnprocessableEntityException( Message.CREDENTIAL_USERNAME_TOO_LONG );
        }

        this.username = username;

        return this;
    }


    public String getPassword() {
        return password;
    }


    public Credential setPassword( String password ) {
        if ( password == null || password.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.CREDENTIAL_PASSWORD_REQUIRED );
        }

        this.password = password;

        return this;
    }


    public String getLink() {
        return link;
    }


    public Credential setLink( String link ) {
        this.link = link;

        return this;
    }


    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }


    public Card getCard() {
        return card;
    }


    public Credential setCard( Card card ) {
        if ( card == null ) {
            throw new HttpUnprocessableEntityException( Message.CREDENTIAL_CARD_REQUIRED );
        }

        this.card = card;

        if ( !card.getCredentials().contains( this ) ) {
            card.addCredential( this );
        }

        return this;
    }
}
