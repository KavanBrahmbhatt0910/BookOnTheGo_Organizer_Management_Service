package com.group3.BookOnTheGo.OTP.Controller;

import com.group3.BookOnTheGo.OTP.DataTransferObject.VerifyOTPRequestDto;
import com.group3.BookOnTheGo.OTP.Service.IOTPService;
import com.group3.BookOnTheGo.Utils.BookOnTheGoResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/otp")
public class OTPController {

    private final IOTPService otpService;

    public OTPController(IOTPService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/verify")
    public ResponseEntity<Object> verifyOTP(@NotNull @RequestBody VerifyOTPRequestDto verifyOTPRequest) {
        String email = verifyOTPRequest.getEmail();
        Integer otp = verifyOTPRequest.getOtp();
        if (otp.toString().length() != 6) {
            return ResponseEntity.badRequest().body(BookOnTheGoResponse.builder()
                    .success(false)
                    .message("Invalid OTP")
                    .build());
        }
        return otpService.verifyOTP(otp, email);
    }

}
