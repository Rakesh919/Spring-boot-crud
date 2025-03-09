package com.example.project.Entity.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sub_user")
public class SubUser {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="main_user_id",referencedColumnName = "id",nullable = false)
//    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private User user_id;
}
