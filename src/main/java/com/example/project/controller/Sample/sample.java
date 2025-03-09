package com.example.project.controller.Sample;

import com.example.project.dto.response.ErrorResponse;
import com.example.project.dto.response.ResponseDto;
import com.example.project.utils.EmailService;
import com.example.project.utils.OTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class sample {

    @Autowired
    private OTP otpObj;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(sample.class);

    @GetMapping("/")
    public String sampleController(){
        return "Hello from Spring Boot";
    }

    @GetMapping("/home")
    public String sayHello(){
        logger.info("sayHello controller method started");
        return "index.html";
    }


    @GetMapping("/otp/send")
    public ResponseEntity<?> sendOtpController(){
        logger.info("send otp controller starts");
        String otpId;
        try{
             otpId = otpObj.sendOtp("+919911142160");
        }catch(Exception e ){
            logger.error("Exception occurred at sendOtpController ",e);
            throw new RuntimeException(e);
        }

       return ResponseEntity.ok(new ResponseDto("Otp sent successfully ","SUC-200",otpId));
    }

    @GetMapping("otp/verify")
    public ResponseEntity<?> verifyOtpController(@RequestParam String otp, @RequestParam String otpId){
        logger.info("verify otp controller method starts ");
        String verify;
        try{
            verify = otpObj.verifyOtp(otp,otpId);
            if(verify == null ){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Wrong Otp","BAD-400"));
            }
        }catch(Exception e){
            logger.error("Exception occurred at verifyOtpController ",e);
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(new ErrorResponse("Otp verified successfully","SUC-200"));
    }

    @GetMapping("/otp")
    public ResponseEntity<?> otp(){
        return ResponseEntity.ok(new ResponseDto("Otp generated successfully","SUC-200",emailService.generateRandomNumber(4)));
    }
}
