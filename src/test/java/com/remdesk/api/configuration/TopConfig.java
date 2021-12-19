package com.remdesk.api.configuration;

import com.remdesk.api.configuration.poc.Subject;

import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class TopConfig {

    public static List< Class< ? > > getSubscribers() {
        return Subject.getSubject();
    }
}
