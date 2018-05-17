package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.GrandAward;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GrandAward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrandAwardRepository extends JpaRepository<GrandAward, Long> {

}
