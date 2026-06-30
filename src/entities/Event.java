package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Event {
    private Integer id;
    private String name;
    private LocalDate data;
    private Integer maximumCapacity;
    private Double ticketBase;

    private List<Ticket> ticketsSold = new ArrayList<>();

    private Set<String> buyerEmails = new HashSet<>();

    public Event(Integer id, String name, LocalDate data, Integer maximumCapacity, Double ticketBase) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.maximumCapacity = maximumCapacity;
        this.ticketBase = ticketBase;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(Integer maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public Double getTicketBase() {
        return ticketBase;
    }

    public void setTicketBase(Double ticketBase) {
        this.ticketBase = ticketBase;
    }

    public List<Ticket> getTicketsSold() {
        return ticketsSold;
    }

    public Set<String> getBuyerEmails() {
        return buyerEmails;
    }

    public void addTicket(Ticket ticket) {
        ticketsSold.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        ticketsSold.remove(ticket);
    }

    public void addBuyerEmail(String emails) {
        buyerEmails.add(emails);
    }
    public void removeBuyerEmail (String emails){
        buyerEmails.remove(emails);
    }
}
