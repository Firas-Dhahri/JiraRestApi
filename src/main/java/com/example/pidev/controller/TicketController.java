package com.example.pidev.controller;


import com.example.pidev.dto.TicketDto;
import com.example.pidev.entities.Ticket;
import com.example.pidev.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/ticket")
@CrossOrigin(origins = "http://localhost:4200/")

@RestController
public class TicketController {
    @Autowired
    TicketService ticketService;

    /*@PostMapping("/ajouterticekt")
    public Ticket ajouterticket(@RequestBody Ticket ticket) {
        Ticket tick = ticketService.ajouterticket(ticket);
        return tick;

    }*/
    @GetMapping("/afficherlestockets")
    public List<Ticket> afficherTicket() {
        return ticketService.getallticekts();
    }

    /*@PostMapping("/AjouterTicket")
    public Ticket createIssue(@RequestParam String key,
                              @RequestParam String issueType,
                              @RequestParam String summary,
                              @RequestParam String description) {
        return ticketService.addIssue(key ,issueType, summary, description);
    }*/
   @PostMapping("/createIssue")
    public ResponseEntity<Ticket> createIssue(@RequestBody TicketDto ticketDto) {
        Ticket createdIssue = ticketService.createIssue(ticketDto);
        if (createdIssue != null) {
            return new ResponseEntity<>(createdIssue, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/{key}")
    public Ticket updateTicket(@PathVariable String key,
                               @RequestParam(required = false) String summary,
                               @RequestParam(required = false) String description) {
        return ticketService.updateIssueByKey(key, summary, description);
    }


    @PostMapping("/createproj")
    public ResponseEntity<String> createProject(@RequestParam String projectName,
                                                @RequestParam String projectKey ) {
        String project = ticketService.createProject( projectKey,projectName);
        if (project != null) {
            return new ResponseEntity<>("Project created with key: " + projectKey, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create project", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete/{issueKey}")
    public ResponseEntity<String> deleteIssue(@PathVariable String issueKey) {
        boolean deleted = ticketService.deleteIssue(issueKey);
        if (deleted) {
            return ResponseEntity.ok().body("Issue with key " + issueKey + " has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete issue with key " + issueKey + ".");
        }
    }
}






