package com.remdesk.api.repository;

import com.remdesk.api.entity.Card;
import com.remdesk.api.repository.jpa.CardJpa;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Service
public class CardRepositoryImpl extends AbstractRepository< Card > implements CardRepository {

    protected final CardJpa cardJpa;


    public CardRepositoryImpl(
            EntityManager entityManager,
            CardJpa cardJpa ) {
        super( entityManager, cardJpa );
        this.cardJpa = cardJpa;
    }


    @Override
    protected Class< Card > getClassType() {
        return Card.class;
    }
}
