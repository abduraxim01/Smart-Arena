package com.practise.Smart_Arena.controller.auth;

import com.practise.Smart_Arena.DTO.requestDTO.LoginDTOForRequest;
import com.practise.Smart_Arena.exception.AllExceptions;
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

    @Autowired
    private LoginService logSer;

    @PostMapping(value = "/numberValidate",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> numberValidate(@RequestBody LoginDTOForRequest loginDTOForRequest) {
        try {
            logSer.numberValidate(loginDTOForRequest.getPhoneNumber());
            return ResponseEntity.ok(true);
        } catch (AllExceptions.IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (AllExceptions.UsernameAlreadyTakenException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (AllExceptions.EntityNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    @PostMapping(value = "/otpCheck")
    public ResponseEntity<?> otpCheck(@RequestBody LoginDTOForRequest otp) {
        try {
            return ResponseEntity.ok(logSer.otpCheck(otp));
        } catch (AllExceptions.IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }
}
