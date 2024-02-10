package com.example.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraResponse {
    private List<Ticket> issues;

    public List<Ticket> getIssues() {
        return issues;
    }

    public void setIssues(List<Ticket> issues) {
        this.issues = issues;
    }
}