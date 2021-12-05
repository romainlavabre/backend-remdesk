package com.remdesk.api.module.file;

import com.remdesk.api.entity.File;
import com.remdesk.api.entity.Folder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class PathResolver {

    public static String getPath( File file ) {
        List< String > parts = new ArrayList<>();
        parts.add( file.getName() );

        Folder folder = file.getFolder();

        while ( folder != null ) {
            parts.add( String.valueOf( folder.getId() ) );

            folder = folder.getParent();
        }

        Collections.reverse( parts );

        StringJoiner stringJoiner = new StringJoiner( "/" );

        for ( String part : parts ) {
            stringJoiner.add( part );
        }

        return stringJoiner.toString();
    }


    public static String getHumanPath( File file ) {
        List< String > parts = new ArrayList<>();
        parts.add( file.getName() );

        Folder folder = file.getFolder();

        while ( folder != null ) {
            parts.add( folder.getName() );

            folder = folder.getParent();
        }

        Collections.reverse( parts );

        StringJoiner stringJoiner = new StringJoiner( "/" );

        for ( String part : parts ) {
            stringJoiner.add( part );
        }

        return stringJoiner.toString();
    }
}
