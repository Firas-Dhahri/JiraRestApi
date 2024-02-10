package com.example.pidev.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Ticket")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idticket")
    private Long id;

    private String self;
    private String key;

    @Transient // This annotation tells JPA to ignore this field when mapping to the database
    private Fields fields;

    // Getter and setter for fields
    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    @Data // Lombok annotation for generating getters and setters
    public static class Fields {
        private String summary;
        private String description;
        private Priority priority;
        private Status status;
        private Assignee assignee;
        private Reporter reporter;
        private String created;
        private String updated;
    }

    @Data // Lombok annotation for generating getters and setters
    public static class Priority {
        private String name;
    }

    @Data // Lombok annotation for generating getters and setters
    public static class Status {
        private String name;
    }

    @Data // Lombok annotation for generating getters and setters
    public static class Assignee {
        private String name;
    }

    @Data // Lombok annotation for generating getters and setters
    public static class Reporter {
        private String name;
    }
}
