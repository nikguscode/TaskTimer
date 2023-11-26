package com.nikguscode.TaskTimer.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "session_c")
@Getter
@Setter
public class UserState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private long sessionId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "active_category_name")
    private String activeCategoryName;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "duration")
    private String formattedTime;

    // поля, которые не взаимодействуют с базой данных
    @Transient
    private boolean isStarted;

    @Transient
    private Instant startTime;

    @Transient
    private Instant endTime;

}
