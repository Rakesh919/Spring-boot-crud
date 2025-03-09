package com.example.project.Repository.User;

import com.example.project.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReposotory extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
