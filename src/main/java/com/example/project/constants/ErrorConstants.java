package com.example.project.constants;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ErrorConstants {

    private static final ErrorDetails INTERNAL_SERVER_ERROR = new ErrorDetails("Internal Server Error","INT-500");
    private static final ErrorDetails NOT_FOUND = new ErrorDetails("Details Not found ","BAD-404");
    private static final ErrorDetails BAD_REQUEST = new ErrorDetails("Bad Request","BAD-400");
    private static final ErrorDetails UNAUTHORIZED_ACCESS = new ErrorDetails("Unauthorized access","BAD-401");

    // prevent instantiation
    private ErrorConstants(){}

    // Inner class
//        @Getter
//        @Setter
        public record ErrorDetails(String message, String code) {
    }
}
