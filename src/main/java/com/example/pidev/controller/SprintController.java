package com.example.pidev.controller;

import com.example.pidev.dto.SprintResponseDto;
import com.example.pidev.entities.Sprint;
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
}

