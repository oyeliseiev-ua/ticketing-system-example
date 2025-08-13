package com.sporty.ticketing_system.controller;

import com.sporty.ticketing_system.dto.TicketSubmitDto;
import com.sporty.ticketing_system.service.TicketSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

	@Autowired
	private TicketSubmitService ticketSubmitService;

	@PostMapping("/submit")
	public ResponseEntity<TicketSubmitDto> submitTicket(@RequestBody TicketSubmitDto dto) {
		return ResponseEntity.ok(ticketSubmitService.submit( dto ));
	}

}
