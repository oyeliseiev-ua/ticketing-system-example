package com.sporty.ticketing_system.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTicketStatusEvent {
	private Long ticketId;
	private String status;
}
