package com.example.pidev.dto;

import com.example.pidev.entities.Sprint;
import com.example.pidev.entities.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketResponseDto {
    private List<Ticket> issues;

        public List<Ticket> getIssues() {
        return issues;
    }
    public void setIssues(List<Ticket> issues) {
        this.issues = issues;
    }


}