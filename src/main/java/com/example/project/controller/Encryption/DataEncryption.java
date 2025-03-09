package com.example.project.controller.Encryption;

import com.example.project.dto.response.ResponseDto;
import com.example.project.utils.EncryptData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
public class DataEncryption {

    private static final Logger logger = LoggerFactory.getLogger(DataEncryption.class);

    @Autowired
    private EncryptData encryptData;

    // Data in params is optional as - > encrypt?data=hello
    // query parameters are part of url - > encrypt/data/hello
    // @PostMapping("/encrypt/{data}") -> data to be accessed using PathVariable

    @PostMapping("/encrypt")
    public ResponseEntity<?> encryptData(@RequestParam String data){
    logger.info("encryptData controller method starts");

        try{
     String encryptedData =  encryptData.encrypt(data);
            ResponseDto response = new ResponseDto("Data encrypted successfully","SUC-200",encryptedData);
            return ResponseEntity.ok(response);

    }catch(Exception e){
        logger.error("Exception occurred at encryptData");
        throw new RuntimeException(e);
    }
    }

    @GetMapping("/decrypt")
    public ResponseEntity<?> decryptData(@RequestParam String encryptedData){

        logger.info("decryptData controller method started ");

        try{
            String decryptedData = encryptData.decrypt(encryptedData);
            return ResponseEntity.ok(new ResponseDto("Decrypted data","SUC-200",decryptedData));
        }catch(Exception e){
            logger.error("Exception occurred at decryptData method ",e);
            throw new RuntimeException(e);
        }
    }
}
