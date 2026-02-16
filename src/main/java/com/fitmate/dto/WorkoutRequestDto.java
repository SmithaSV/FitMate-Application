package com.fitmate.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutRequestDto {
    @NotBlank(message="Activity type required")
    private String activityType;

}
