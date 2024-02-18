package com.example.pidev.dto;

import com.example.pidev.entities.Ticket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TicketResponseDto {
    private Ticket issues;




}