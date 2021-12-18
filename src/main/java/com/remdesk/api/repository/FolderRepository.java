package com.remdesk.api.repository;

import com.remdesk.api.entity.Folder;

import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface FolderRepository extends DefaultRepository< Folder > {

    List< Folder > findAllByParent( Folder folder );


    List< Folder > findAllByRoot();
}
