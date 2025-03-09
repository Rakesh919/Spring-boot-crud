package com.example.project.Repository.User;

//import SubUser;
import com.example.project.Entity.User.SubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubUserRepository extends JpaRepository<SubUser,Long> {
}
