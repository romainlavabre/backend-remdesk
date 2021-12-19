package com.remdesk.api.api.poc.loader.mock;

import com.remdesk.api.configuration.TopConfig;

import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class EntityHandler extends com.remdesk.api.api.poc.kernel.entity.EntityHandler {

    protected List< Class< ? > > getTypesAnnotated() {
        return TopConfig.getSubscribers();
    }
}
