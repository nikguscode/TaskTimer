package com.nikguscode.TaskTimer.model.repository;

import com.nikguscode.TaskTimer.model.entity.SessionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface SessionRepository extends JpaRepository<SessionState, Long>, CrudRepository<SessionState, Long> {

    ArrayList<SessionState> findSessionIdByUserId(long userId);

    ArrayList<SessionState> getDurationByUserId(long userId);

    @Modifying
    @Query("UPDATE SessionState s SET s.endDate = :endDate, s.duration = :duration " +
            "WHERE s.sessionId = :sessionId")
    void finishSession(
            @Param("sessionId") Long sessionId,
            @Param("endDate") LocalDateTime endDate,
            @Param("duration") long duration
    );

    @Query("SELECT s.activeCategoryName FROM SessionState s " +
            "WHERE s.userId = :userId AND s.sessionId = (SELECT MAX(s2.sessionId) FROM SessionState s2 WHERE s2.userId = :userId)")
    String getLastSessionCategoryName(@Param("userId") Long userId);


}
