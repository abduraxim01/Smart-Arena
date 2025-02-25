package com.practise.Smart_Arena.service;

import com.practise.Smart_Arena.exception.AllExceptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberFilter {

    final private Logger log = LogManager.getLogger(PhoneNumberFilter.class);

    public void isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\+998(20|33|90|91|93|94|99)\\d{7}$";
        if (!phoneNumber.trim().matches(regex)) {
            log.error("Phone number is invalid: {}", phoneNumber);
            throw new AllExceptions.IllegalArgumentException("Phone number is invalid: " + phoneNumber);
        }
    }
}
