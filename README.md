# Ticketing System

A simple microservice example using Spring Boot, Kafka and the Outbox Pattern to ensure reliable event publishing and message consumption.  
The service demonstrates receiving events via Kafka, persisting them to a database, and using clean architecture principles.

---

## 📂 GitHub Repository
[Public GitHub Repo](https://github.com/oyeliseiev-ua/ticketing-system-example.git)  

---

## 🛠 Tech Stack
- **Java 21**
- **Spring Boot 3.5.x**
- **Spring Kafka**
- **Lombok**
- **H2** in-memory database
- **Kafka** (Confluent)
- **Docker Compose**

---

## 🚀 Step-by-Step Setup & Run Instructions

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/oyeliseiev-ua/ticketing-system-example.git
cd ticketing-system-example
```

### Start Kafka & Kafka UI using Docker Compose

```bash
docker compose up -d
```
This will start:
* Kafka broker (port 9092)
* Zookeeper (port 2181)
* Kafka UI (port 8081)
* Network: ticketing-system-net

### Run the Spring Boot Application
```bash
mvn spring-boot:run
```

### Submit a Ticket via REST API
```bash
POST http://localhost:8080/api/v1/ticket/submit
Content-Type: application/json
```

```json
{
  "userId": "abc123",
  "subject": "IT",
  "description": "Provide macbook laptop"
}
```

### Check Kafka Messages in Kafka UI
* Open http://localhost:8081
* Navigate to support-tickets topic
* Confirm the ticket message is received.

### Produce a Test Message to Assign a Ticket
Send a message to the Kafka topic ticket-assignments via Kafka UI or CLI:
```json
{
  "ticketId": 1,
  "assigneeId": "agent-007"
}
```
Check logs: Ticket 1 is successfully assigned to agent-007

### Produce a Test Message to Update Ticket Status
Send a message to the Kafka topic ticket-updates via Kafka UI or CLI:
```json
{
  "ticketId": 1,
  "status": "in_progress"
}
```
Check logs: Ticket 1 status is successfully updated

## Message Formats

### Ticket Submission Event

```json
{
  "id": "number",
  "userId": "string",
  "subject": "string",
  "description": "string"
}
```

### Ticket Assignment Event

```json
{
  "ticketId": "number",
  "assigneeId": "string"
}
```

### Ticket Update Event

```json
{
  "ticketId": "number",
  "status": "string"
}
```

## Tests Included
skipped(lack of time)

## Design Decisions

* Outbox Pattern — ensures ticket submission events are first saved in the database before publishing to Kafka for reliability.
* Since Kafka guarantees at-least-once delivery for consumers, the ID of the submitted ticket is used as Ticket id to check before persist to handle idempotency.
* Separate Service Layer — the Kafka listener delegates database writes to a TicketService.
* Transactional Safety — database operations are wrapped in @Transactional to ensure atomicity.
* Config via Properties — Kafka topics and group IDs are injected from application.properties for flexibility.
* Retries & Error Handling — any failures throw exceptions, allowing Spring Kafka to retry message consumption.
* Lombok — reduces boilerplate code in entities, DTOs, and services.
* H2 Database — lightweight in-memory database for development and testing.

## AI Tool Usage & Validation

AI Tools Used: ChatGPT (GPT-5) to scaffold:
* Docker Compose configuration
* README.md initial draft
* Some clarifications related configuration, code and design

Validation Steps:
* All AI-generated code was reviewed manually
* Tested locally with Docker Compose
