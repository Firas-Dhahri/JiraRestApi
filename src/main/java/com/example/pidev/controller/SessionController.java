package com.example.pidev.controller;


import com.example.pidev.entities.Session;
import com.example.pidev.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/session")
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class SessionController {
    @Autowired
    SessionService sessionService;

    @PostMapping("/ajoutersession")
    public Session ajoutersession(@RequestBody Session session) {
        return sessionService.ajoutersession(session);
    }

    @GetMapping("/afficher")
    public List<Session> AfficherSession() {
        return sessionService.afficherSession();
    }
    }




