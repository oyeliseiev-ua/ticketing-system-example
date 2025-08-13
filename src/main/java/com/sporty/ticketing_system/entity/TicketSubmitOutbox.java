package com.sporty.ticketing_system.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketSubmitOutbox {

	@Id
	@GeneratedValue
	private UUID id;
	private Long aggregateId;
	private String type;
	private String payload;
	private LocalDateTime createdAt;
	private boolean published;
}
