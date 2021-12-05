package com.remdesk.api.repository;

import com.remdesk.api.entity.Folder;
import com.remdesk.api.repository.jpa.FolderJpa;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class FolderRepositoryImpl extends AbstractRepository< Folder > implements FolderRepository {

    protected final FolderJpa folderJpa;


    public FolderRepositoryImpl(
            EntityManager entityManager,
            FolderJpa folderJpa ) {
        super( entityManager, folderJpa );
        this.folderJpa = folderJpa;
    }


    @Override
    public List< Folder > findAllByParent( Folder folder ) {
        return folderJpa.findAllByParent( folder );
    }


    @Override
    protected Class< Folder > getClassType() {
        return Folder.class;
    }
}
