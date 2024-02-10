package com.example.pidev.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSession")
    Long idSession;

    @Column(name = "nomSession")
    String nomSession;

    @Column(name = "Date_debut")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime Date_debut;
}
