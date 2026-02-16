package com.fitmate.Repository;

import com.fitmate.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout,Long> {
    List<Workout> findByUserEmail(String email);

}
