package com.remdesk.api.configuration;

import com.remdesk.api.entity.Card;

import java.util.Set;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class TopConfig {

    public static Set< Class< ? > > getSubscribers() {
        return Set.of(
                Card.class
        );
    }
}
