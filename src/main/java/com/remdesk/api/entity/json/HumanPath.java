package com.remdesk.api.entity.json;

import com.remdesk.api.api.json.put.Put;
import com.remdesk.api.entity.File;
import com.remdesk.api.module.file.PathResolver;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class HumanPath implements Put {

    @Override
    public Object build( Object object ) {
        return PathResolver.getHumanPath( ( File ) object );
    }
}
