package services;

import entities.Event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class EventService {
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void listFutureEvents(List<Event> events) {

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

    public void findEventsByName(List<Event> events, String word) {

        events.stream()
                .filter(event -> event.getName().toLowerCase().contains(word.toLowerCase()))
                .forEach(event -> System.out.println(event.getId() + " - " + event.getName() + " - " + event.getData().format(fmt)));


    }
}


