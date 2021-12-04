package com.remdesk.api.repository.jpa;

import com.remdesk.api.entity.Card;
import com.remdesk.api.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Repository
public interface CredentialJpa extends JpaRepository< Credential, Long > {

    List< Credential > findAllByCard( Card card );
}
