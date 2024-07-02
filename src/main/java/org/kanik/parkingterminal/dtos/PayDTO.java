package org.kanik.parkingterminal.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class PayDTO {
    int cost;
    int ticket;
    String card;
}
