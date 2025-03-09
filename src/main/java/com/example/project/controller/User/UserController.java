package com.example.project.controller.User;

import com.example.project.Entity.User.User;
import com.example.project.Service.User.UserService;
import com.example.project.dto.response.ErrorResponse;
import com.example.project.dto.response.ResponseDto;
import com.example.project.utils.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwt;

    @PostMapping("/create")
    public ResponseEntity<?> addUserController(@Valid @RequestBody User user, BindingResult result) throws MessagingException {
        logger.info("addUserController method started");

        if (service.isEmailExists(user.getEmail())) {
            ErrorResponse error = new ErrorResponse("Email Already Exists", "BAD-400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        if (result.hasErrors()) {
            Map<String, String> map = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                map.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(map);
        }
        User data = service.addUser(user);

        String token = jwt.generateToken(data.getEmail());
        ResponseDto responseDto = new ResponseDto("User Created successfully", "SUC-200", token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/get-user/{user_id}")
    public ResponseEntity<?> getUserByIdController(@PathVariable Integer user_id) {
        logger.info("get User by id controller method starts");
        User user;
        try {
            user = service.getUserByIdService(user_id);
            if (user == null) {
                logger.warn("User Details not found for user Id {}", user_id);
                ErrorResponse errorResponse = new ErrorResponse("Details not found ", "BAD-400");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            logger.error("Exception occurred at getUserById method ", e);
            throw new RuntimeException(e);
        }
        ResponseDto responseDto = new ResponseDto("Details fetched successfully", "SUC-200", user);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsersController() {
        logger.info("getAllUsersController method starts ");
        List<User> usersList;
        try {

            usersList = service.fetchAllUsersService();
            if (usersList.isEmpty()) {
                logger.warn("No Details found in Db ");
                ErrorResponse errorResponse = new ErrorResponse("Details not found ", "BAD-400");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

        } catch (Exception e) {
            logger.error("Error occurred at getAllUsersController ", e);
            throw new RuntimeException(e);
        }

        ResponseDto responseDto = new ResponseDto("Details fetched successfully", "SUC-200", usersList);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserController(@Valid @RequestBody User user) {
        logger.info("updateUserController method starts ");

        User updatedDetails;
        try {
            User userDetails = service.getUserByIdService(user.getId());
            if (userDetails == null) {
                logger.error("User Details not found for this ID {}", user.getId());
                ErrorResponse errorResponse = new ErrorResponse("Details not found", "BAD-404");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            updatedDetails = service.updateUserService(user);
        } catch (Exception e) {
            logger.error("Exception occurred at updateUserController ", e);
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new ResponseDto("Details updated successfully", "SUC-200", updatedDetails));
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUserController(@RequestParam String email, @RequestParam String password) {
        logger.info("loginUser controller method starts ");
        try {
            ErrorResponse response = service.loginUserService(email, password);
            if (!response.getMessage().equals("true")) {
                logger.warn(response.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            String token = jwt.generateToken(email);
            return ResponseEntity.ok(new ResponseDto("User logged in successfully", "SUC-200", token));
        } catch (Exception e) {
            logger.error("Exception occurred at loginUserController ", e);
            throw new RuntimeException(e);
        }
    }
}
