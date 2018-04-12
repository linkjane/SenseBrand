package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Car;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Car entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("select distinct car from Car car left join fetch car.drivers")
    List<Car> findAllWithEagerRelationships();

    @Query("select car from Car car left join fetch car.drivers where car.id =:id")
    Car findOneWithEagerRelationships(@Param("id") Long id);

}
