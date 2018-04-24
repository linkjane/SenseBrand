package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.IndustryTypeName;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IndustryTypeName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndustryTypeNameRepository extends JpaRepository<IndustryTypeName, Long> {

}
