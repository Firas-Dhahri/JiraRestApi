package com.example.pidev.dto;

import com.example.pidev.entities.Sprint;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Sprint}
 */
@Value
public  class SprintGetDto implements Serializable {
    long id;
    String self;
    String state;
    String name;
    String startDate;
    String endDate;
    String createdDate;
    long originBoardId;
    String goal;
}