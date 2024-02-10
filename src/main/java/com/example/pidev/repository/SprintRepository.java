package com.example.pidev.repository;

import com.example.pidev.entities.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint,Long> {
}
