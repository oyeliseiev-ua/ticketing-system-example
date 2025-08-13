package com.sporty.ticketing_system.mapper;

import com.sporty.ticketing_system.entity.Ticket;
import com.sporty.ticketing_system.event.TicketSubmitEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

	Ticket ticketSubmitEventToEntity(TicketSubmitEvent ticketSubmitEvent);

}
