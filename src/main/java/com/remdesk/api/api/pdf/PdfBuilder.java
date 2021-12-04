package com.remdesk.api.api.pdf;

import java.io.File;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface PdfBuilder {
    File build( String html );
}
