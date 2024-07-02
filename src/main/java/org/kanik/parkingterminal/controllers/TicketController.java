package org.kanik.parkingterminal.controllers;

import lombok.RequiredArgsConstructor;
import org.kanik.parkingterminal.dtos.PayDTO;
import org.kanik.parkingterminal.dtos.TicketDTO;
import org.kanik.parkingterminal.services.TicketService;
import org.kanik.parkingterminal.util.CostCalculator;
import org.kanik.parkingterminal.util.TicketMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;
    private final CostCalculator costCalculator;

    @PostMapping("/enter")
    @ResponseStatus(HttpStatus.OK)
    public TicketDTO enter(){
        return TicketMapper.convertToDTO(ticketService.createNewTicketAndGet());
    }
    @PostMapping("/fine-ticket")
    @ResponseStatus(HttpStatus.OK)
    public TicketDTO fineTicket(){
        return TicketMapper.convertToDTO(ticketService.createFineTicketAndGet());
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TicketDTO> getAll(){
        return ticketService.findAll().stream().map(TicketMapper::convertToDTO).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TicketDTO getTicket(@PathVariable Integer id) {
        return TicketMapper.convertToDTO(ticketService.findTicketById(id));
    }
    @GetMapping("/cost/{id}")
    @ResponseStatus(HttpStatus.OK)
    public int cost(@PathVariable int id){
        return ticketService.calculateCost(id);
    }
    @GetMapping("/fine")
    @ResponseStatus(HttpStatus.OK)
    public int fine(){
        return costCalculator.getFine();
    }
    @GetMapping("/free-time")
    @ResponseStatus(HttpStatus.OK)
    public int freeTime(){
        return costCalculator.getFreeTime();
    }
    @GetMapping("/exit-time")
    @ResponseStatus(HttpStatus.OK)
    public int exitTime(){
        return costCalculator.getExitTime();
    }

    @PostMapping("/pay")
    @ResponseStatus(HttpStatus.OK)
    public boolean pay(@RequestBody PayDTO payDTO){
        return ticketService.payTicket(payDTO.getTicket());
    }
    @PostMapping("/exit/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean exit(@PathVariable int id){
        return ticketService.exit(id);
    }

    
}
