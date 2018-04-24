package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.OurSight;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OurSight entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OurSightRepository extends JpaRepository<OurSight, Long> {

}
