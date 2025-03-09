package com.example.project.dto.response;

import lombok.Getter;
import lombok.Setter;
//import org.springframework.stereotype.Component;

@Getter
@Setter
//@Component
public class ResponseDto {

    private String message;
    private String code;
    private Object data;

    public ResponseDto(String message,String code,Object data){
        this.message= message;
        this.code = code;
        this.data = data;
    }

}


@Getter
@Setter
//@Component
class Payload{
    private final String email;
    private final int Id;

   public Payload(String email,int id){
       this.email = email;
       this.Id = id;
   }
}
