package com.example.pidev.service;

import com.example.pidev.entities.Session;

import java.util.List;

public interface SessionService {

    Session ajoutersession (Session session);
    List<Session> afficherSession();

}
