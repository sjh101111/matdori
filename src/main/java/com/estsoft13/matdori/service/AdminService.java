package com.estsoft13.matdori.service;

import com.estsoft13.matdori.domain.User;
import com.estsoft13.matdori.util.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminService {
    public boolean isEligibleForAssociate(User user) {
        int commentCount = user.getComments().size(); // User 엔티티에 연결된 Comment의 수
        int reviewCount = user.getReviews().size();
        Role beginner = Role.Beginner;
        if (commentCount >= 1 && reviewCount >= 1 &&
        user.getRole().equals(beginner)
        ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEligibleForMember(User user) {
        int commentCount = user.getComments().size(); // User 엔티티에 연결된 Comment의 수
        int reviewCount = user.getReviews().size();
        Role associate = Role.Associate;
        if (commentCount > 3 && reviewCount > 3
        && user.getRole().equals(associate) ) {
            return true;
        } else {
            return false;
        }
    }
}
