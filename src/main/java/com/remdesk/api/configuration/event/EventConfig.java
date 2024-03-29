package com.remdesk.api.configuration.event;

import com.remdesk.api.api.event.EventSubscriber;
import com.remdesk.api.api.event.annotation.Subscribers;
import com.remdesk.api.api.event.annotation.UnitEvent;
import com.remdesk.api.api.history.HistoryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class EventConfig implements Event {

    protected HistoryHandler historyHandler;


    public EventConfig(
            final HistoryHandler historyHandler ) {
        this.historyHandler = historyHandler;
    }


    @UnitEvent( name = TRANSACTION_SUCCESS )
    public Map< String, Class > transactionSuccess() {
        return new HashMap<>();
    }


    @Subscribers( event = TRANSACTION_SUCCESS )
    public List< EventSubscriber > subscribersTransactionSuccess() {
        final List< EventSubscriber > list = new ArrayList<>();

        list.add( this.historyHandler );

        return list;
    }
}
