package com.remdesk.api.entity;

import com.remdesk.api.api.json.annotation.Group;
import com.remdesk.api.api.json.annotation.Json;
import com.remdesk.api.api.json.annotation.JsonPut;
import com.remdesk.api.api.json.annotation.Row;
import com.remdesk.api.api.poc.annotation.*;
import com.remdesk.api.configuration.json.GroupType;
import com.remdesk.api.configuration.poc.TriggerIdentifier;
import com.remdesk.api.configuration.response.Message;
import com.remdesk.api.entity.json.HumanPath;
import com.remdesk.api.exception.HttpUnprocessableEntityException;
import com.remdesk.api.module.file.DeleteFile;
import com.remdesk.api.module.file.MoveFile;
import com.remdesk.api.module.file.UploadFile;
import com.remdesk.api.repository.FileRepository;

import javax.persistence.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@PocEnabled( repository = FileRepository.class )
@JsonPut( group = {
        @Group( name = GroupType.GUEST, row = {
                @Row( key = "human_path", handler = HumanPath.class )
        } )
} )
@Entity
public class File {

    @EntryPoint(
            getOne = @GetOne( enabled = true, authenticated = false ),
            getAllBy = {@GetAllBy( entity = Folder.class, authenticated = false )},
            post = @Post(
                    fields = {"name", "folder"},
                    authenticated = false,
                    triggers = {@Trigger( triggerId = TriggerIdentifier.FILE_UPLOAD_FILE, provideMe = true )}
            ),
            delete = @Delete( authenticated = false, triggers = {@Trigger( triggerId = TriggerIdentifier.FILE_DELETE_FILE, provideMe = true )} ),
            unmanagedTriggers = {
                    @UnmanagedTrigger( id = TriggerIdentifier.FILE_UPLOAD_FILE, createExecutor = UploadFile.class ),
                    @UnmanagedTrigger( id = TriggerIdentifier.FILE_MOVE_FILE, updateExecutor = MoveFile.class ),
                    @UnmanagedTrigger( id = TriggerIdentifier.FILE_DELETE_FILE, deleteExecutor = DeleteFile.class )
            }
    )
    @Json( groups = {
            @Group( name = GroupType.GUEST )
    } )
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @EntryPoint(
            patch = @Patch( authenticated = false, triggers = {@Trigger( triggerId = TriggerIdentifier.FILE_MOVE_FILE, provideMe = true )} )
    )
    @Json( groups = {
            @Group( name = GroupType.GUEST )
    } )
    @Column( nullable = false )
    private String name;

    @Json( groups = {
            @Group( name = GroupType.GUEST, key = "content_type" )
    } )
    @Column( name = "content_type", nullable = false )
    private String contentType;

    @Json( groups = {
            @Group( name = GroupType.GUEST )
    } )
    @Column( nullable = false )
    private String path;

    @Json( groups = {
            @Group( name = GroupType.GUEST )
    } )
    private long size;

    @Json( groups = {
            @Group( name = GroupType.GUEST, key = "created_at" )
    } )
    @Column( name = "created_at", nullable = false )
    private final ZonedDateTime createdAt;

    @EntryPoint(
            patch = @Patch( authenticated = false, triggers = {@Trigger( triggerId = TriggerIdentifier.FILE_MOVE_FILE, provideMe = true )} )
    )
    @Json( groups = {
            @Group( name = GroupType.GUEST, object = true, key = "folder_id" )
    } )
    @ManyToOne( cascade = {CascadeType.PERSIST} )
    @JoinColumn( name = "folder_id" )
    private Folder folder;


    public File() {
        createdAt = ZonedDateTime.now( ZoneId.of( "UTC" ) );
    }


    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public File setName( String name ) {
        if ( name == null || name.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.FILE_NAME_REQUIRED );
        }

        if ( name.length() > 255 ) {
            throw new HttpUnprocessableEntityException( Message.FILE_NAME_TOO_LONG );
        }

        this.name = name;

        return this;
    }


    public String getContentType() {
        return contentType;
    }


    public File setContentType( String contentType ) {
        if ( contentType == null || contentType.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.FILE_CONTENT_TYPE_REQUIRED );
        }

        this.contentType = contentType;

        return this;
    }


    public String getPath() {
        return path;
    }


    public File setPath( String path ) {

        if ( path == null || path.isBlank() ) {
            throw new HttpUnprocessableEntityException( Message.FILE_PATH_REQUIRED );
        }

        this.path = path;

        return this;
    }


    public long getSize() {
        return size;
    }


    public File setSize( Long size ) {
        if ( size == null ) {
            throw new HttpUnprocessableEntityException( Message.FILE_SIZE_REQUIRED );
        }

        this.size = size;

        return this;
    }


    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }


    public Folder getFolder() {
        return folder;
    }


    public File setFolder( Folder folder ) {
        this.folder = folder;

        return this;
    }
}
