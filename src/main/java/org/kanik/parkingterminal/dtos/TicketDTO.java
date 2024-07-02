package org.kanik.parkingterminal.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Builder
@Getter
@Setter
@Data
public class TicketDTO {
    Integer id;

    LocalDateTime entryTime;

    LocalDateTime payTime;
}