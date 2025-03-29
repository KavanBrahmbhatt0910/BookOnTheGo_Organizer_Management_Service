package com.group3.BookOnTheGo.OTP.Service;

import org.springframework.http.ResponseEntity;

public interface IOTPService {
    Integer generateOTP();
    boolean registerOTP(Integer otp_code, Long userId);
    ResponseEntity<Object> verifyOTP(Integer otp_code, String Email);
}
