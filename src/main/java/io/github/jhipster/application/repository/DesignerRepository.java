package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Designer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Designer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignerRepository extends JpaRepository<Designer, Long> {

}
