package it.interlogica.southafricanmobilenumbers.repository;

import it.interlogica.southafricanmobilenumbers.model.Elaboration;
import it.interlogica.southafricanmobilenumbers.service.dto.ElaborationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ElaborationRepository extends JpaRepository<Elaboration,Long> {
    @Query("select new it.interlogica.southafricanmobilenumbers.service.dto.ElaborationDTO(e.id, e.name, e.status) from Elaboration e")
    Page<ElaborationDTO> findAllDto(Pageable pageable);
}
