package com.example.project.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private String code;

    public ErrorResponse(String message,String code){
        this.message= message;
        this.code = code;
    }

}
