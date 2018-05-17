package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.SolutionDetailAnalysis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SolutionDetailAnalysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolutionDetailAnalysisRepository extends JpaRepository<SolutionDetailAnalysis, Long> {

}
