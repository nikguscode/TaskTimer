package com.nikguscode.TaskTimer.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserState {

    @Transient
    private long userId;

    @Transient
    private String userName;

    @Transient
    private boolean isStarted;

    @Transient
    private int sessionCounter;

    @Transient
    private String lastActiveCategoryName;

    @Transient
    private int lastMessageId;

}
