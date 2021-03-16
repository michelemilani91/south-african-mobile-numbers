package it.interlogica.southafricanmobilenumbers.service.dto;

import it.interlogica.southafricanmobilenumbers.model.Elaboration;
import it.interlogica.southafricanmobilenumbers.model.ElaborationStatusType;

public class ElaborationDTO {
    private Long id;
    private String name;
    private String status;

    public ElaborationDTO() {}

    public ElaborationDTO(Long id, String name, ElaborationStatusType status) {
        this.id = id;
        this.name = name;
        this.status = status.name();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
