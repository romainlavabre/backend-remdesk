package com.remdesk.api.repository.jpa;

import com.remdesk.api.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Romain Lavabre <romainlavabre98@gmail.com>
 */
@Repository
public interface FileJpa extends JpaRepository< File, Long > {
}
