package com.fitmate.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@Builder
public class WorkoutResponseDto {
    private Long id;
    private String activityType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer durationMinutes;
    private Integer caloriesBurned;

}
