package com.example.project.Service.User;

//import SubUser;
import com.example.project.Entity.User.SubUser;
import com.example.project.Repository.User.SubUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubUserService {

    @Autowired
    private SubUserRepository subUserRepository;

    public SubUser addSubUser(SubUser subUser){
        return subUserRepository.save(subUser);
    }

    public SubUser getSubUserDetails (long id){
        return subUserRepository.findById(id).orElse(null);
    }
}
