package com.example.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraResponse {
    private List<Ticket> issues;
    private List<Sprint> sprint;



    public List<Ticket> getIssues() {
        return issues;
    }

    public  List<Sprint>getSprint(){return  sprint ;}

    public void setIssues(List<Ticket> issues) {
        this.issues = issues;
    }
    public void setSprint(List<Sprint> sprint) {
        this.sprint = sprint;
    }
}