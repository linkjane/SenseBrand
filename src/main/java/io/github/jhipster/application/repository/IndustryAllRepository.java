package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.IndustryAll;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IndustryAll entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndustryAllRepository extends JpaRepository<IndustryAll, Long> {

}
