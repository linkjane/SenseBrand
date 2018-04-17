package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Reader;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Reader entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

}
