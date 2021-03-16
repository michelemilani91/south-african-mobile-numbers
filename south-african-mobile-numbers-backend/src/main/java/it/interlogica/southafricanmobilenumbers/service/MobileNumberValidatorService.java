package it.interlogica.southafricanmobilenumbers.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import it.interlogica.southafricanmobilenumbers.service.dto.MobileNumberValidationResult;
import org.springframework.stereotype.Service;

@Service
public class MobileNumberValidatorService {

    public static final String REGION_SOUTH_AFRICA = "ZA";

    private final PhoneNumberUtil phoneUtil;

    public MobileNumberValidatorService() {
        this.phoneUtil = PhoneNumberUtil.getInstance();
    }

    public MobileNumberValidationResult checkMobileNumber(String mobileNumber) {
        final MobileNumberValidationResult result = new MobileNumberValidationResult();
        final Phonenumber.PhoneNumber southAfricaNumberProto;
        try {
            southAfricaNumberProto = phoneUtil.parse(mobileNumber, REGION_SOUTH_AFRICA);
        } catch (NumberParseException e) {
            result.setValid(false);
            result.setErrorMessage(e.getMessage());
            return result;
        }
        if (!PhoneNumberUtil.PhoneNumberType.MOBILE.equals(phoneUtil.getNumberType(southAfricaNumberProto))) {
            result.setValid(false);
            result.setErrorMessage("Not mobile number");
            return result;
        }
        // TODO add here other checks
        result.setValid(true);
        result.setFormattedNumber(phoneUtil.format(southAfricaNumberProto, PhoneNumberUtil.PhoneNumberFormat.E164));
        return result;
    }


}
