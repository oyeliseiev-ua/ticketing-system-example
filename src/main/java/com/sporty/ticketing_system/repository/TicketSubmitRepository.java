package com.sporty.ticketing_system.repository;

import com.sporty.ticketing_system.entity.TicketSubmit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketSubmitRepository extends JpaRepository<TicketSubmit, Long> {
}
