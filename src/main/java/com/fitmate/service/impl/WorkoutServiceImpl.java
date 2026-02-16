package com.fitmate.service.impl;

import com.fitmate.Repository.UserRepository;
import com.fitmate.Repository.WorkoutRepository;
import com.fitmate.dto.WorkoutRequestDto;
import com.fitmate.dto.WorkoutResponseDto;
import com.fitmate.entity.User;
import com.fitmate.entity.Workout;
import com.fitmate.mapper.WorkoutMapper;
import com.fitmate.service.WorkoutService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkoutServiceImpl implements WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    @Override
    public WorkoutResponseDto startWorkout(WorkoutRequestDto dto, String email) {
       User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("user not found"));
        Workout workout= Workout.builder()
                .user(user)
                .activityType(dto.getActivityType())
                .startTime(LocalDateTime.now())
                .build();
        return WorkoutMapper.todto(workoutRepository.save(workout));

    }

    @Override
    public WorkoutResponseDto stopWorkout(Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new RuntimeException("Workout not found"));
        workout.setEndTime(LocalDateTime.now());
        int duration = (int) Duration.between(
                workout.getStartTime(),
                workout.getEndTime()
        ).toMinutes();
        workout.setDurationMinutes(duration);
        int calories = calculateCalories(workout.getActivityType(), duration);
        workout.setCaloriesBurned(calories);
        return WorkoutMapper.todto(workoutRepository.save(workout));
    }

    @Override
    public List<WorkoutResponseDto> getUserWorkouts(String email) {
       // User user = userRepository.findByEmail(email)
               // .orElseThrow(() -> new RuntimeException("User not found"));
        return workoutRepository
                .findByUserEmail(email)
                .stream()
                .map(WorkoutMapper::todto)
                .toList();
    }

    private int calculateCalories(String type,int duration){
        int perMinute;
        switch(type.toUpperCase()){
            case "RUNNING": perMinute = 10; break;
            case "WALKING": perMinute=5;break;
            case "GYM": perMinute=8;break;
            case "CYCLING": perMinute=7;break;
            default: perMinute=6;

        }
        return duration *perMinute;




    }

}
