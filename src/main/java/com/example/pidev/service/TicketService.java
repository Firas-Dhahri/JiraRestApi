package com.example.pidev.service;

import com.example.pidev.dto.TicketDto;
import com.example.pidev.entities.Ticket;

import java.util.List;

public interface TicketService  {



    public List<Ticket> getallticekts();
    public Ticket createIssue(TicketDto ticketDto) ;

    public String createProject(String projectKey,String projectName);

    public Ticket updateIssueByKey(String issueKey, String summary, String description) ;

    public boolean deleteIssue(String issueKey) ;










    }
