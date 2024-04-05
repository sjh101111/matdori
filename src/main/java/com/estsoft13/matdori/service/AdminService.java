package com.estsoft13.matdori.service;

import com.estsoft13.matdori.domain.User;
import com.estsoft13.matdori.util.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminService {

    // 사용자 등급(BEGINNER -> ASSOCIATE) 업데이트 로직
    public boolean isEligibleForAssociate(User user) {
        int commentCount = user.getComments().size(); // User 엔티티에 연결된 Comment의 수
        int reviewCount = user.getReviews().size();
        Role beginner = Role.ROLE_BEGINNER;
        if (commentCount >= 1 && reviewCount >= 1 &&
        user.getRole().equals(beginner)
        ) {
            return true;
        } else {
            return false;
        }
    }

    // 사용자 등급(ASSOCIATE -> MEMBER) 업데이트 로직
    public boolean isEligibleForMember(User user) {
        int commentCount = user.getComments().size(); // User 엔티티에 연결된 Comment의 수
        int reviewCount = user.getReviews().size();
        Role associate = Role.ROLE_ASSOCIATE;
        if (commentCount >= 3 && reviewCount >= 3
        && user.getRole().equals(associate) ) {
            return true;
        } else {
            return false;
        }
    }
}
