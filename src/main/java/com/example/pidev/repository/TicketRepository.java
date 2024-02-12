package com.example.pidev.repository;

import com.example.pidev.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    boolean existsByKey(String key);

}
