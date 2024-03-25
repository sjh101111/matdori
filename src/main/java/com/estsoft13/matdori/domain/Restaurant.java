package com.estsoft13.matdori.domain;

import jakarta.persistence.*;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    // @Column(name = "category", nullable = false)
    @Column(name = "category")
    private String category;
}
