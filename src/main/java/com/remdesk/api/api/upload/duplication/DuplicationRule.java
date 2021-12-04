package com.remdesk.api.api.upload.duplication;

import com.remdesk.api.api.upload.UploadedFile;
import com.remdesk.api.api.upload.exception.DuplicationException;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface DuplicationRule {

    /**
     * You can rename this file or throw an DuplicationException
     *
     * @param uploadedFile
     */
    void exec( UploadedFile uploadedFile )
            throws DuplicationException;
}
