package com.remdesk.api.repository.jpa;

import com.remdesk.api.entity.File;
import com.remdesk.api.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Repository
public interface FileJpa extends JpaRepository< File, Long > {

    List< File > findAllByFolder( Folder folder );


    Optional< File > findByPath( String path );
}
