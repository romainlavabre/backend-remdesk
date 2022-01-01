package com.remdesk.api.parameter;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface StorageParameter {
    String PREFIX                 = "storage_";
    String CLIENT_ID              = PREFIX + "client_id";
    String CLIENT_SECRET          = PREFIX + "client_secret";
    String COMPARTMENT            = PREFIX + "compartment";
    String ZONE                   = PREFIX + "zone";
    String DEFAULT_COMMAND        = PREFIX + "default_command";
    String CUSTOM_COMMANDS        = PREFIX + "custom_commands";
    String PRESERVE_NETWORK_LEVEL = PREFIX + "preserve_network_level";
}
