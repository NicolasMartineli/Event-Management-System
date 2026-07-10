#  Event Management System

A console-based Java application for managing events and ticket sales, built with **pure Java** — no frameworks, no database, no GUI. This is a personal project built to practice core Object-Oriented Programming concepts and modern Java features.

---

##  Overview

The system allows an events company to register events, sell different types of tickets, track ticket sales, generate financial reports, and manage cancellations — all through an interactive console menu.

Each event has a maximum capacity, a base ticket price, and enforces business rules such as preventing overbooking and duplicate purchases from the same buyer.

---

##  Features

- **Event management**: register, list, and search events by ID or name
- **Ticket sales**: sell tickets with automatic price calculation based on ticket type
- **Three ticket types**, each with its own pricing rule:
  | Type | Price |
  |------|-------|
  | Common | 100% of base price |
  | Student | 50% of base price |
  | VIP | 150% of base price |
- **Ticket cancellation** by buyer email
- **Financial reports**: total revenue, revenue by ticket type, and quantity sold by ticket type
- **Smart filtering & sorting**: list upcoming events, sort events by date, and search events by (partial, case-insensitive) name
- **Custom exception handling**: every business rule violation raises a specific, descriptive exception — no stack traces are ever shown to the user
- **Input validation with retry**: invalid input (wrong number/date format, invalid ticket type) prompts the user to try again instead of aborting the whole operation

---

##  Concepts Practiced

This project was built specifically to practice the following Java and OOP concepts:

- Classes, objects, encapsulation, and constructors
- Composition between classes
- Inheritance and abstract classes
- Polymorphism (ticket price calculation, no `if` chains needed)
- Custom exceptions
- Collections: `List`, `Set`, `Map`
- Generics (a reusable, generic `Repository<T>`)
- Lambda expressions and the Stream API
- Package-based project organization

---

##  Project Structure

```
src/
├── application/
│   └── Program.java              # Entry point, console menu, and I/O logic
├── entities/
│   ├── Event.java                # Event entity
│   ├── Ticket.java                # Abstract base class for tickets
│   ├── CommonTicket.java          # Concrete ticket type
│   ├── StudentTicket.java         # Concrete ticket type
│   └── VipTicket.java             # Concrete ticket type
├── entities/enums/
│   └── TicketType.java            # COMMON, STUDENT, VIP
├── repositories/
│   ├── Repository.java            # Generic repository (save, findAll, findById)
│   └── EventRepository.java       # Repository specialized for Event
├── services/
│   ├── EventService.java          # Business logic for events
│   └── TicketService.java         # Business logic for tickets
└── exceptions/
    ├── InvalidEventException.java
    ├── InvalidTicketException.java
    ├── EventFullException.java
    ├── EventNotFoundException.java
    ├── DuplicateBuyerException.java
    └── TicketNotFoundException.java
```

---

##  Architecture

The project follows a simple layered architecture:

- **`entities`** — domain models (`Event`, `Ticket` and its subclasses)
- **`repositories`** — in-memory data storage, built on a generic `Repository<T>` backed by a `Map<Integer, T>`
- **`services`** — business rules and validation (`EventService`, `TicketService`)
- **`application`** — the console UI layer (`Program`), responsible only for reading input, calling services, and displaying output

This separation keeps validation and business logic out of the UI layer, and keeps the storage layer unaware of any business rules.

### Polymorphism in action

`Ticket` is an abstract class with an abstract method `priceTicket()`. Each subclass (`CommonTicket`, `StudentTicket`, `VipTicket`) implements its own pricing logic. This means the rest of the system never needs to check the ticket type to know how much it costs — it simply calls `ticket.priceTicket()` and lets polymorphism do the work.

### Generics

`Repository<T>` is a generic class providing `save`, `findAll`, and `findById` operations. `EventRepository` extends `Repository<Event>`, specializing it without duplicating any code.

---

##  Getting Started

### Prerequisites

- Java 17 or higher (uses `LocalDate`, `switch` on `int`, and modern Stream API features)

### Running the project

1. Clone the repository:
   ```bash
   git clone git@github.com:NicolasMartineli/Event-Management-System.git
   cd Event-Management-System
   ```
2. Compile and run `Program.java` from your IDE (IntelliJ IDEA recommended), or via command line:
   ```bash
   javac -d out $(find src -name "*.java")
   java -cp out application.Program
   ```

---

##  Usage Example

```
==== EVENT SYSTEM ====

1 - Register event
2 - List Event
3 - Search for events per ID
4 - Sell tickets
5 - List event tickets
6 - Show financial summary of an event
7 - Cancel ticket
8 - List upcoming events
9 - List events ordered per date
10 - Search for events per name
0 - Exit

Choose an option: 1
Event name: Java Music Festival
Event date (yyyy-MM-dd): 2026-08-20
Maximum capacity: 3
Base price: 200.00

Event registered successfully!
Event ID: 1
```

```
Choose an option: 4
Event ID: 1
Buyer name: Matheus
Buyer email: matheus@email.com
Ticket type (VIP/STUDENT/COMMON): VIP

Ticket sold successfully!
Final price: R$ 300.00
```

```
Choose an option: 6
Enter the event ID: 1

Financial summary - Java Music Festival
Date: 20/08/2026
Capacity: 3
Tickets sold: 1
Tickets available: 2
Total revenue: R$ 300.00

Quantity per type:
VIP: 1

Revenue per type:
VIP: R$ 300.00
```

---

##  Error Handling

All business rule violations are handled through custom exceptions, and every operation that reads user input is wrapped in validation logic. The user is always shown a clear, human-readable message (e.g. `Error: This email already has a ticket for this event.`) — no raw exceptions or stack traces are ever displayed.

| Exception | Thrown when |
|---|---|
| `InvalidEventException` | Event data fails validation (empty name, past date, invalid capacity/price) |
| `InvalidTicketException` | Ticket data is invalid (empty buyer name/email, invalid type) |
| `EventFullException` | The event has reached its maximum capacity |
| `EventNotFoundException` | No event exists with the given ID |
| `DuplicateBuyerException` | The same email already purchased a ticket for that event |
| `TicketNotFoundException` | No ticket matches the given email during cancellation |

---

##  Possible Improvements

- Persist data to a file or database instead of in-memory storage
- Add a general system report (total events, total revenue, top-selling event)
- Sort tickets alphabetically by buyer name
- Add extra fields for `StudentTicket` (institution) and `VipTicket` (perk)

---

##  Author

**Nícolas Martineli**
Information Systems student at FHO (Fundação Hermínio Ometto)

---

##  License

This is a personal project, built independently to practice Java and object-oriented programming.
