package com.sporty.ticketing_system.mapper;

import com.sporty.ticketing_system.dto.TicketSubmitDto;
import com.sporty.ticketing_system.entity.TicketSubmit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketSubmitMapper {
	TicketSubmitDto toDto(TicketSubmit entity);
	TicketSubmit toEntity(TicketSubmitDto dto);
}
