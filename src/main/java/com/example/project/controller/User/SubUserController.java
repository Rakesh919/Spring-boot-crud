package com.example.project.controller.User;


//import SubUser;
import com.example.project.Entity.User.SubUser;
import com.example.project.Service.User.SubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubUserController {

    @Autowired
    private SubUserService subUserService;

    @PostMapping("/sub/create")
    public SubUser addSubUserController(@RequestBody SubUser subUser){
        return subUserService.addSubUser(subUser);
    }

    @GetMapping("/sub/get")
    public SubUser getSubUserController(@RequestParam long id){
        return subUserService.getSubUserDetails(id);
    }
}
