package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.BrandRank;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BrandRank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrandRankRepository extends JpaRepository<BrandRank, Long> {

}
