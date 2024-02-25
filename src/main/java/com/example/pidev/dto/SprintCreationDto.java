package com.example.pidev.dto;

import com.example.pidev.entities.Sprint;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Sprint}
 */
@Value
public class SprintCreationDto implements Serializable {
    String name;
    String startDate;
    String endDate;
    String state;
    long originBoardId;
    String goal;
}