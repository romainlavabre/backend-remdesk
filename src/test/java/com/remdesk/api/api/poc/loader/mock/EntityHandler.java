package com.remdesk.api.api.poc.loader.mock;

import com.remdesk.api.configuration.TopConfig;

import java.util.Set;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class EntityHandler extends com.remdesk.api.api.poc.kernel.entity.EntityHandler {

    protected Set< Class< ? > > getTypesAnnotated() {
        return TopConfig.getSubscribers();
    }
}
