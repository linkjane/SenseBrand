package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.MyFile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MyFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyFileRepository extends JpaRepository<MyFile, Long> {

}
