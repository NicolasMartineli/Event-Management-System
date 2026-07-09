package services;


import entities.*;
import entities.enums.TicketType;
import exceptions.*;

import java.time.LocalDate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import static entities.enums.TicketType.*;

public class TicketService {


    public Ticket sellTicket(String buyerName, String buyerEmail, Event event, TicketType ticketType) {

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
            throw new DuplicateBuyerException("Error: This email already has a ticket");

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

    public void financialSummary(Event event) {
        if (event == null) {
            throw new EventNotFoundException("Error: Event does not exist");
        }

        System.out.printf("Financial summary - " + event.getName());
        System.out.println();
        System.out.println("Date: " + event.getData());
        System.out.println("Capacity: " + event.getMaximumCapacity());
        System.out.println("Tickets sold: " + event.getTicketsSold().size());
        System.out.println("Tickets available: " + (event.getMaximumCapacity() - event.getTicketsSold().size()));

        Map<TicketType, Integer> quantityByType = new LinkedHashMap<>();
        Map<TicketType, Double> revenueByType = new LinkedHashMap<>();

        event.getTicketsSold().forEach(ticket -> {

            TicketType type = ticket.getTicketType();
            int quantity = quantityByType.getOrDefault(type, 0);
            quantityByType.put(type, quantity + 1);

            Double sum = ticket.priceTicket();
            revenueByType.merge(type, sum, Double::sum);
        });
        double total = revenueByType.values().stream()
                .mapToDouble(valor -> valor)
                .sum();

        System.out.println("Total revenue: " + total);
        System.out.println();

        System.out.print("Quantity per type ");
        quantityByType.forEach((type, quantity) ->
                System.out.println(type + ": " + quantity));

        System.out.println();
        System.out.print("Revenue per type");
        revenueByType.forEach((type, totalValue) ->
                System.out.printf("%s: R$ %.2f%n", type, totalValue));

    }

    public void cancelTicket(Event event, String buyerEmail) {
        if (event == null) {
            throw new EventNotFoundException("Error: Event is not found.");
        }
        if (event.getData().isBefore(LocalDate.now())) {
            throw new InvalidEventException("Error: The event has already taken place.");
        }
        Optional<Ticket> resultado = event.getTicketsSold().stream()
                .filter(ticket -> ticket.getBuyerEmail().equals(buyerEmail))
                .findFirst();

        if (resultado.isEmpty()) {
            throw new TicketNotFoundException("Error: Ticket is not found");

        } else {
            Ticket ticket = resultado.get();
            event.removeTicket(ticket);
            event.removeBuyerEmail(buyerEmail);
            System.out.println("Ticket successfully cancelled.");
        }


    }

}



