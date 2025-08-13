package com.sporty.ticketing_system.service;

import com.sporty.ticketing_system.entity.Ticket;
import com.sporty.ticketing_system.entity.TicketStatus;
import com.sporty.ticketing_system.event.AssignTicketEvent;
import com.sporty.ticketing_system.event.TicketSubmitEvent;
import com.sporty.ticketing_system.event.UpdateTicketStatusEvent;
import com.sporty.ticketing_system.mapper.TicketMapper;
import com.sporty.ticketing_system.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private TicketMapper ticketMapper;

	@Transactional
	public void createTicket(TicketSubmitEvent ticketSubmitEvent) {
		if ( ticketRepository.findById( ticketSubmitEvent.getId() ).isPresent() ) {
			//handle idempotency
			log.info( "Skip duplicate ticket submit event {}",ticketSubmitEvent.getId() );
			return;
		}
		Ticket ticket = ticketRepository.save( ticketMapper.ticketSubmitEventToEntity( ticketSubmitEvent ) );
		log.info( "Ticket {} is created", ticket.getId() );
	}

	@Transactional
	public void updateTicketAssignment(AssignTicketEvent assignTicketEvent) {
		Ticket ticket = ticketRepository.findById( assignTicketEvent.getTicketId() ).orElseThrow(() -> new IllegalArgumentException(
				"Ticket is not found by id: " + assignTicketEvent.getTicketId() ) );
		if ( ticket.getAssigneeId() != null ) {
			log.info( "Ticket {} is already assigned, it will be re-assigned from {} to {}", ticket.getId(), ticket.getAssigneeId(), assignTicketEvent.getAssigneeId() );
		}
		ticket.setAssigneeId( assignTicketEvent.getAssigneeId() );
		ticketRepository.save( ticket );
		log.info( "Ticket {} is successfully assigned to {}", ticket.getId(), assignTicketEvent.getAssigneeId() );
	}

	@Transactional
	public void updateTicketStatus(UpdateTicketStatusEvent updateTicketStatusEvent) {
		Ticket ticket = ticketRepository.findById( updateTicketStatusEvent.getTicketId() ).orElseThrow(() -> new IllegalArgumentException(
				"Ticket is not found by id: " + updateTicketStatusEvent.getTicketId() ) );
		TicketStatus status = TicketStatus.fromValue( updateTicketStatusEvent.getStatus() );
		if ( ticket.getStatus() != null ) {
			log.info( "Ticket {} status will be updated from {} to {}", ticket.getId(), ticket.getStatus(), status );
		}
		ticket.setStatus( status );
		ticketRepository.save( ticket );
		log.info( "Ticket {} status is successfully updated", ticket.getId() );
	}
}
