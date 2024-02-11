package com.example.pidev.dto;

import com.example.pidev.entities.Sprint;
import lombok.Data;

import java.util.List;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class SprintResponseDto {
    private List<Sprint> values;
    public  List<Sprint>getSprint(){return  values ;}

    public void setSprint(List<Sprint> sprint) {
        this.values = sprint;
    }

}