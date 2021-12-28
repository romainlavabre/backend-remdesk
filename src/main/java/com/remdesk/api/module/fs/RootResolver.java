package com.remdesk.api.module.fs;

import com.remdesk.api.api.environment.Environment;
import com.remdesk.api.configuration.environment.Variable;
import org.springframework.stereotype.Service;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class RootResolver {

    protected final Environment environment;


    public RootResolver( Environment environment ) {
        this.environment = environment;
    }


    public String getDir() {
        return environment.getEnv( Variable.HIDDEN_FILENAME );
    }
}
