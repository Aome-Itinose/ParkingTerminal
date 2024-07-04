package org.kanik.parkingterminal.util;

import lombok.Getter;
import org.kanik.parkingterminal.store.entities.TicketEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@Getter
public class CostCalculator {
    @Value("${exitTime}")
    private int exitTime;
    @Value("${freeTime}")
    private int freeTime;
    @Value("${pricePerHour}")
    private double pricePerHour;
    @Value("${fine}")
    private int fine;

    public int calculate(TicketEntity ticket) {
        long timeDiff = ticket.getEntryTime().until(LocalDateTime.now(), ChronoUnit.MINUTES);
        int time;
        if(ticket.getPaidCount() == 0) {
            time = (int) Math.max(0, timeDiff - freeTime);
        }else{
            time = (int) Math.max(0, timeDiff);
        }
        return (int) Math.ceil(time *  pricePerHour / 60);
    }
}
