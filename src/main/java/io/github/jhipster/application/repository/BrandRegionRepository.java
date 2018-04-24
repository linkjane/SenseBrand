package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.BrandRegion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BrandRegion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrandRegionRepository extends JpaRepository<BrandRegion, Long> {

}
