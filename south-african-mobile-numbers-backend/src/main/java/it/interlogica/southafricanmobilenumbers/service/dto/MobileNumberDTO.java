package it.interlogica.southafricanmobilenumbers.service.dto;

public class MobileNumberDTO {

    private Long id;
    private String originalId;
    private String originalMobileNumber;
    private String correctedMobileNumber;
    private boolean valid;
    private String errorMessage;

    public MobileNumberDTO() {}

    public MobileNumberDTO(Long id, String originalId, String originalMobileNumber, String correctedMobileNumber, boolean valid, String errorMessage) {
        this.id = id;
        this.originalId = originalId;
        this.originalMobileNumber = originalMobileNumber;
        this.correctedMobileNumber = correctedMobileNumber;
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public String getOriginalMobileNumber() {
        return originalMobileNumber;
    }

    public void setOriginalMobileNumber(String originalMobileNumber) {
        this.originalMobileNumber = originalMobileNumber;
    }

    public String getCorrectedMobileNumber() {
        return correctedMobileNumber;
    }

    public void setCorrectedMobileNumber(String correctedMobileNumber) {
        this.correctedMobileNumber = correctedMobileNumber;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
