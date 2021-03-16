package it.interlogica.southafricanmobilenumbers.model;

import javax.persistence.*;

@Entity
@Table(name = "mobile_numbers")
public class MobileNumber {

    private Long id;
    private String originalId;
    private String originalMobileNumber;
    private String correctedMobileNumber;
    private boolean valid;
    private String errorMessage;

    private Elaboration elaboration;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "original_id", nullable = true)
    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    @Column(name = "original_mobile_number", nullable = false)
    public String getOriginalMobileNumber() {
        return originalMobileNumber;
    }

    public void setOriginalMobileNumber(String originalMobileNumber) {
        this.originalMobileNumber = originalMobileNumber;
    }

    @Column(name = "corrected_mobile_number")
    public String getCorrectedMobileNumber() {
        return correctedMobileNumber;
    }

    public void setCorrectedMobileNumber(String correctedMobileNumber) {
        this.correctedMobileNumber = correctedMobileNumber;
    }

    @Column(name = "valid", nullable = false)
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Column(name = "errorMessage")
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @ManyToOne
    @JoinColumn(name ="elaboration_id", nullable = false)
    public Elaboration getElaboration() {
        return elaboration;
    }

    public void setElaboration(Elaboration elaboration) {
        this.elaboration = elaboration;
    }
}
