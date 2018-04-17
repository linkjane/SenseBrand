package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ReaderOld;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ReaderOld entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReaderOldRepository extends JpaRepository<ReaderOld, Long> {

}
