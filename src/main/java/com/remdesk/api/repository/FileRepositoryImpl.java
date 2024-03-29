package com.remdesk.api.repository;

import com.remdesk.api.entity.File;
import com.remdesk.api.entity.Folder;
import com.remdesk.api.repository.jpa.FileJpa;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class FileRepositoryImpl extends AbstractRepository< File > implements FileRepository {

    protected final FileJpa fileJpa;


    public FileRepositoryImpl(
            EntityManager entityManager,
            FileJpa fileJpa ) {
        super( entityManager, fileJpa );
        this.fileJpa = fileJpa;
    }


    @Override
    public List< File > findAllByFolder( Folder folder ) {
        return fileJpa.findAllByFolder( folder );
    }


    @Override
    public Optional< File > findByPath( String path ) {
        return fileJpa.findByPath( path );
    }


    @Override
    public List< File > findAllByRoot() {
        return fileJpa.findAllByFolderIsNull();
    }


    @Override
    protected Class< File > getClassType() {
        return File.class;
    }
}
