package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.SolutionCorrelation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SolutionCorrelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolutionCorrelationRepository extends JpaRepository<SolutionCorrelation, Long> {

}
