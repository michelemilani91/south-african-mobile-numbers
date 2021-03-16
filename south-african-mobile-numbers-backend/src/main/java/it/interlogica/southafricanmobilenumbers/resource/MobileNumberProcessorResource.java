package it.interlogica.southafricanmobilenumbers.resource;

import it.interlogica.southafricanmobilenumbers.service.MobileNumberValidatorService;
import it.interlogica.southafricanmobilenumbers.service.MobileNumbersProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MobileNumberProcessorResource {

    private final MobileNumbersProcessorService mobileNumbersProcessorService;
    private final MobileNumberValidatorService mobileNumberValidatorService;

    @Autowired
    public MobileNumberProcessorResource(
            MobileNumbersProcessorService mobileNumbersProcessorService,
            MobileNumberValidatorService mobileNumberValidatorService) {
        this.mobileNumbersProcessorService = mobileNumbersProcessorService;
        this.mobileNumberValidatorService = mobileNumberValidatorService;
    }

    @PostMapping("/single")
    public ResponseEntity<?> isValid(@RequestBody String mobileNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(mobileNumberValidatorService.checkMobileNumber(mobileNumber));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAll(Pageable page) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(mobileNumbersProcessorService.getAllElaboration(page));
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long elaborationId, Pageable page) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(mobileNumbersProcessorService.getMobileNumbers(elaborationId, page));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> create(@RequestParam("file") MultipartFile file) {
        try {
            final String name = "Elaboration of " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            mobileNumbersProcessorService.create(name, file);
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File not valid!");
        }
    }

    @PostMapping("/process/{id}")
    public ResponseEntity<?> process(@PathVariable Long id) {
        try {
            mobileNumbersProcessorService.process(id);
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File not valid!");
        }
    }

}
