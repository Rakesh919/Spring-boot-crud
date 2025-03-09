package com.example.project.Service.User;

import com.example.project.Entity.User.User;
import com.example.project.Repository.User.UserReposotory;
import com.example.project.dto.response.ErrorResponse;
import com.example.project.utils.BCryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserReposotory repository;

    @Autowired
    private BCryptUtil bcryptUtil;

    public User addUser(User user){
        logger.info("addUser service method starts");
        user.setPassword(bcryptUtil.hashPassword(user.getPassword()));
        return repository.save(user);
    }

    public ErrorResponse loginUserService(String email, String password){
        logger.info("loginUser Service method starts ");
       User userDetails = repository.findByEmail(email);

       if(userDetails == null){
           logger.error("User Details not found for Email {} ",email);
//          return false;
           return new ErrorResponse("User Details not found for this Email","BAD-404");
       }

       if(!bcryptUtil.verifyPassword(password,userDetails.getPassword())){
           logger.error("Password did not match for this user {}",email);
//           return false;
           return new ErrorResponse("Password do not match","BAD-400");
       }

//       return true;
        return new ErrorResponse("true","SUC-200");
    }

    public User getUserByIdService(Integer id){
        logger.info("getUserByIdService method starts");
        User userDetails;
        userDetails = repository.findById(id).orElse(null);

        return userDetails;
    }

    public List<User> fetchAllUsersService(){
        logger.info("fetchAllUsers service method starts ");
        List<User> user;
        user= repository.findAll();
        return user;
    }

    public User updateUserService(User user){
        logger.info("userdateUser service service method starts ");
        User existingUser = repository.findById(user.getId()).orElse(null);
        assert existingUser !=null;
        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());

        return repository.save(existingUser);

    }

    public String delteUserByIdServide(Integer id){
        logger.info("deleteUserById service method starts ");
         repository.deleteById(id);
         return "User Deleted successfully";
    }


    public User findByEmailService(String email){
        logger.info("findByEmail service method starts");
        return repository.findByEmail(email);
    }

    public boolean isEmailExists(String email){
        return repository.existsByEmail(email);
    }
}
