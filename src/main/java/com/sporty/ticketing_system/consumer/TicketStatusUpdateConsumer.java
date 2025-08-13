package com.sporty.ticketing_system.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sporty.ticketing_system.event.AssignTicketEvent;
import com.sporty.ticketing_system.event.UpdateTicketStatusEvent;
import com.sporty.ticketing_system.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TicketStatusUpdateConsumer {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private ObjectMapper objectMapper;

	@KafkaListener(topics = "${spring.kafka.ticket.update.topic}", groupId = "${spring.kafka.consumer.ticket.update.group}")
	public void consumeTicketAssignEvent(String message) throws Exception {
		try {
			UpdateTicketStatusEvent updateTicketStatusEvent = objectMapper.readValue( message, UpdateTicketStatusEvent.class );
			ticketService.updateTicketStatus( updateTicketStatusEvent );
		} catch (Exception e) {
			log.error( "Failed to process ticket submit event {}", message, e );
			throw e;
		}
	}
}
