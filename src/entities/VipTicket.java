package entities;

import entities.enums.TicketType;

public class VipTicket extends Ticket{
    public VipTicket(String buyerName, String buyerEmail, Event event, TicketType ticketType) {
        super(buyerName, buyerEmail, event, ticketType);
    }
    @Override
    public Double priceTicket(){
        return getEvent().getTicketBase() + getEvent().getTicketBase() / 2;
    }
}
