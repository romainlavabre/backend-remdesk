package com.remdesk.api.module.file.reader;

import com.remdesk.api.entity.File;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface ReaderHandler {
    void openFile( File file );
}
