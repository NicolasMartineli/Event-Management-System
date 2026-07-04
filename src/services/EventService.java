package services;

import entities.Event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventService {
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void listFutureEvents(List<Event> events) {
        events.stream()
                .filter(event -> event.getData().isAfter(LocalDate.now()) || event.getData().isEqual(LocalDate.now()))
                .forEach(event -> System.out.println(
                        event.getId() + " - " + event.getName() + " - " + event.getData().format(fmt)));
    }
}
