package com.remdesk.api.repository.jpa;

import com.remdesk.api.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Repository
public interface FolderJpa extends JpaRepository< Folder, Long > {

    List< Folder > findAllByParent( Folder folder );
}
