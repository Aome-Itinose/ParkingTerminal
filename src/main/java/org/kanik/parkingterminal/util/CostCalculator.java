package org.kanik.parkingterminal.util;

import lombok.Getter;
import org.kanik.parkingterminal.store.entities.TicketEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Date;

@Component
@Getter
public class CostCalculator {
    @Value("${exitTime}")
    private int exitTime;
    @Value("${freeTime}")
    private int freeTime;
    @Value("${pricePerHour}")
    private int pricePerHour;
    @Value("${fine}")
    private int fine;

    public int calculate(TicketEntity ticket) {
        long timeDiff = ticket.getEntryTime().until(LocalDateTime.now(), ChronoUnit.MINUTES);
        int time = (int) Math.max(0, timeDiff - freeTime);
        return Math.ceilDiv(time, 60) * pricePerHour;
    }
}
