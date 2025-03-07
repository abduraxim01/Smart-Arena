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

    final private OwnerRepository ownerRep;

    final private PlayerRepository playerRep;

    final private SendSMSService smsSer;

    final private JwtUtil jwtUtil;


    final private Logger log = LogManager.getLogger(LoginService.class);

    final private PhoneNumberFilter phoneNumberFilter;

    private ConcurrentHashMap<String, OtpEntry> otpCache = new ConcurrentHashMap<>();

    @Autowired
    public LoginService(OwnerRepository ownerRep, PlayerRepository playerRep, SendSMSService smsSer, JwtUtil jwtUtil, PhoneNumberFilter phoneNumberFilter) {
        this.ownerRep = ownerRep;
        this.playerRep = playerRep;
        this.smsSer = smsSer;
        this.jwtUtil = jwtUtil;
        this.phoneNumberFilter = phoneNumberFilter;
    }

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
        long expiryTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3000 * 60);  // OTP expires in 3 minutes
        otpCache.put(phoneNumber, new OtpEntry(otp, expiryTime));
    }

    public LoginDTOForResponse otpCheck(LoginDTOForRequest loginDTOForRequest) {
        phoneNumberFilter.isValidPhoneNumber(loginDTOForRequest.getPhoneNumber());
        if (verifyOtp(loginDTOForRequest.getPhoneNumber(), loginDTOForRequest.getCode())) {
            log.info("User successfully login as {}  phoneNumber: {}", loginDTOForRequest.isOwner() ? "OWNER" : "PLAYER", loginDTOForRequest.getPhoneNumber());
            removeOtp(loginDTOForRequest.getPhoneNumber());
            return generateResponse(loadUserByUsername(loginDTOForRequest.getPhoneNumber()), loginDTOForRequest);

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

    public LoginDTOForResponse generateResponse(UserDetails user, LoginDTOForRequest loginDTO) {
        Map<String, List<String>> permissionsMap = new HashMap<>();
        String role = loginDTO.isOwner() ? "OWNER" : "PLAYER";
        UUID id = loginDTO.isOwner() ? ownerRep.findByPhoneNumber(loginDTO.getPhoneNumber()).getId() : playerRep.findByPhoneNumber(loginDTO.getPhoneNumber()).getId();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        int index;
        String authorityName;

        for (GrantedAuthority authority : authorities) {
            authorityName = authority.getAuthority();
            index = authorityName.indexOf("_");
            if (!authorityName.startsWith("ROLE_"))
                permissionsMap.computeIfAbsent(authorityName.substring(0, index), k -> new ArrayList<>()).add(authorityName.substring(index + 1));
        }

        return LoginDTOForResponse.builder()
                .userId(id)
                .role(role)
                .permissions(permissionsMap)
                .token(jwtUtil.encode(loginDTO.getPhoneNumber()))
                .build();
    }
}
