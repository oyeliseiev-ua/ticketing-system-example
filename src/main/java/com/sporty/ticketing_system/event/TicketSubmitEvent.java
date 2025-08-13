package com.sporty.ticketing_system.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSubmitEvent {

	private Long id;
	private String userId;
	private String subject;
	private String description;
}
