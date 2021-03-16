package it.interlogica.southafricanmobilenumbers.service;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import it.interlogica.southafricanmobilenumbers.model.Elaboration;
import it.interlogica.southafricanmobilenumbers.model.ElaborationStatusType;
import it.interlogica.southafricanmobilenumbers.model.MobileNumber;
import it.interlogica.southafricanmobilenumbers.repository.ElaborationRepository;
import it.interlogica.southafricanmobilenumbers.repository.MobileNumberRepository;
import it.interlogica.southafricanmobilenumbers.service.dto.ElaborationDTO;
import it.interlogica.southafricanmobilenumbers.service.dto.MobileNumberDTO;
import it.interlogica.southafricanmobilenumbers.service.dto.MobileNumberValidationResult;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class MobileNumbersProcessorService {

    private final ElaborationRepository elaborationRepository;
    private final MobileNumberRepository mobileNumberRepository;
    private final MobileNumberValidatorService mobileNumberValidatorService;

    @Autowired
    public MobileNumbersProcessorService(
            ElaborationRepository elaborationRepository,
            MobileNumberRepository mobileNumberRepository,
            MobileNumberValidatorService mobileNumberValidatorService) {
        this.elaborationRepository = elaborationRepository;
        this.mobileNumberRepository = mobileNumberRepository;
        this.mobileNumberValidatorService = mobileNumberValidatorService;
    }

    public Page<ElaborationDTO> getAllElaboration(Pageable pageable) {
        return elaborationRepository.findAllDto(pageable);
    }

    public Long create(String name, MultipartFile file) throws IOException {
        final String csvData = new String(file.getBytes(), StandardCharsets.UTF_8);
        final Elaboration elaboration = new Elaboration();
        elaboration.setName(name);
        elaboration.setCsvData(csvData);
        elaboration.setStatus(ElaborationStatusType.UNPROCESSED);
        elaborationRepository.save(elaboration);
        return elaboration.getId();
    }

    public void process(Long elaborationId) throws IOException {
        final Optional<Elaboration> elaborationOp = elaborationRepository.findById(elaborationId);
        if (elaborationOp.isPresent()) {
            final Elaboration elaboration = elaborationOp.get();
            if (!ElaborationStatusType.UNPROCESSED.equals(elaboration.getStatus()))
                throw new IOException("Elaboration status not valid!");
            elaboration.setStatus(ElaborationStatusType.PROCESSING);
            elaborationRepository.save(elaboration);
            final Reader in = new StringReader(elaboration.getCsvData());
            final Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);
            final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            try {
                for (CSVRecord record : records) {
                    final String originalId = record.get(0);
                    final String originalMobileNumber = record.get(1);
                    final MobileNumber mobileNumber = new MobileNumber();
                    mobileNumber.setElaboration(elaboration);
                    mobileNumber.setOriginalId(originalId);
                    mobileNumber.setOriginalMobileNumber(originalMobileNumber);
                    final MobileNumberValidationResult mobileNumberValidationResult = mobileNumberValidatorService.checkMobileNumber(originalMobileNumber);
                    mobileNumber.setValid(mobileNumberValidationResult.isValid());
                    mobileNumber.setCorrectedMobileNumber(mobileNumberValidationResult.getFormattedNumber());
                    mobileNumber.setErrorMessage(mobileNumberValidationResult.getErrorMessage());
                    mobileNumberRepository.save(mobileNumber);
                }
            } catch (Exception ignored) {
                // TODO handle errors instead ignore them
            }
            elaboration.setStatus(ElaborationStatusType.PROCESSED);
            elaborationRepository.save(elaboration);
            in.close();
        }
    }

    public Page<MobileNumberDTO> getMobileNumbers(Long elaborationId, Pageable page) {
        return mobileNumberRepository.findByElaborationId(elaborationId, page);
    }
}
