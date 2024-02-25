package com.example.pidev.dto.modelMapper;


import com.example.pidev.dto.SprintGetDto;
import com.example.pidev.dto.TicketGetDto;
import com.example.pidev.entities.Sprint;
import com.example.pidev.entities.Ticket;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SprintMapper {
    private final ModelMapper modelMapper;

    public SprintMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SprintGetDto toDto(Sprint sprint) {
        return modelMapper.map(sprint, SprintGetDto.class);
    }

}
