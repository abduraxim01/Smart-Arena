package com.practise.Smart_Arena.service.auth;

import com.practise.Smart_Arena.DTO.OtpEntry;
import com.practise.Smart_Arena.DTO.requestDTO.OtpDTO;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.repository.OwnerRepository;
import com.practise.Smart_Arena.service.jwtService.JwtUtil;
import com.practise.Smart_Arena.service.smsService.SendSMSService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private OwnerRepository ownRep;

    @Autowired
    private SendSMSService smsSer;

    @Autowired
    private JwtUtil jwtUtil;

    private ConcurrentHashMap<String, OtpEntry> otpCache = new ConcurrentHashMap<>();

    final private Logger log = LogManager.getLogger(LoginService.class);

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) {
        return ownRep.findByPhoneNumber(phoneNumber);
    }

    public void numberValidate(String phoneNumber) {
        isValidPhoneNumber(phoneNumber);
        if (ownRep.existsByPhoneNumber(phoneNumber)) {
            String otp = smsSer.sendSMSForAuth(phoneNumber);
            storeOtp(phoneNumber, otp);
            log.info("Otp sent to {} , code: {}", phoneNumber, otp);
            return;
        }
        log.error("Phone number not yet registered: {}", phoneNumber);
        throw new AllExceptions.EntityNotFoundException("Phone number not yet registered: " + phoneNumber);
    }

    public void storeOtp(String phoneNumber, String otp) {
        long expiryTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5000 * 60);  // OTP expires in 5 minutes
        otpCache.put(phoneNumber, new OtpEntry(otp, expiryTime));
    }

    public String otpCheck(OtpDTO otpDTO) {
        isValidPhoneNumber(otpDTO.getPhoneNumber());
        if (verifyOtp(otpDTO.getPhoneNumber(), otpDTO.getCode())) {
            log.info("User successfully done in login phoneNumber: {}", otpDTO.getPhoneNumber());
            return jwtUtil.encode(otpDTO.getPhoneNumber());
        }
        throw new AllExceptions.IllegalArgumentException("Otp code is invalid: " + otpDTO.getCode());
    }

    public boolean verifyOtp(String phoneNumber, String enteredOtp) {
        OtpEntry entry = otpCache.get(phoneNumber);
        if (entry == null || entry.getExpiryTime() < System.currentTimeMillis()) {
            otpCache.remove(phoneNumber);  // Remove expired OTP
            return false;
        }
        return entry.getOtp().equals(enteredOtp);
    }

    public void isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\+998(20|33|90|91|93|94|99)\\d{7}$";
        if (!phoneNumber.trim().matches(regex)) {
            log.error("Phone number is invalid: {}", phoneNumber);
            throw new AllExceptions.IllegalArgumentException("Phone number is invalid: " + phoneNumber);
        }
    }
}
