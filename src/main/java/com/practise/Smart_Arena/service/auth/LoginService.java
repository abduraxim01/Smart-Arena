package com.practise.Smart_Arena.service.auth;

import com.practise.Smart_Arena.DTO.OtpEntry;
import com.practise.Smart_Arena.DTO.requestDTO.LoginDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.LoginDTOForResponse;
import com.practise.Smart_Arena.exception.AllExceptions;
import com.practise.Smart_Arena.repository.OwnerRepository;
import com.practise.Smart_Arena.repository.PlayerRepository;
import com.practise.Smart_Arena.service.PhoneNumberFilter;
import com.practise.Smart_Arena.service.jwtService.JwtUtil;
import com.practise.Smart_Arena.service.smsService.SendSMSService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private OwnerRepository ownerRep;

    @Autowired
    private PlayerRepository playerRep;

    @Autowired
    private SendSMSService smsSer;

    @Autowired
    private JwtUtil jwtUtil;

    private ConcurrentHashMap<String, OtpEntry> otpCache = new ConcurrentHashMap<>();

    final private Logger log = LogManager.getLogger(LoginService.class);

    final private PhoneNumberFilter phoneNumberFilter = new PhoneNumberFilter();

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) {
        if (ownerRep.existsByPhoneNumber(phoneNumber)) return ownerRep.findByPhoneNumber(phoneNumber);
        if (playerRep.existsByPhoneNumber(phoneNumber)) return playerRep.findByPhoneNumber(phoneNumber);
        return null;
    }

    public void numberValidate(String phoneNumber) {
        phoneNumberFilter.isValidPhoneNumber(phoneNumber);
        if (ownerRep.existsByPhoneNumber(phoneNumber) || playerRep.existsByPhoneNumber(phoneNumber)) {
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

    public LoginDTOForResponse otpCheck(LoginDTOForRequest loginDTOForRequest) {
        phoneNumberFilter.isValidPhoneNumber(loginDTOForRequest.getPhoneNumber());
        if (verifyOtp(loginDTOForRequest.getPhoneNumber(), loginDTOForRequest.getCode())) {
            log.info("User successfully done in login phoneNumber: {}", loginDTOForRequest.getPhoneNumber());
            removeOtp(loginDTOForRequest.getPhoneNumber());
            return generateResponse(loadUserByUsername(loginDTOForRequest.getPhoneNumber()), loginDTOForRequest.getPhoneNumber());
//            String role = loadUserByUsername(loginDTOForRequest.getPhoneNumber()).getAuthorities().toString();
//            if (role.substring(6, role.length() - 1).equals("OWNER"))
//                return LoginDTOForResponse.builder()
//                        .id(ownerRep.findByPhoneNumber(loginDTOForRequest.getPhoneNumber()).getId())
//                        .token(jwtUtil.encode(loginDTOForRequest.getPhoneNumber()))
//                        .role("OWNER")
//                        .build();
//            else return LoginDTOForResponse.builder()
//                    .id(playerRep.findByPhoneNumber(loginDTOForRequest.getPhoneNumber()).getId())
//                    .token(jwtUtil.encode(loginDTOForRequest.getPhoneNumber()))
//                    .role("PLAYER")
//                    .build();

        }
        throw new AllExceptions.IllegalArgumentException("Otp code is invalid: " + loginDTOForRequest.getCode());
    }

    public boolean verifyOtp(String phoneNumber, String enteredOtp) {
        OtpEntry entry = otpCache.get(phoneNumber);
        if (entry == null || entry.getExpiryTime() < System.currentTimeMillis()) {
            otpCache.remove(phoneNumber);  // Remove expired OTP
            return false;
        }
        return entry.getOtp().equals(enteredOtp);
    }

    public void removeOtp(String phoneNumber) {
        otpCache.remove(phoneNumber);
    }

    public LoginDTOForResponse generateResponse(UserDetails user, String phoneNumber) {
        Map<String, List<String>> permissionsMap = new HashMap<>();
        String role = "";
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        int index;
        String authorityName;

        for (GrantedAuthority authority : authorities) {
            authorityName = authority.getAuthority();
            index = authorityName.indexOf("_");
            if (!authorityName.startsWith("ROLE_")) {
                permissionsMap.computeIfAbsent(authorityName.substring(0, index), k -> new ArrayList<>()).add(authorityName.substring(index + 1));
            } else role = authorityName.substring(index + 1);
        }

        return LoginDTOForResponse.builder()
                .userId(null) // proyektga to'g'irlaw kere
                .role(role)
                .permissions(permissionsMap)
                .token(jwtUtil.encode(phoneNumber))
                .build();
    }
}
