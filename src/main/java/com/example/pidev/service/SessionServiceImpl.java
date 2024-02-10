package com.example.pidev.service;

import com.example.pidev.entities.Session;
import com.example.pidev.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService{

    @Autowired
    SessionRepository sessionRepository;
    @Override
    public Session ajoutersession(Session session) {
        Session addedSession = session;
        return sessionRepository.save(session);
    }

    @Override
    public List<Session> afficherSession() {
        return sessionRepository.findAll();
    }
}
