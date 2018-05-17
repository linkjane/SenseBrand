package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DesignerIdeaDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DesignerIdeaDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignerIdeaDetailsRepository extends JpaRepository<DesignerIdeaDetails, Long> {

}
