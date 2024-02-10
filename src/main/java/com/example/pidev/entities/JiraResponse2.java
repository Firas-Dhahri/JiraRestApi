package com.example.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraResponse2 {
    private List<Sprint> values;




    public  List<Sprint>getSprint(){return  values ;}

    public void setSprint(List<Sprint> sprint) {
        this.values = sprint;
    }
}