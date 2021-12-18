package com.remdesk.api.configuration.response;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface Message {

    String CARD_NAME_REQUIRED                                        = "CARD_NAME_REQUIRED";
    String CARD_NAME_TOO_LONG                                        = "CARD_NAME_TOO_LONG";
    String CREDENTIAL_NAME_REQUIRED                                  = "CREDENTIAL_NAME_REQUIRED";
    String CREDENTIAL_NAME_TOO_LONG                                  = "CREDENTIAL_NAME_TOO_LONG";
    String CREDENTIAL_USERNAME_REQUIRED                              = "CREDENTIAL_USERNAME_REQUIRED";
    String CREDENTIAL_USERNAME_TOO_LONG                              = "CREDENTIAL_USERNAME_TOO_LONG";
    String CREDENTIAL_PASSWORD_REQUIRED                              = "CREDENTIAL_PASSWORD_REQUIRED";
    String CREDENTIAL_CARD_REQUIRED                                  = "CREDENTIAL_CARD_REQUIRED";
    String FOLDER_NAME_REQUIRED                                      = "FOLDER_NAME_REQUIRED";
    String FOLDER_NAME_TOO_LONG                                      = "FOLDER_NAME_TOO_LONG";
    String FILE_NAME_REQUIRED                                        = "FILE_NAME_REQUIRED";
    String FILE_NAME_TOO_LONG                                        = "FILE_NAME_TOO_LONG";
    String FILE_CONTENT_TYPE_REQUIRED                                = "FILE_CONTENT_TYPE_REQUIRED";
    String FILE_PATH_REQUIRED                                        = "FILE_PATH_REQUIRED";
    String FILE_SIZE_REQUIRED                                        = "FILE_SIZE_REQUIRED";
    String FILE_FILE_REQUIRED                                        = "FILE_FILE_REQUIRED";
    String FILE_UNABLE_TO_MOVE                                       = "FILE_UNABLE_TO_MOVE";
    String FILE_UNABLE_TO_REMOVE                                     = "FILE_UNABLE_TO_REMOVE";
    String FILE_UNABLE_TO_UPLOAD                                     = "FILE_UNABLE_TO_UPLOAD";
    String FILE_ALREADY_EXISTS                                       = "FILE_ALREADY_EXISTS";
    String DATABASE_CONFIGURATION_USERNAME_REQUIRED                  = "DATABASE_CONFIGURATION_USERNAME_REQUIRED";
    String DATABASE_CONFIGURATION_SOFTWARE_REQUIRED                  = "DATABASE_CONFIGURATION_SOFTWARE_REQUIRED";
    String DATABASE_CONFIGURATION_PORT_REQUIRED                      = "DATABASE_CONFIGURATION_PORT_REQUIRED";
    String DATABASE_CONFIGURATION_HOST_REQUIRED                      = "DATABASE_CONFIGURATION_HOST_REQUIRED";
    String DATABASE_CONFIGURATION_NO_CONNECTION_ESTABLISHED          = "DATABASE_CONFIGURATION_NO_CONNECTION_ESTABLISHED";
    String STORAGE_CONFIGURATION_ZONE_REQUIRED                       = "STORAGE_CONFIGURATION_ZONE_REQUIRED";
    String STORAGE_CONFIGURATION_COMPARTMENT_REQUIRED                = "STORAGE_CONFIGURATION_COMPARTMENT_REQUIRED";
    String STORAGE_CONFIGURATION_CLIENT_SECRET_REQUIRED              = "STORAGE_CONFIGURATION_CLIENT_SECRET_REQUIRED";
    String STORAGE_CONFIGURATION_CLIENT_ID_REQUIRED                  = "STORAGE_CONFIGURATION_CLIENT_ID_REQUIRED";
    String FILE_SOFTWARE_USER_CONFIGURATION_DEFAULT_COMMAND_REQUIRED = "FILE_SOFTWARE_USER_CONFIGURATION_DEFAULT_COMMAND_REQUIRED";
    String FILE_SOFTWARE_USER_CONFIGURATION_CONTENT_TYPE_REQUIRED    = "FILE_SOFTWARE_USER_CONFIGURATION_CONTENT_TYPE_REQUIRED";
    String FILE_SOFTWARE_USER_CONFIGURATION_COMMAND_REQUIRED         = "FILE_SOFTWARE_USER_CONFIGURATION_COMMAND_REQUIRED";
}
