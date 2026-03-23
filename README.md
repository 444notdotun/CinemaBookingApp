# 🎬 Cinema Booking App

A production-grade cinema seat booking and payment system built with **Java Spring Boot**, featuring a full payment lifecycle, webhook-driven confirmation, and reliability-first design patterns.

**Live Demo:** [cinema-booking-app-gamma.vercel.app](https://cinema-booking-app-gamma.vercel.app)

---

## What This Is

Most booking systems treat payment as an afterthought. This one treats it as a core infrastructure problem.

The app manages the full lifecycle of a cinema booking — from seat selection through payment confirmation — with built-in guarantees around consistency, idempotency, and failure recovery. It was built to handle the hard cases: duplicate webhook deliveries, payment timeouts, seats locked by users who never pay.

---

## Key Engineering Decisions

### Payment State Machine
Every booking moves through a strict state lifecycle:

```
PENDING → AWAITING_PAYMENT → CONFIRMED
                           → EXPIRED
                           → FAILED
```

State transitions are enforced at the service layer. A booking cannot skip states or move backward — this prevents race conditions and double-bookings.

### Webhook Security — HMAC SHA512 Verification
Paystack sends a signature header with every webhook event. Before processing any payment confirmation, the app recomputes the HMAC SHA512 signature using the raw request body and the secret key, then compares it to the header value.

Only valid, verified webhook events trigger state transitions. Tampered or replayed requests are rejected before any business logic runs.

### Idempotency Controls
Paystack can deliver the same webhook event multiple times. The app tracks processed payment references and ignores duplicate events — meaning the same payment can never be confirmed twice, regardless of how many times Paystack retries delivery.

### Scheduler-Based Seat Locking with Startup Reconciliation
When a user initiates booking, seats are locked immediately to prevent double-selection. A scheduled job runs at a fixed interval to expire bookings that never reached payment — releasing locked seats back to the pool.

On application startup, a reconciliation pass runs to handle any bookings that were in-flight when the server last shut down. This ensures no seats remain permanently locked due to a restart.

### Transactional Email Notifications
Booking confirmation and failure events trigger transactional emails via **Mailtrap**, keeping users informed at every state transition.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3 |
| Security | Spring Security, JWT |
| Payment | Paystack (webhooks + verify API) |
| Database | MongoDB |
| Email | Mailtrap (SMTP) |
| Containerization | Docker |
| Frontend | React (deployed on Vercel) |

---

## Architecture Overview

```
Client
  │
  ▼
REST API (Spring Boot)
  │
  ├── BookingController           → Initiates booking, locks seats
  ├── PaymentController           → Initializes Paystack transaction
  │     └── listenToPaymentResult → Receives & verifies Paystack webhook events
  │           └── HMAC SHA512 verification before any processing
  │
  ├── BookingService              → State machine enforcement
  ├── PaymentService              → Idempotency, reference tracking
  └── SeatLockScheduler           → Expires unpaid bookings, recovers on restart
```

---

## Reliability Patterns

| Pattern | Implementation |
|---|---|
| Idempotency | Payment reference deduplication before processing |
| Webhook verification | HMAC SHA512 signature check on all incoming events |
| Seat lock expiry | `@Scheduled` fixed-rate job releasing unpaid seats and recovering on restart |
| State enforcement | Enum-based state machine, transitions validated in service layer |

---

## Running Locally

```bash
git clone https://github.com/444notdotun/CinemaBookingApp
cd CinemaBookingApp

# Set the following in your application.properties or as environment variables:
# PAYSTACK_SECRET_KEY=your_paystack_secret
# MAILTRAP_USERNAME=your_mailtrap_username
# MAILTRAP_PASSWORD=your_mailtrap_password
# MONGODB_URI=your_mongodb_connection_string

# Run with Docker
docker compose up

# Or run directly
./mvnw spring-boot:run
```

---

## What I'd Do Differently at Scale

- **Replace MongoDB with PostgreSQL** for stronger transactional guarantees across booking + payment records
- **Extract webhook processing to a queue** (e.g., Redis Streams or Kafka) to decouple ingestion from processing and handle burst webhook delivery
- **Add distributed locking** (Redis `SETNX`) for seat locking instead of DB-level locking — safer under horizontal scaling
- **Expose booking state via SSE or WebSocket** so the frontend doesn't need to poll for confirmation

---

## Author

**Adedotun Adewole** — Backend Engineer  
[GitHub](https://github.com/444notdotun) · [LinkedIn](https://linkedin.com/in/adedotun-adewole-3ba6531a6)
