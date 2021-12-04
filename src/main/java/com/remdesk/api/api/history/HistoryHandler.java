package com.remdesk.api.api.history;

import com.remdesk.api.api.event.EventSubscriber;

public interface HistoryHandler extends EventSubscriber {

    /**
     * Create a log for creation
     *
     * @param object
     */
    void create( Object object );


    /**
     * Create a log for update
     *
     * @param object
     * @param property
     */
    void update( Object object, String property );


    /**
     * Create log for deletion
     *
     * @param object
     */
    void delete( Object object );
}
