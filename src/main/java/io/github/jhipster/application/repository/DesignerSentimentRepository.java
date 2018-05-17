package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DesignerSentiment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DesignerSentiment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignerSentimentRepository extends JpaRepository<DesignerSentiment, Long> {

}
