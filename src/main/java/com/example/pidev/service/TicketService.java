package com.example.pidev.service;

import com.example.pidev.dto.TicketCreationDto;
import com.example.pidev.dto.TicketGetDto;
import com.example.pidev.entities.Ticket;

import java.util.List;

public interface TicketService  {



    public List<Ticket> getallticekts();
    public TicketGetDto createIssue(TicketCreationDto ticketCreationDto) ;
    public String createProject(String projectKey,String projectName);

    public Ticket updateIssueByKey(String issueKey, String summary, String description) ;

    public boolean deleteIssue(String issueKey) ;

    void affectTicketsToSprint(long sprintId, List<Long> ticketIds);

    //public void updateSprintFieldInJira(String ticketKey, String sprintName) ;









    }
