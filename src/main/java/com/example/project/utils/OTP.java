package com.example.project.utils;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OTP {

    private static final Logger logger = LoggerFactory.getLogger(OTP.class);


    private static final String HEADER_CLIENT_ID = "clientId";
    private static final String HEADER_CLIENT_SECRET = "clientSecret";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String SEND_OTP_URL = "https://auth.otpless.app/auth/v1/initiate/otp";
    private static final String VERIFY_OTP_URL = "https://auth.otpless.app/auth/v1/verify/otp";

    @Value("${otpless.CLIENT_ID}")
    private String clientId;

    @Value("${otpless.CLIENT_SECRET}")
    private String client_secret;

    @Value("${otpless.OTP_EXPIRY_TIME}")
    private String expiry_time;

    @Value("${otpless.OTP_LENGTH}")
    private String otp_length;

    public String sendOtp(String phoneNumber) {
    logger.info("sendOtp util method start ");
    try{

        JSONObject requestBody = new JSONObject();
        requestBody.put("phoneNumber", phoneNumber);
        requestBody.put("expiry", Integer.parseInt(expiry_time)); // Ensure expiryTime is an integer
        requestBody.put("otpLength", Integer.parseInt(otp_length)); // Ensure otpLength is an integer
        requestBody.put("channels", new String[]{"SMS"});

        JSONObject metadata = new JSONObject();
        metadata.put("key1", "Data1");
        metadata.put("key2", "Data2");
        requestBody.put("metadata", metadata);

//        HttpResponse<JsonNode> response = Unirest.post("https://auth.otpless.app/auth/v1/initiate/otp")
//                .header("clientId", clientId)
//                .header("clientSecret", client_secret)
//                .header("Content-Type", "application/json")
//                .body("{\n" +
//                        "  \"phoneNumber\": \"" + phoneNumber + "\",\n" +
//                        "  \"expiry\": " + expiry_time + ",\n" +
//                        "  \"otpLength\": " + otp_length + ",\n" +
//                        "  \"channels\": [\"SMS\"],\n" +
//                        "  \"metadata\": {\n" +
//                        "    \"key1\": \"Data1\",\n" +
//                        "    \"key2\": \"Data2\"\n" +
//                        "  }\n" +
//                        "}")
//                .asJson();

        HttpResponse<JsonNode> response = Unirest.post(SEND_OTP_URL)
                .header(HEADER_CLIENT_ID, clientId)
                .header(HEADER_CLIENT_SECRET, client_secret)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(requestBody.toString())
                .asJson();

        JSONObject responseBody = response.getBody().getObject();
        if (responseBody.has("errorCode")) {
//            logger.error("responseBody {} ",responseBody);
            String errorCode = responseBody.getString("errorCode");
            String errorMessage = responseBody.getString("message");
            String description = responseBody.getString("description");
//            logger.error("API error: {} - {} - {}", errorCode, errorMessage,description);
            throw new RuntimeException("API error: " + errorMessage+errorMessage+description);
        }
        return response.getBody().toString();
    }
    catch(Exception e){
        logger.error("Failed to send otp",e);
                throw new RuntimeException(e);
    }
    }

    public String verifyOtp(String otp,String otpId){
        logger.info("verifyOtp util method starts ");
    try{
        HttpResponse<String> response = Unirest.post(VERIFY_OTP_URL)
                .header("clientId", clientId)
                .header("clientSecret", client_secret)
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"requestId\": \"" + otpId + "\",\n" +
                        "  \"otp\": \"" + otp + "\"\n" +
                        "}")
                .asString();

        return response.getBody();
    }catch(Exception e){
        logger.error("Failed to Verify OTP",e);
      throw new RuntimeException(e);
    }
    }
}
