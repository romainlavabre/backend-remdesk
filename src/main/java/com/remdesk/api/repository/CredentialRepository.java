package com.remdesk.api.repository;

import com.remdesk.api.entity.Card;
import com.remdesk.api.entity.Credential;

import java.util.List;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
public interface CredentialRepository extends DefaultRepository< Credential > {

    List< Credential > findAllByCard( Card card );
}
