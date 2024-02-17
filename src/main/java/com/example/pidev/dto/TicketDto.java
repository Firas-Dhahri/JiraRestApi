package com.example.pidev.dto;

import jakarta.persistence.Embedded;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.pidev.entities.Ticket}
 */
@Value
@Data

public class TicketDto implements Serializable {
    Long id;
    String self;
    String key;
    @Embedded
    FieldsDto fields;
    String issueType;

    /**
     * DTO for {@link com.example.pidev.entities.Ticket.Fields}
     */
    @Value
    @Data
    public static class FieldsDto implements Serializable {

        String summary;
        String description;
        String created;
        String updated;
    }
}