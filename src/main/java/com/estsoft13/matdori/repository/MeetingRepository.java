package com.estsoft13.matdori.repository;

import com.estsoft13.matdori.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByTitleContainingOrContentContainingOrRestaurant_NameContaining(String keyword1, String keyword2,String keyword3);

    List<Meeting> findAllByUserId(Long userId);
}
