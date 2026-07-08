package application;

import entities.Event;
import exceptions.InvalidEventException;
import repositories.EventRepository;
import services.EventService;
import services.TicketService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int option = -1;
        int nextId = 1;

        EventRepository eventRepository = new EventRepository();
        TicketService ticketService = new TicketService();
        EventService eventService = new EventService();

        while (option != 0) {

            System.out.println("==== EVENT SYSTEM ====");
            System.out.println();
            System.out.println("1 - Register event");
            System.out.println("2 - List Event");
            System.out.println("3 - Search for events per ID");
            System.out.println("4 - Sell tickets");
            System.out.println("5 - List event tickets");
            System.out.println("6 - Show financial summary of an event");
            System.out.println("7 - Cancel ticket");
            System.out.println("8 - List  upcoming events");
            System.out.println("9 - List events ordered per date");
            System.out.println("10 - Search for events per name");
            System.out.println("0 - Exit");
            System.out.println();
            System.out.print("Choose an option: ");
            option = Integer.parseInt(sc.nextLine());
            System.out.println();


            switch (option) {
                case 1:
                    try {
                        System.out.print("Event name: ");
                        String name = sc.nextLine();
                        System.out.println();

                        System.out.print("Event date (yyyy-MM-dd): ");
                        LocalDate date = LocalDate.parse(sc.nextLine());
                        System.out.println();

                        System.out.print("Maximum capacity: ");
                        int max = Integer.parseInt(sc.nextLine());
                        System.out.println();

                        System.out.print("Base price: ");
                        double ticketBase = Double.parseDouble(sc.nextLine());
                        System.out.println();


                        Event event = eventService.registerEvent(nextId, name, date, max, ticketBase);
                        eventRepository.save(event.getId(), event);

                        System.out.println("Event registered successfully!");
                        System.out.println("Event ID: " + nextId);

                        nextId++;
                        break;
                    } catch (InvalidEventException e) {
                        System.out.println("Error " + e.getMessage());
                    } catch (DateTimeParseException e) {
                        System.out.println("Error: Invalid date format. Please use yyyy-MM-dd.");
                    }

                case 2:
                    Collection<Event> events = eventRepository.findAll();
                    eventService.listEvents(events);
                    break;
            }


        }

    }
}
