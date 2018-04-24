package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.SolutionDetailImg;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SolutionDetailImg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolutionDetailImgRepository extends JpaRepository<SolutionDetailImg, Long> {

}
