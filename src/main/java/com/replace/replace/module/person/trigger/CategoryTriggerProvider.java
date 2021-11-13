package com.replace.replace.module.person.trigger;

import com.replace.replace.api.top.api.ResourceProvider;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class CategoryTriggerProvider implements ResourceProvider {


    @Override
    public List< Object > getResources( Object subject ) {
        return List.of( subject );
    }
}
