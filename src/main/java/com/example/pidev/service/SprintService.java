package com.example.pidev.service;

import com.example.pidev.dto.SprintResponseDto;
import com.example.pidev.entities.Sprint;

public interface SprintService {
    SprintResponseDto getAllSprints(long boardId);

    public Sprint createSprint(long boardId, String name, String startDate, String endDate,String goal) ;


    }
