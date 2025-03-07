package com.practise.Smart_Arena.controller.auth;

import com.practise.Smart_Arena.DTO.requestDTO.LoginDTOForRequest;
import com.practise.Smart_Arena.DTO.responseDTO.LoginDTOForResponse;
import com.practise.Smart_Arena.service.auth.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/login")
public class Login {

    final private LoginService logSer;

    @Autowired
    public Login(LoginService logSer) {
        this.logSer = logSer;
    }

    @PostMapping(value = "/numberValidate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> numberValidate(@RequestBody LoginDTOForRequest loginDTOForRequest) {
        logSer.numberValidate(loginDTOForRequest.getPhoneNumber());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/otpCheck")
    public ResponseEntity<LoginDTOForResponse> otpCheck(@RequestBody LoginDTOForRequest otp) {
        return ResponseEntity.ok(logSer.otpCheck(otp));
    }
}
