package com.remdesk.api.configuration.poc;

import com.remdesk.api.entity.Card;
import com.remdesk.api.entity.Credential;
import com.remdesk.api.entity.File;
import com.remdesk.api.entity.Folder;

import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class Subject {

    /**
     * @return List of subject with @PocEnabled annotation
     */
    public static List< Class< ? > > getSubject() {
        return List.of(
                Card.class,
                Credential.class,
                File.class,
                Folder.class
        );
    }
}
