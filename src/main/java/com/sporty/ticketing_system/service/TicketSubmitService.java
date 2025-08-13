package com.sporty.ticketing_system.service;

import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sporty.ticketing_system.dto.TicketSubmitDto;
import com.sporty.ticketing_system.entity.TicketSubmit;
import com.sporty.ticketing_system.entity.TicketSubmitOutbox;
import com.sporty.ticketing_system.mapper.TicketSubmitMapper;
import com.sporty.ticketing_system.repository.TicketSubmitOutboxRepository;
import com.sporty.ticketing_system.repository.TicketSubmitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TicketSubmitService {

	@Autowired
	private TicketSubmitRepository ticketSubmitRepository;

	@Autowired
	private TicketSubmitOutboxRepository ticketSubmitOutboxRepository;

	@Autowired
	private TicketSubmitMapper ticketSubmitMapper;

	@Autowired
	private ObjectMapper objectMapper;

	@Transactional
	public TicketSubmitDto submit(TicketSubmitDto dto) {
		try {
			TicketSubmit ticketSubmit = ticketSubmitRepository.save( ticketSubmitMapper.toEntity( dto ) );
			log.info( "Ticket submit is created {}", ticketSubmit.getId() );
			TicketSubmitOutbox outboxEvent = TicketSubmitOutbox.builder()
					.aggregateId( ticketSubmit.getId() )
					.type( "TicketSubmitted" )
					.payload( objectMapper.writeValueAsString( ticketSubmit ))
					.createdAt( LocalDateTime.now() )
					.build();
			outboxEvent = ticketSubmitOutboxRepository.save( outboxEvent );
			log.info( "Ticket submit outbox event is created {}", outboxEvent.getId());
			return ticketSubmitMapper.toDto( ticketSubmit );
		}
		catch (JsonProcessingException e) {
			throw new RuntimeException( e );
		}

	}

}
