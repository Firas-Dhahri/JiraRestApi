package com.example.pidev.service;

import com.example.pidev.dto.SprintResponseDto;

public interface SprintService {
    SprintResponseDto getAllSprints(long boardId);

}
