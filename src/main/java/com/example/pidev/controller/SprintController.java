package com.example.pidev.controller;

import com.example.pidev.dto.SprintResponseDto;
import com.example.pidev.entities.Sprint;
import com.example.pidev.entities.Ticket;
import com.example.pidev.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SprintController {
    @Autowired
    SprintService sprintService;

    @GetMapping("/affichersprint/{boardId}")
    public SprintResponseDto afficherSprint(@PathVariable long boardId) {
        return sprintService.getAllSprints(boardId);
    }

    @PostMapping("/ajoutersprint/{boardId}")
    public ResponseEntity<Sprint> createSprint(@PathVariable long boardId,
                                               @RequestParam String name,
                                               @RequestParam String startDate,
                                               @RequestParam String goal,
                                               @RequestParam String endDate) {
        Sprint createdSprint = sprintService.createSprint(boardId, name, startDate, endDate,goal);
        if (createdSprint != null) {
            return new ResponseEntity<>(createdSprint, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("update/{sprintId}")
    public ResponseEntity<Sprint> updateSprint(
            @PathVariable Long sprintId,
            @RequestParam(required = false) Long  boardId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String createdDate,
                        @RequestParam(required = false) String goal) {
        Sprint sprint = sprintService.updateSprint(sprintId, name, startDate, endDate, goal, boardId,state,createdDate);
        if (sprint != null) {
            return new ResponseEntity<>(sprint, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{sprintId}")
    public ResponseEntity<String> deleteIssue(@PathVariable Long sprintId) {
        boolean deleted = sprintService.deleteSprint(sprintId);
        if (deleted) {
            return ResponseEntity.ok().body("Sprint with id " + sprintId + " has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete sprint with key " + sprintId + ".");
        }
    }
}



