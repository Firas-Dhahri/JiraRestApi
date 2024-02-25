package com.example.pidev.controller;

import com.example.pidev.dto.*;
import com.example.pidev.entities.Sprint;
import com.example.pidev.services.Interface.ISprintService;
import com.example.pidev.services.impl.SprintService;
import com.example.pidev.services.impl.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sprints")
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class SprintController {
    @Autowired
    SprintService sprintService;
    @Autowired
    TicketService ticketService;

    @GetMapping("/affichersprint/{boardId}")
    public List <Sprint> afficherSprint(@PathVariable long boardId) {
        return sprintService.getAllSprints(boardId);
    }

    @PostMapping("/ajoutersprint")
    public ResponseEntity<SprintGetDto> createSprint(@RequestBody SprintCreationDto sprintCreationDto   ) {
        SprintGetDto createdSprint = sprintService.createSprint(sprintCreationDto);
        if (createdSprint != null) {
            return new ResponseEntity<>(createdSprint, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/{sprintId}")
    public SprintGetDto updateSprint(@PathVariable Long sprintId ,@RequestBody SprintCreationDto sprintCreationDto) {
            return sprintService.updateSprint(sprintId, sprintCreationDto);
    }

    @DeleteMapping("/delete/{sprintId}")
    public ResponseEntity<String> deleteIssue(@PathVariable Long sprintId) {
        boolean deleted = sprintService.deleteSprint(sprintId);
        if (deleted) {
            return ResponseEntity.ok().body("Sprint with id " + sprintId + " has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete sprint with key " + sprintId + ".");
        }
    }
    @PutMapping("/{sprintId}/tickets")
    public ResponseEntity<String> associateTicketsWithSprint(@PathVariable("sprintId") long sprintId, @RequestBody List<Long> ticketIds) {
        try {
            ticketService.affectTicketsToSprint(sprintId, ticketIds);
            return ResponseEntity.ok("Tickets associated with sprint successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while associating tickets with sprint.");
        }
    }
    @GetMapping("/getProjectName")
    public List<String> getAllBoardName() {
        return sprintService.getAllBoardName();
    }

}



