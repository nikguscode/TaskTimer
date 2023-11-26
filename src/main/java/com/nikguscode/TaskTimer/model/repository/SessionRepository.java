package com.nikguscode.TaskTimer.model.repository;

import com.nikguscode.TaskTimer.model.entity.UserState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface SessionRepository extends JpaRepository<UserState, Long>, CrudRepository<UserState, Long> {
    @Modifying
    @Query("UPDATE UserState u SET u.endDate = :endDate, u.formattedTime = :duration " +
            "WHERE u.sessionId = :sessionId")
    void updateEndDateAndDurationBySessionId(
            @Param("sessionId") Long sessionId,
            @Param("endDate") LocalDateTime endDate,
            @Param("duration") String duration
    );
}
