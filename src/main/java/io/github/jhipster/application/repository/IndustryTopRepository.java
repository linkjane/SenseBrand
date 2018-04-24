package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.IndustryTop;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IndustryTop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndustryTopRepository extends JpaRepository<IndustryTop, Long> {

}
