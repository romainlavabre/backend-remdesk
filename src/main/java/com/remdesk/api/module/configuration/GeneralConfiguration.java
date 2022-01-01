package com.remdesk.api.module.configuration;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public class GeneralConfiguration {
    public static final int PRESERVE_NETWORK_LEVEL_0 = 0;
    public static final int PRESERVE_NETWORK_LEVEL_1 = 1;
    public static final int PRESERVE_NETWORK_LEVEL_2 = 2;

    private static GeneralConfiguration generalConfiguration;

    private String encryptionKey;

    private int preserveNetworkLevel;


    public GeneralConfiguration() {
        preserveNetworkLevel = PRESERVE_NETWORK_LEVEL_0;
    }


    public String getEncryptionKey() {
        return encryptionKey;
    }


    public GeneralConfiguration setEncryptionKey( String encryptionKey ) {
        this.encryptionKey = encryptionKey;

        return this;
    }


    public static GeneralConfiguration getInstance() {
        return generalConfiguration;
    }
}
