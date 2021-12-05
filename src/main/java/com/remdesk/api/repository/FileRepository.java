package com.remdesk.api.repository;

import com.remdesk.api.entity.File;
import org.springframework.stereotype.Service;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public interface FileRepository extends DefaultRepository< File > {
}
