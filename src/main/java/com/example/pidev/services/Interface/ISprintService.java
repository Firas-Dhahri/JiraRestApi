package com.example.pidev.services.Interface;

import com.example.pidev.dto.SprintCreationDto;
import com.example.pidev.dto.SprintGetDto;
import com.example.pidev.dto.SprintResponseDto;
import com.example.pidev.entities.Sprint;

import java.util.List;

public interface ISprintService {
    public List<Sprint> getAllSprints(long originBoardId);

    public SprintGetDto createSprint(SprintCreationDto sprintCreationDto) ;
    public SprintGetDto updateSprint(Long sprintId,SprintCreationDto sprintCreationDto);
    public boolean deleteSprint(long sprintId) ;
    public List<String> getAllBoardName() ;


    }
