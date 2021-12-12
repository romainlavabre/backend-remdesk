package com.remdesk.api.parameter;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface StorageParameter {
    String PREFIX        = "storage_";
    String CLIENT_ID     = PREFIX + "client_id";
    String CLIENT_SECRET = PREFIX + "client_secret";
    String COMPARTMENT   = PREFIX + "compartment";
    String ZONE          = PREFIX + "zone";
}
