package com.estsoft13.matdori.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="username", nullable = false)
    private  String username;

    @Column(name ="email", nullable = false, unique = true)
    private String email;

    @Column(name ="password", nullable = false)
    private String password;

    //@Enumerated(EnumType.STRING)
    //private Role role;
}

/*public enum Role{
    ADMIN,
    USER
}*/
