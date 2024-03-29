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
    @Table(name = "Sprint")
    public class Sprint implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "idSprint")
        private long id;
        private String self;
        private String state;

        public String getName() {
            return name;
        }

        private String name;
        private String startDate;
        private String endDate;
        private String createdDate;
        private long originBoardId;
        private String goal;



        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "ticket_idticket")
        private Ticket ticket;

    }
