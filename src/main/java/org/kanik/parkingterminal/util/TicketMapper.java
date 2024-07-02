package org.kanik.parkingterminal.util;

import org.kanik.parkingterminal.dtos.TicketDTO;
import org.kanik.parkingterminal.store.entities.TicketEntity;

public class TicketMapper {
    public static TicketEntity convertToEntity(TicketDTO ticketDTO){
        return TicketEntity.builder()
                .id(ticketDTO.getId())
                .entryTime(ticketDTO.getEntryTime())
                .payTime(ticketDTO.getPayTime())
                .build();
    }
    public static TicketDTO convertToDTO(TicketEntity ticketEntity){
        return TicketDTO.builder()
                .id(ticketEntity.getId())
                .entryTime(ticketEntity.getEntryTime())
                .payTime(ticketEntity.getPayTime())
                .build();
    }
}
