package com.example.pidev.dto;

import com.example.pidev.entities.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Ticket}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketGetDto implements Serializable {
    Long id;
    String self;
    String key;
    FieldsDto fields;
    List<SprintGetDto> sprints;

    /**
     * DTO for {@link Ticket.Fields}
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FieldsDto implements Serializable {
        String summary;
        Float customfield_10016;
        IssuetypeDto issuetype;
        String description;
        PriorityDto priority;
        String created;
        String updated;

        /**
         * DTO for {@link Ticket.Fields.Issuetype}
         */
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class IssuetypeDto implements Serializable {
            String name;

        }

        /**
         * DTO for {@link Ticket.Priority}
         */
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class PriorityDto implements Serializable {
            String name;
        }
    }


}