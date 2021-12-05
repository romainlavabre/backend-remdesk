package com.remdesk.api.configuration;

import com.remdesk.api.entity.Card;
import com.remdesk.api.entity.Credential;
import com.remdesk.api.entity.File;
import com.remdesk.api.entity.Folder;

import java.util.Set;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class TopConfig {

    public static Set< Class< ? > > getSubscribers() {
        return Set.of(
                Card.class,
                Credential.class,
                Folder.class,
                File.class
        );
    }
}
