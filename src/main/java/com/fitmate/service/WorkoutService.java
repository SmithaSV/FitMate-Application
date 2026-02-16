package com.fitmate.service;

import com.fitmate.dto.WorkoutRequestDto;
import com.fitmate.dto.WorkoutResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface WorkoutService {
    WorkoutResponseDto startWorkout(WorkoutRequestDto dto, String email);
    WorkoutResponseDto stopWorkout(Long workoutId);
    List<WorkoutResponseDto> getUserWorkouts(String email);

}
