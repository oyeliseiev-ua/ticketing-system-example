package com.sporty.ticketing_system.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sporty.ticketing_system.event.TicketSubmitEvent;
import com.sporty.ticketing_system.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TicketSubmitEventConsumer {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private ObjectMapper objectMapper;

	@KafkaListener(topics = "${spring.kafka.ticket.submit.topic}", groupId = "${spring.kafka.consumer.ticket.submit.group}")
	public void consumeTicketSubmitEvent(String message) throws Exception {
		try {
			TicketSubmitEvent ticketSubmitEvent = objectMapper.readValue( message, TicketSubmitEvent.class );
			ticketService.createTicket( ticketSubmitEvent );
		} catch (Exception e) {
			log.error( "Failed to process ticket submit event {}", message, e );
			throw e;
		}
	}
}
