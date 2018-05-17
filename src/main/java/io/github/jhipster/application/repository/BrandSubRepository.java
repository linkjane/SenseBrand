package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.BrandSub;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BrandSub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrandSubRepository extends JpaRepository<BrandSub, Long> {

}
