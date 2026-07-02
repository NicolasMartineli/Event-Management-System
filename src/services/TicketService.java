package services;


import entities.*;
import entities.enums.TicketType;
import exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static entities.enums.TicketType.*;

public class TicketService {


    public Ticket sellTicket(Integer id, String buyerName, String buyerEmail, Event event, TicketType ticketType) {

        Ticket ticket = null;

        if (event == null) {
            throw new EventNotFoundException("Error: Event does not exist");
        }
        if (ticketType == null) {
            throw new InvalidTicketException("Error: Invalid ticket ");
        }
        if (event.getTicketsSold().size() >= event.getMaximumCapacity()) {
            throw new EventFullException("Error: The event is full");
        }
        if (buyerName == null || buyerName.isBlank()) {
            throw new InvalidTicketException("Error: Buyer name cannot be empty");
        }
        if (buyerEmail == null || buyerEmail.isBlank()) {
            throw new InvalidTicketException("Error: Buyer email cannot be empty");
        }
        if (event.getData().isBefore(LocalDate.now())) {
            throw new InvalidEventException("Error: The event has already happened");
        }
        if (event.getBuyerEmails().contains(buyerEmail)) {
            throw new DuplicateBuyerException("Error: This email already has a ticket ");

        }
        if (ticketType == COMMOM) {
            ticket = new CommonTicket(buyerName, buyerEmail, event, ticketType);

        } else if (ticketType == VIP) {
            ticket = new VipTicket(buyerName, buyerEmail, event, ticketType);

        } else if (ticketType == STUDENT) {
            ticket = new StudentTicket(buyerName, buyerEmail, event, ticketType);

        }
        if (ticket != null) {
            event.addTicket(ticket);
            event.addBuyerEmail(buyerEmail);

        }
        return ticket;
    }

    public void listTicketByEvent(Event event) {
        if (event == null) {
            throw new EventNotFoundException("Error: Event does not exist");
        }
        List<Ticket> eventTicket = event.getTicketsSold();

        if (eventTicket.isEmpty()) {
            System.out.println("No tickets sold for this event yet. ");
            return;
        }
        System.out.print("Tickets sales for " + event.getName() + ":");
        System.out.println();

        eventTicket.forEach(ticket ->
                System.out.printf("%s - %s - %s - %.2f%n",
                        ticket.getBuyerName(),
                        ticket.getBuyerEmail(),
                        ticket.getTicketType(),
                        ticket.priceTicket())
        );
    }
}



