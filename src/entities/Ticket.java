package entities;

import entities.enums.TicketType;

public abstract class Ticket {

    protected String buyerName;
    protected String buyerEmail;
    protected Event event;
    protected TicketType ticketType;

    public Ticket(String buyerName, String buyerEmail, Event event, TicketType ticketType) {
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.event = event;
        this.ticketType = ticketType;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public Event getEvent() {
        return event;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public abstract Double priceTicket();

}

