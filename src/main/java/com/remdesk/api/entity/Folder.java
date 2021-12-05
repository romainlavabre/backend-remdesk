package com.remdesk.api.entity;

import com.remdesk.api.api.json.annotation.Group;
import com.remdesk.api.api.json.annotation.Json;
import com.remdesk.api.api.poc.annotation.*;
import com.remdesk.api.configuration.json.GroupType;
import com.remdesk.api.configuration.response.Message;
import com.remdesk.api.exception.HttpUnprocessableEntityException;
import com.remdesk.api.repository.FolderRepository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@PocEnabled( repository = FolderRepository.class )
@Entity
public class Folder {

    @EntryPoint(
            getOne = @GetOne( enabled = true, authenticated = false ),
            getAllBy = @GetAllBy( entity = Folder.class, method = "findAllByParent", route = "/by/parent/{id:[0-9]+}", authenticated = false ),
            post = @Post( fields = {"name", "parent"}, authenticated = false ),
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
            @Group( name = GroupType.GUEST, object = true, key = "children_id" )
    } )
    @OneToMany( cascade = {CascadeType.PERSIST}, mappedBy = "parent" )
    private final List< Folder > children;

    @EntryPoint(
            patch = @Patch( authenticated = false )
    )
    @RequestParameter( name = "folder_parent_id" )
    @Json( groups = {
            @Group( name = GroupType.GUEST, object = true, key = "parent_id" )
    } )
    @ManyToOne
    @JoinColumn( name = "parent_id" )
    private Folder parent;


    public Folder() {
        children = new ArrayList<>();
    }


    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public Folder setName( String name ) {
        if ( name == null || name.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.FOLDER_NAME_REQUIRED );
        }

        if ( name.length() > 255 ) {
            throw new HttpUnprocessableEntityException( Message.FOLDER_NAME_TOO_LONG );
        }

        this.name = name;

        return this;
    }


    public List< Folder > getChildren() {
        return children;
    }


    public Folder addChild( Folder folder ) {
        if ( !children.contains( folder ) ) {
            children.add( folder );

            if ( folder.getParent() != this ) {
                folder.setParent( this );
            }
        }

        return this;
    }


    public Folder getParent() {
        return parent;
    }


    public Folder setParent( Folder parent ) {
        this.parent = parent;

        if ( parent != null && !parent.getChildren().contains( this ) ) {
            parent.addChild( this );
        }

        return this;
    }
}
