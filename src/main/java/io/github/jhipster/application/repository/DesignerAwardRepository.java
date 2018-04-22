package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DesignerAward;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DesignerAward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignerAwardRepository extends JpaRepository<DesignerAward, Long> {

}
