package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DesignerIdeaMedia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DesignerIdeaMedia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignerIdeaMediaRepository extends JpaRepository<DesignerIdeaMedia, Long> {

}
