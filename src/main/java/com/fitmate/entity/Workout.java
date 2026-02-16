package com.fitmate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="workouts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workout {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
   private Long id;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id",nullable=false)
    private User user;
    @Column(nullable=false)
    private String activityType;
    @Column(nullable=false)
    private LocalDateTime startTime;
    @Column(nullable=true)
   private LocalDateTime endTime;
    @Column(nullable=true)
    private Integer durationMinutes;
    @Column(nullable=true)
    private Integer caloriesBurned;

}
