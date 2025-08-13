package com.sporty.ticketing_system.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignTicketEvent {
	private Long ticketId;
	private String assigneeId;
}
