package com.sporty.ticketing_system.publisher;

import java.util.List;

import com.sporty.ticketing_system.entity.TicketSubmitOutbox;
import com.sporty.ticketing_system.repository.TicketSubmitOutboxRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TicketSubmitEventPublisher {

	@Autowired
	private TicketSubmitOutboxRepository ticketSubmitOutboxRepository;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value( "${spring.kafka.ticket.submit.topic}" )
	private String supportsTicketTopic;

	@Scheduled(fixedDelay = 5000)
	public void publishTicketSubmitEvents() {
		List<TicketSubmitOutbox> events = ticketSubmitOutboxRepository.findTop10ByPublishedFalseOrderByCreatedAtAsc();
		for ( TicketSubmitOutbox event : events ) {
			try {
				kafkaTemplate.send( new ProducerRecord<>( supportsTicketTopic, event.getPayload()) ).get();
				event.setPublished( true );
				ticketSubmitOutboxRepository.save( event );
				log.info( "Ticket submit event {} is published to {}", event.getId(), supportsTicketTopic );
			}
			catch (Exception e) {
				log.error( "Failed to publish event {}", event.getId(), e );
			}
		}
	}
}
