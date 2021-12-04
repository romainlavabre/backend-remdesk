package com.remdesk.api.repository;

import com.remdesk.api.entity.Card;
import com.remdesk.api.entity.Credential;
import com.remdesk.api.repository.jpa.CredentialJpa;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class CredentialRepositoryImpl extends AbstractRepository< Credential > implements CredentialRepository {

    protected final CredentialJpa credentialJpa;


    public CredentialRepositoryImpl(
            EntityManager entityManager,
            CredentialJpa credentialJpa ) {
        super( entityManager, credentialJpa );
        this.credentialJpa = credentialJpa;
    }


    @Override
    public List< Credential > findAllByCard( Card card ) {
        return credentialJpa.findAllByCard( card );
    }


    @Override
    protected Class< Credential > getClassType() {
        return Credential.class;
    }
}
