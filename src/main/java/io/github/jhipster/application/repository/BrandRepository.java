package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Brand;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Brand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("select distinct brand from Brand brand left join fetch brand.brandRanks left join fetch brand.brandRegions")
    List<Brand> findAllWithEagerRelationships();

    @Query("select brand from Brand brand left join fetch brand.brandRanks left join fetch brand.brandRegions where brand.id =:id")
    Brand findOneWithEagerRelationships(@Param("id") Long id);

}
