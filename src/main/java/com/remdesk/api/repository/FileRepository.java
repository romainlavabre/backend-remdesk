package com.remdesk.api.repository;

import com.remdesk.api.entity.File;
import com.remdesk.api.entity.Folder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public interface FileRepository extends DefaultRepository< File > {
    List< File > findAllByFolder( Folder folder );


    Optional< File > findByPath( String path );


    List< File > findAllByRoot();
}
