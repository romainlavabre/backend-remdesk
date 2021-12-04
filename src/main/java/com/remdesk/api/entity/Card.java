package com.remdesk.api.entity;

import com.remdesk.api.api.poc.annotation.*;
import com.remdesk.api.configuration.response.Message;
import com.remdesk.api.exception.HttpUnprocessableEntityException;
import com.remdesk.api.repository.CardRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @EntryPoint(
            patch = @Patch( authenticated = false )
    )
    private String name;

    private final ZonedDateTime createdAt;


    public Card() {
        createdAt = ZonedDateTime.now( ZoneId.of( "UTC" ) );
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
}
