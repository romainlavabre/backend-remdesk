package com.remdesk.api.api.poc.kernel.entry;

import com.remdesk.api.api.json.Encoder;
import com.remdesk.api.api.poc.annotation.*;
import com.remdesk.api.api.poc.kernel.entity.EntityHandler;
import com.remdesk.api.api.poc.kernel.exception.NoRouteMatchException;
import com.remdesk.api.api.poc.kernel.router.RouteHandler;
import com.remdesk.api.api.request.Request;
import com.remdesk.api.api.storage.data.DataStorageHandler;
import com.remdesk.api.configuration.json.GroupType;
import com.remdesk.api.repository.DefaultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class Controller {

    protected final Logger logger = LoggerFactory.getLogger( this.getClass() );

    protected final CreateEntry        createEntry;
    protected final UpdateEntry        updateEntry;
    protected final DeleteEntry        deleteEntry;
    protected final RouteHandler       routeHandler;
    protected final EntityHandler      entityHandler;
    protected final DataStorageHandler dataStorageHandler;
    protected final Request            request;
    protected final ApplicationContext applicationContext;


    public Controller(
            CreateEntry createEntry,
            UpdateEntry updateEntry,
            DeleteEntry deleteEntry,
            RouteHandler routeHandler,
            EntityHandler entityHandler,
            DataStorageHandler dataStorageHandler,
            Request request,
            ApplicationContext applicationContext ) {
        this.createEntry        = createEntry;
        this.updateEntry        = updateEntry;
        this.deleteEntry        = deleteEntry;
        this.routeHandler       = routeHandler;
        this.entityHandler      = entityHandler;
        this.dataStorageHandler = dataStorageHandler;
        this.request            = request;
        this.applicationContext = applicationContext;
    }


    public ResponseEntity< Map< String, Object > > getOne( @PathVariable( "id" ) long id )
            throws NoRouteMatchException,
                   IllegalAccessException {
        RouteHandler.Route route = routeHandler.getRoute( request, GetOne.class );

        DefaultRepository< ? > defaultRepository = entityHandler.getEntity( route.getSubject() ).getDefaultRepository();

        return ResponseEntity.ok(
                Encoder.encode( defaultRepository.findOrFail( id ), getGroup( route.getRole() ) )
        );
    }


    public ResponseEntity< List< Map< String, Object > > > getAll()
            throws NoRouteMatchException,
                   IllegalAccessException {
        RouteHandler.Route route = routeHandler.getRoute( request, GetAll.class );

        DefaultRepository< ? > defaultRepository = applicationContext.getBean( route.getRepository() );

        return ResponseEntity.ok(
                Encoder.encode( defaultRepository.findAll(), getGroup( route.getRole() ) )
        );
    }


    public ResponseEntity< Map< String, Object > > getOneBy( @PathVariable( "id" ) long id )
            throws Throwable {
        RouteHandler.Route route = routeHandler.getRoute( request, GetOneBy.class );

        DefaultRepository< ? > relationRepository = entityHandler.getEntity( (( GetOneBy ) route.getHttpType()).entity() ).getDefaultRepository();

        Object relation = relationRepository.findOrFail( id );

        DefaultRepository< ? > defaultRepository = applicationContext.getBean( route.getRepository() );

        Method method = defaultRepository.getClass().getDeclaredMethod( route.getRepositoryMethod(), (( GetOneBy ) route.getHttpType()).entity() );

        try {
            return ResponseEntity.ok(
                    Encoder.encode( method.invoke( defaultRepository, relation ), getGroup( route.getRole() ) )
            );
        } catch ( Throwable throwable ) {
            throw throwable.getCause();
        }
    }


    public ResponseEntity< List< Map< String, Object > > > getAllBy( @PathVariable( "id" ) long id )
            throws Throwable {
        RouteHandler.Route route = routeHandler.getRoute( request, GetAllBy.class );

        DefaultRepository< ? > relationRepository = applicationContext.getBean( entityHandler.getEntity( (( GetAllBy ) route.getHttpType()).entity() ).getRepository() );

        Object relation = relationRepository.findOrFail( id );

        DefaultRepository< ? > defaultRepository = applicationContext.getBean( route.getRepository() );

        Method method = defaultRepository.getClass().getDeclaredMethod( route.getRepositoryMethod(), (( GetAllBy ) route.getHttpType()).entity() );

        try {
            return ResponseEntity.ok(
                    Encoder.encode( ( List< ? extends Object > ) method.invoke( defaultRepository, relation ), getGroup( route.getRole() ) )
            );
        } catch ( Throwable throwable ) {
            throw throwable.getCause();
        }
    }


    @Transactional
    public ResponseEntity< Map< String, Object > > post()
            throws Throwable {
        RouteHandler.Route route = routeHandler.getRoute( request, Post.class );

        Object subject = route.getSubject().getDeclaredConstructor().newInstance();

        createEntry.create( request, subject, route );

        dataStorageHandler.save();

        return ResponseEntity.ok(
                Encoder.encode( subject, getGroup( route.getRole() ) )
        );
    }


    @Transactional
    public ResponseEntity< Map< String, Object > > put( @PathVariable( "id" ) long id )
            throws Throwable {
        RouteHandler.Route route = routeHandler.getRoute( request, Put.class );

        DefaultRepository< ? > defaultRepository = entityHandler.getEntity( route.getSubject() ).getDefaultRepository();
        Object                 subject           = defaultRepository.findOrFail( id );

        updateEntry.update( request, subject, route );

        dataStorageHandler.save();

        return ResponseEntity.ok(
                Encoder.encode( subject, getGroup( route.getRole() ) )
        );
    }


    @Transactional
    public ResponseEntity< Void > patch( @PathVariable( "id" ) long id )
            throws Throwable {
        RouteHandler.Route route = routeHandler.getRoute( request, Patch.class );

        DefaultRepository< ? > defaultRepository = entityHandler.getEntity( route.getSubject() ).getDefaultRepository();
        Object                 subject           = defaultRepository.findOrFail( id );

        updateEntry.update( request, subject, route );

        dataStorageHandler.save();

        return ResponseEntity.noContent().build();
    }


    @Transactional
    public ResponseEntity< Void > delete( @PathVariable( "id" ) long id )
            throws Throwable {
        RouteHandler.Route route = routeHandler.getRoute( request, GetOne.class );

        DefaultRepository< ? > defaultRepository = applicationContext.getBean( route.getRepository() );

        Object subject = defaultRepository.findOrFail( id );

        deleteEntry.delete( request, subject, route );

        dataStorageHandler.save();

        return ResponseEntity.noContent().build();
    }


    private String getGroup( String role ) throws IllegalAccessException {
        Field field;

        if ( role == null ) {
            return "GUEST";
        }
        
        try {
            field = GroupType.class.getDeclaredField( role.replaceFirst( "ROLE_", "" ).toUpperCase() );
        } catch ( NoSuchFieldException e ) {
            logger.error( "No json group found for " + role );
            return GroupType.DEFAULT;
        }

        return String.valueOf( field.get( null ) );
    }
}
