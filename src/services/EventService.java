package services;

import entities.Event;
import exceptions.InvalidEventException;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class EventService {
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void listFutureEvents(Collection<Event> events) {

        events.stream()
                .filter(event -> event.getData().isAfter(LocalDate.now()) || event.getData().isEqual(LocalDate.now()))
                .forEach(event -> System.out.println(
                        event.getId() + " - " + event.getName() + " - " + event.getData().format(fmt)));
    }

    public void listEventsperDate(List<Event> events) {

        Comparator<Event> comp = (e1, e2) -> e1.getData().compareTo(e2.getData());

        events.sort(comp);
        events.forEach(event -> System.out.println(event.getId()
                + " - " + event.getName() + " - " + event.getData().format(fmt)));

    }

    public void findEventsPerName(List<Event> events, String word) {

        events.stream()
                .filter(event -> event.getName().toLowerCase().contains(word.toLowerCase()))
                .forEach(event -> System.out.println(event.getId() + " - " + event.getName() + " - " + event.getData().format(fmt)));


    }

    public Event registerEvent(Integer id, String name, LocalDate date, Integer maxCapacity, Double ticketBase) {

        if (name == null || name.isBlank()) {
            throw new InvalidEventException("Error: Event name cannot be empty.");
        }

        if (date.isBefore(LocalDate.now())) {
            throw new InvalidEventException("Error: Event date cannot be before the current date.");
        }

        if (maxCapacity == null || maxCapacity <= 0) {
            throw new InvalidEventException("Error: Maximum capacity must be greater than zero.");
        }

        if (ticketBase == null || ticketBase <= 0) {
            throw new InvalidEventException("Error: Base price must be greater than zero.");
        }

        return new Event(id, name, date, maxCapacity, ticketBase);
    }

    public void listEvents(Collection<Event> events) {

        if (events.isEmpty()) {
            System.out.println("No events registered yet.");
            return;
        }
        System.out.println("Registered events:");
        System.out.println();

        for (Event event : events) {
            System.out.println("ID: " + event.getId());
            System.out.println("Name: " + event.getName());
            System.out.println("Date: " + event.getData().format(fmt));
            System.out.println("Maximum capacity: " + event.getMaximumCapacity());
            System.out.println("Tickets sold: " + event.getTicketsSold().size());
            System.out.printf("Base price: %.2f%n", event.getTicketBase());
            System.out.println();
        }
    }

    public void findEventsPerId(Event event) {
        if (event == null) {
            System.out.println("Error: Event not found.");
            return;
        }

        System.out.printf("ID: %d%nName: %s%nDate: %s%nMaximum capacity: %d%nTickets sold: %d%nBase price: %.2f%n",
                event.getId(),
                event.getName(),
                event.getData().format(fmt),
                event.getMaximumCapacity(),
                event.getTicketsSold().size(),
                event.getTicketBase());
    }
}



