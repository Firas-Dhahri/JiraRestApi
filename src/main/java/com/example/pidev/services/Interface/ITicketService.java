package com.example.pidev.services.Interface;

import com.example.pidev.dto.TicketCreationDto;
import com.example.pidev.dto.TicketGetDto;
import com.example.pidev.entities.Ticket;

import java.util.List;

public interface ITicketService  {



    public List<Ticket> getallticekts();
    public TicketGetDto createIssue(TicketCreationDto ticketCreationDto) ;
    public String createProject(String projectKey,String projectName);

    public TicketGetDto updateIssueByKey(String issueKey, TicketCreationDto ticketCreationDto) ;
    public boolean deleteIssue(String issueKey) ;

    void affectTicketsToSprint(long sprintId, List<Long> ticketIds);

    //public void updateSprintFieldInJira(String ticketKey, String sprintName) ;

    public TicketGetDto getTicektById(String key) ;








    }
