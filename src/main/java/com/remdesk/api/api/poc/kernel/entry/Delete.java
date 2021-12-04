package com.remdesk.api.api.poc.kernel.entry;

import com.remdesk.api.api.history.HistoryHandler;
import com.remdesk.api.api.poc.kernel.entity.EntityHandler;
import com.remdesk.api.api.poc.kernel.router.RouteHandler;
import com.remdesk.api.api.request.Request;
import com.remdesk.api.repository.DefaultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class Delete implements DeleteEntry {

    protected final HistoryHandler historyHandler;
    protected final EntityHandler  entityHandler;


    public Delete(
            HistoryHandler historyHandler,
            EntityHandler entityHandler ) {
        this.historyHandler = historyHandler;
        this.entityHandler  = entityHandler;
    }


    @Override
    public void delete( Request request, Object subject, RouteHandler.Route route )
            throws Throwable {
        delete( request, subject, route.getTriggers() );
    }


    protected void delete( Request request, Object subject, List< com.remdesk.api.api.poc.annotation.Trigger > nextTriggers )
            throws Throwable {
        delete( request, subject, nextTriggers, null );
    }


    protected void delete( Request request, Object subject, List< com.remdesk.api.api.poc.annotation.Trigger > nextTriggers, Object executor )
            throws Throwable {

        if ( executor != null ) {
            (( com.remdesk.api.api.crud.Delete ) executor).delete( request, subject );
        } else {
            EntityHandler.Entity entity = entityHandler.getEntity( subject.getClass() );
            historyHandler.delete( subject );

            DefaultRepository defaultRepository = entity.getDefaultRepository();
            defaultRepository.remove( subject );
        }


        Trigger.getInstance().handleTriggers( request, nextTriggers, subject );
    }
}
