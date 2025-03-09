package com.example.project.Entity.User;


//import com.example.project.annotations.FieldMatch;
import com.example.project.annotations.PasswordValidator;
//import com.example.project.annotations.UniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
@EntityListeners(AuditingEntityListener.class)
//@FieldMatch(first="password",second="confirmPassword",message= "Passwords do not match")
//@FieldMatch(first="password", second = "confirmPassword" , message="Passwords do not match")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message="Name can't be empty")
    private String name;

//    @UniqueEmail
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email can't be blank")
    @Column(unique = true,nullable = false, name = "Email Already exists in DB")
    private String email;

    @NotBlank(message = "Password can't be empty")
    @Size(min = 4,message = "Password must be minimum 4 digit long")
    @PasswordValidator
    private String password;

    @Column(name = "user_role")
    private String userRole = "NORMAL_USER";




//    @CreatedBy
//    @Column(updatable=false)
//    private String createdBy;

    @CreatedDate
    @Column(updatable=false)
    private LocalDateTime createdAt;

//    @LastModifiedBy
//    @Column(insertable = false)
//    private String updatedBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
