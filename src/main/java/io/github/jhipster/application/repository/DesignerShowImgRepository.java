package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DesignerShowImg;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DesignerShowImg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DesignerShowImgRepository extends JpaRepository<DesignerShowImg, Long> {

}
