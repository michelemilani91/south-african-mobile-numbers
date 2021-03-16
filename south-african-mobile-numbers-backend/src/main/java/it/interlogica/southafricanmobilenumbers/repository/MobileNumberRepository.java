package it.interlogica.southafricanmobilenumbers.repository;

import it.interlogica.southafricanmobilenumbers.model.MobileNumber;
import it.interlogica.southafricanmobilenumbers.service.dto.MobileNumberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MobileNumberRepository extends JpaRepository<MobileNumber,Long> {
    @Query("select new it.interlogica.southafricanmobilenumbers.service.dto.MobileNumberDTO(" +
            "m.id, m.originalId, m.originalMobileNumber, m.correctedMobileNumber, m.valid, m.errorMessage" +
            ") from MobileNumber m where m.elaboration.id = :elaborationId")
    Page<MobileNumberDTO> findByElaborationId(@Param("elaborationId") Long elaborationId, Pageable page);
}
