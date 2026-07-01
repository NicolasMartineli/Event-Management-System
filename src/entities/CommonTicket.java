package entities;

import entities.enums.TicketType;

public class CommonTicket extends Ticket {
    public CommonTicket(String buyerName, String buyerEmail, Event event, TicketType ticketType) {
        super(buyerName, buyerEmail, event, ticketType);
    }

    @Override
    public Double priceTicket() {
        return getEvent().getTicketBase();
    }


}
