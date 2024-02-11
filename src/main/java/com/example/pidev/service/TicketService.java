package com.example.pidev.service;

import com.example.pidev.entities.Ticket;

import java.util.List;

public interface TicketService  {



    public List<Ticket> getallticekts();
    public Ticket createIssue(String key, String issueType, String summary, String description) ;

    public String createProject(String projectName);








}
