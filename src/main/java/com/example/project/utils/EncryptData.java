package com.example.project.utils;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

@Component
public class EncryptData {

    @Value("${encryption.algorithm}")
    private String ALGORITHM;

    private static final Logger logger = LoggerFactory.getLogger(EncryptData.class);

    @Value("${encryption.SECRET_KEY}")
    private String SECRET_KEY;


    // ----> Generate Secret key   <----  //

    public String generateSecretKey() throws Exception{
       logger.info("generateSecretKey util method start");
        try{

            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(256); // AES-256 for strong encryption
            SecretKey key = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded());

        }catch(Exception e){
            logger.error("Exception occurred at generateSecretKey util method ",e);
            throw new RuntimeException(e);
        }
    }



    // Encrypt password        ----->

    public String encrypt(String data) throws Exception{
        logger.info("encrypt function started in EncryptData class");
        try{
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }catch(Exception e){
         logger.error("Exception occurred at encrypt util method ",e);
         throw new RuntimeException(e);
        }
    }


    // Decrypt Password ---->

    public  String decrypt(String encryptedData) throws Exception{
        logger.info("decrypt util method started ");

        try{
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);

        }catch(Exception e){
            logger.error("Exception occurred at decrypt util method ",e);
            throw new RuntimeException(e);
        }
    }
}
