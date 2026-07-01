package entities;

import entities.enums.TicketType;

public class StudentTicket extends Ticket{
    public StudentTicket(String buyerName, String buyerEmail, Event event, TicketType ticketType) {
        super(buyerName, buyerEmail, event, ticketType);
    }
    @Override
    public Double priceTicket(){
        return getEvent().getTicketBase() / 2;
    }
}
