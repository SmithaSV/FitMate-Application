package com.fitmate.mapper;

import com.fitmate.dto.WorkoutRequestDto;
import com.fitmate.dto.WorkoutResponseDto;
import com.fitmate.entity.User;
import com.fitmate.entity.Workout;

import java.time.LocalDateTime;

public class WorkoutMapper {
    public static WorkoutResponseDto todto(Workout workout) {
        return WorkoutResponseDto.builder()
                .id(workout.getId())
                .activityType(workout.getActivityType())
                .startTime(workout.getStartTime())
                .endTime(workout.getEndTime())
                .durationMinutes(workout.getDurationMinutes())
                .caloriesBurned(workout.getCaloriesBurned())
                .build();
    }
    public static Workout toEntity(WorkoutRequestDto dto, User user){
        return Workout.builder()
                .activityType(dto.getActivityType())
                .startTime(LocalDateTime.now())
                .user(user)
                .build();
    }
}
