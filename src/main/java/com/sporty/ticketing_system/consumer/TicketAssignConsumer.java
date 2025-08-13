package com.sporty.ticketing_system.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sporty.ticketing_system.event.AssignTicketEvent;
import com.sporty.ticketing_system.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TicketAssignConsumer {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private ObjectMapper objectMapper;

	@KafkaListener(topics = "${spring.kafka.ticket.assign.topic}", groupId = "${spring.kafka.consumer.ticket.assign.group}")
	public void consumeTicketAssignEvent(String message) throws Exception {
		try {
			AssignTicketEvent assignTicketEvent = objectMapper.readValue( message, AssignTicketEvent.class );
			ticketService.updateTicketAssignment( assignTicketEvent );
		} catch (Exception e) {
			log.error( "Failed to process ticket submit event {}", message, e );
			throw e;
		}
	}
}
