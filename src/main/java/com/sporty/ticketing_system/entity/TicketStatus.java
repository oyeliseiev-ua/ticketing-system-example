package com.sporty.ticketing_system.entity;

import java.util.Arrays;

public enum TicketStatus {
	OPEN, IN_PROGRESS, RESOLVED, CLOSED;

	public static TicketStatus fromValue(String value) {
		return Arrays.stream( values() )
				.filter( v -> v.name().equalsIgnoreCase( value ) )
				.findFirst()
				.orElseThrow( () -> new IllegalArgumentException( "Not valid status name: " + value + ", must be in " + Arrays.toString(
						values() ) ) );
	}
}
