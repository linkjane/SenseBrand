package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.IndustryType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IndustryType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndustryTypeRepository extends JpaRepository<IndustryType, Long> {

}
