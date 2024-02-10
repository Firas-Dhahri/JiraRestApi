package com.example.pidev.controller;

import com.example.pidev.entities.JiraResponse2;
import com.example.pidev.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SprintController {
    @Autowired
    SprintService sprintService;

    @GetMapping("/affichersprint/{boardId}")
    public JiraResponse2 afficherSprint(@PathVariable long boardId) {
        return sprintService.getAllSprints(boardId);
    }
}
