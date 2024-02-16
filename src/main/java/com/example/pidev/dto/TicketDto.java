package com.example.pidev.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.pidev.entities.Ticket}
 */
@Value
public class TicketDto implements Serializable {
    Long id;
    String self;
    String key;
    FieldsDto fields;
    String issueType;

    /**
     * DTO for {@link com.example.pidev.entities.Ticket.Fields}
     */
    @Value
    public static class FieldsDto implements Serializable {
        String summary;
        String description;
        String created;
        String updated;
    }
}