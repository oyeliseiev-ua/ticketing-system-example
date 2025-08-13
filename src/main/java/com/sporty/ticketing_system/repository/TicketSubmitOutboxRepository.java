package com.sporty.ticketing_system.repository;

import java.util.List;
import java.util.UUID;

import com.sporty.ticketing_system.entity.TicketSubmitOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketSubmitOutboxRepository extends JpaRepository<TicketSubmitOutbox, UUID> {

	List<TicketSubmitOutbox> findTop10ByPublishedFalseOrderByCreatedAtAsc();
}
