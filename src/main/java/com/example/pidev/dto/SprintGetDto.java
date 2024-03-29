package com.example.pidev.dto;

import com.example.pidev.entities.Sprint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Sprint}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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