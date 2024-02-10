package com.example.pidev.controller;


import com.example.pidev.entities.Ticket;
import com.example.pidev.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/ticket")
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class TicketController {
    @Autowired
    TicketService ticketService;

    @PostMapping("/ajouterticekt")
    public Ticket ajouterticket(@RequestBody Ticket ticket) {
        Ticket tick = ticketService.ajouterticket(ticket);
        return tick;

    }
    @GetMapping("/afficherlestockets")
    public List<Ticket> afficherTicket(){


        return ticketService.getallticekts();
    }


    @PutMapping("/updateticket/{idticket}")
    public Ticket updateTicket(@PathVariable Long idticket, @RequestBody Ticket ticket) {
        //ticket.setIdticket(idticket);
        return ticketService.modifierticket(ticket);
    }
    @DeleteMapping("/deleteticekt/{idticket}")
    public void supprimierticket(@PathVariable Long idticket) {
        ticketService.supprimierticekt(idticket);
    }


}
