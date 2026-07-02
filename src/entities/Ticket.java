package entities;

import entities.enums.TicketType;

import java.util.Objects;

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
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(buyerEmail, ticket.buyerEmail) && Objects.equals(event, ticket.event);
    }
    @Override
    public int hashCode() {
        return Objects.hash(buyerEmail, event);
    }

    public abstract Double priceTicket();

}

