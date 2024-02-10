package com.example.pidev.service;

import com.example.pidev.entities.Ticket;

import java.util.List;

public interface TicketService  {



    public List<Ticket> getallticekts();

    Ticket ajouterticket (Ticket ticket);

    Ticket modifierticket(Ticket ticket);

    public void supprimierticekt(Long idticket);







}
