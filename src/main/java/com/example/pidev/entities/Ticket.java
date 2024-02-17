package com.example.pidev.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @Embedded
    private Fields fields;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sprint> sprints = new ArrayList<>();

    // Getter and setter for fields
    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public void setSprint(Sprint sprint) {
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Fields {
        private String summary;
        private String description;
        @Embedded
        private Priority priority;
        @Embedded
        private Status status;
        @Embedded
        private Assignee assignee;
        @Embedded
        private Reporter reporter;
        private String created;
        private String updated;
    }

    @Embeddable
    @Data
    public static class Priority {
        @Column(name = "priority_name") // Unique column name for Priority name
        private String name;
    }

    @Embeddable
    @Data
    public static class Status {
        @Column(name = "status_name") // Unique column name for Status name
        private String name;
    }

    @Embeddable
    @Data
    public static class Assignee {
        @Column(name = "assignee_name") // Unique column name for Assignee name
        private String name;
    }

    @Embeddable
    @Data
    public static class Reporter {
        @Column(name = "reporter_name") // Unique column name for Reporter name
        private String name;
    }
}