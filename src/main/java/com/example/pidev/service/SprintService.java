package com.example.pidev.service;

import com.example.pidev.entities.JiraResponse2;

public interface SprintService {
    JiraResponse2 getAllSprints(long boardId);

}
