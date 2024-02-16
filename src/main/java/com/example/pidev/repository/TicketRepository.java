package com.example.pidev.repository;

import com.example.pidev.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    boolean existsByKey(String key);

    /*@Query("SELECT t FROM Ticket t JOIN t.sprints s WHERE s.name = :sprintName")
    List<Ticket> findTicketsBySprintName(@Param("sprintName") String sprintName);*/


}
