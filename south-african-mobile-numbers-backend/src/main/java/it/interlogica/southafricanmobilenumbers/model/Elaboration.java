package it.interlogica.southafricanmobilenumbers.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "elaborations")
public class Elaboration {

    private Long id;
    private String name;
    private String csvData;
    private ElaborationStatusType status;
    private List<MobileNumber> mobileNumbers = new ArrayList<>();

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "csv_data", nullable = false, length = 10_000_000)
    public String getCsvData() {
        return csvData;
    }

    public void setCsvData(String csvFile) {
        this.csvData = csvFile;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public ElaborationStatusType getStatus() {
        return status;
    }

    public void setStatus(ElaborationStatusType status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "elaboration", cascade = CascadeType.ALL)
    public List<MobileNumber> getMobileNumbers() {
        return mobileNumbers;
    }

    public void setMobileNumbers(List<MobileNumber> mobileNumbers) {
        this.mobileNumbers = mobileNumbers;
    }
}
