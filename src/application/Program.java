package application;

import entities.Event;
import entities.Ticket;
import entities.enums.TicketType;
import exceptions.*;
import repositories.EventRepository;
import services.EventService;
import services.TicketService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


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
            String input = sc.nextLine();

            while (input.isBlank()) {
                input = sc.nextLine();
            }

            try {
                option = Integer.parseInt(input);
                System.out.println();
            } catch (NumberFormatException o) {
                System.out.println("Error: Please enter a valid number.");
                continue;
            }

            switch (option) {

                case 1:
                    try {
                        System.out.print("Event name: ");
                        String name = sc.nextLine();
                        System.out.println();

                        LocalDate date = null;
                        boolean validDate = false;
                        while (!validDate) {
                            try {
                                System.out.print("Event date (yyyy-MM-dd): ");
                                date = LocalDate.parse(sc.nextLine());
                                validDate = true;
                            } catch (DateTimeParseException e) {
                                System.out.println("Error: Invalid date format. Please use yyyy-MM-dd.");
                            }
                        }
                        System.out.println();

                        Integer max = null;
                        boolean validMax = false;
                        while (!validMax) {
                            try {
                                System.out.print("Maximum capacity: ");
                                max = Integer.parseInt(sc.nextLine());
                                validMax = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Invalid number format. Try again.");
                            }
                        }
                        System.out.println();

                        Double ticketBase = null;
                        boolean validPrice = false;
                        while (!validPrice) {
                            try {
                                System.out.print("Base price: ");
                                ticketBase = Double.parseDouble(sc.nextLine());
                                validPrice = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Invalid number format. Try again.");
                            }
                        }
                        System.out.println();

                        Event event = eventService.registerEvent(nextId, name, date, max, ticketBase);
                        eventRepository.save(event.getId(), event);

                        System.out.println("Event registered successfully!");
                        System.out.println("Event ID: " + nextId);

                        nextId++;

                    } catch (InvalidEventException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:

                    Collection<Event> events = eventRepository.findAll();
                    eventService.listEvents(events);
                    break;

                case 3:

                    try {
                        System.out.print("Enter the event ID: ");
                        Integer id = Integer.parseInt(sc.nextLine());
                        System.out.println();

                        Event event = eventRepository.findById(id);

                        eventService.findEventsPerId(event);

                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid ID format.");
                    }
                    break;

                case 4:
                    try {
                        Integer id = null;
                        Event event = null;
                        boolean validId = false;
                        while (!validId) {
                            try {
                                System.out.print("Event ID: ");
                                id = Integer.parseInt(sc.nextLine());
                                event = eventRepository.findById(id);
                                if (event == null) {
                                    System.out.println("Error: Event does not exist. Try again.");
                                } else {
                                    validId = true;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Invalid ID format. Try again.");
                            }
                        }
                        System.out.println();

                        System.out.print("Buyer name: ");
                        String name = sc.nextLine();
                        System.out.println();

                        System.out.print("Buyer email: ");
                        String buyerEmail = sc.nextLine();
                        System.out.println();

                        TicketType ticketType = null;
                        boolean validType = false;
                        while (!validType) {
                            try {
                                System.out.print("Ticket type (VIP/STUDENT/COMMON): ");
                                ticketType = TicketType.valueOf(sc.nextLine().toUpperCase());
                                validType = true;
                            } catch (IllegalArgumentException e) {
                                System.out.println("Error: Invalid ticket type. Try again.");
                            }
                        }
                        System.out.println();

                        Ticket ticket = ticketService.sellTicket(name, buyerEmail, event, ticketType);

                        System.out.println("Ticket sold successfully!");
                        System.out.printf("Final price: R$ %.2f%n", ticket.priceTicket());

                    } catch (InvalidTicketException | EventFullException | DuplicateBuyerException |
                             InvalidEventException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        System.out.print("Enter the event ID: ");
                        Integer id = Integer.parseInt(sc.nextLine());
                        System.out.println();

                        Event event = eventRepository.findById(id);

                        ticketService.listTicketByEvent(event);
                    } catch (EventNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid ID format.");
                    }
                    break;

                case 6:
                    try {
                        System.out.print("Enter the event ID: ");
                        Integer id = Integer.parseInt(sc.nextLine());
                        System.out.println();

                        Event event = eventRepository.findById(id);

                        ticketService.financialSummary(event);
                    } catch (EventNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid ID format.");
                    }
                    break;

                case 7:
                    try {
                        System.out.print("Enter the event ID: ");
                        Integer id = Integer.parseInt(sc.nextLine());
                        System.out.println();

                        System.out.print("Buyer email: ");
                        String buyerEmail = sc.nextLine();
                        System.out.println();

                        Event event = eventRepository.findById(id);
                        ticketService.cancelTicket(event, buyerEmail);
                    } catch (EventNotFoundException | InvalidEventException | TicketNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Invalid ID format.");
                    }
                    break;

                case 8:
                    System.out.println("Future events:");
                    Collection<Event> event = eventRepository.findAll();
                    eventService.listFutureEvents(event);
                    break;

                case 9: {
                    System.out.print("Events ordered by date: ");
                    System.out.println();

                    List<Event> eventList = new ArrayList<>(eventRepository.findAll());

                    eventService.listEventsperDate(eventList);
                }
                break;

                case 10:
                    System.out.print("Type a word: ");
                    String word = sc.nextLine();
                    System.out.println();

                    List<Event> eventList = new ArrayList<>(eventRepository.findAll());

                    eventService.findEventsPerName(eventList, word);

                    break;
            }
            if (option != 0) {
                System.out.println();
                System.out.print("Press Enter to continue...");
                sc.nextLine();
            }

        }

    }

}

