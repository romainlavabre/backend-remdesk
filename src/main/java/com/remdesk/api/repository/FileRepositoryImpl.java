package com.remdesk.api.repository;

import com.remdesk.api.entity.File;
import com.remdesk.api.repository.jpa.FileJpa;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

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
    protected Class< File > getClassType() {
        return File.class;
    }
}
