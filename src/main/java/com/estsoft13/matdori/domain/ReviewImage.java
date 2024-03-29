package com.estsoft13.matdori.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "img_path")
    private String imgPath;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public ReviewImage(String imgPath, Review review) {
        this.imgPath = imgPath;
        this.review = review;
    }
}
