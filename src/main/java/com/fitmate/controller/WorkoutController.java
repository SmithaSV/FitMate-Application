package com.fitmate.controller;

import com.fitmate.dto.WorkoutRequestDto;
import com.fitmate.dto.WorkoutResponseDto;
import com.fitmate.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {
    private final WorkoutService workoutService;


    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }
    @PostMapping("/start")
    public WorkoutResponseDto startWorkout(@RequestBody @Valid WorkoutRequestDto dto, Authentication authentication){
        return workoutService.startWorkout(dto, authentication.getName());
    }
    @PostMapping("/stop/{id}")
    public WorkoutResponseDto stopWorkout(@PathVariable Long id){
        return workoutService.stopWorkout(id);

    }
    @GetMapping("/history")
    public List<WorkoutResponseDto> getWorkoutHistory(Authentication authentication){
        String email= authentication.getName();
        return workoutService.getUserWorkouts(email);

    }
}
