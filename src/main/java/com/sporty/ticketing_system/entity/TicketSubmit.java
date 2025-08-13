package com.sporty.ticketing_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketSubmit {

	@Id
	@GeneratedValue
	private Long id;
	private String userId;
	private String subject;
	private String description;
}
