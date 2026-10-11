# Travel Booking Backend

A RESTful backend application built with Java and Spring Boot to manage hotel listings, room bookings, booking cancellations, and simulated payments. It uses MySQL for persistent storage and exposes REST APIs that can be tested with Postman.

## Features

- Add, retrieve, and search hotels by city
- Create and retrieve bookings
- Calculate booking amounts based on the number of nights
- Check room availability when creating bookings
- Cancel bookings and restore room availability
- Simulate payments using UPI, Card, and Net Banking
- Prevent duplicate payment attempts for a booking
- Prevent payments for cancelled bookings
- Validate requests and payment methods
- Handle exceptions with meaningful HTTP status codes
- Use DTOs for payment API responses

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot 4.1.1
- **Persistence:** Spring Data JPA and Hibernate
- **Database:** MySQL 8
- **Build Tool:** Maven
- **API Testing:** Postman
- **Server:** Embedded Apache Tomcat

## Project Structure

```text
travel-booking-backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/anubhav/TravelBooking/
│       │       ├── controller/    # Handle HTTP requests
│       │       ├── service/       # Business logic
│       │       ├── repository/    # Database access (Spring Data JPA)
│       │       ├── entity/        # Database tables
│       │       ├── dto/           # Request/response objects
│       │       └── exception/     # Custom exceptions and global handler
│       └── resources/
│           └── application.properties
├── screenshots/
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Prerequisites

- JDK 17
- MySQL Server 8
- Git
- Maven, or the Maven Wrapper included in the project
- Postman for API testing

## Database Setup

Create the database in MySQL:

```sql
CREATE DATABASE travel_booking;
```

Configure `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/travel_booking
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080
```

The database credentials are read from environment variables so that no real password is stored in the code:

- `DB_USERNAME` — your MySQL username
- `DB_PASSWORD` — your MySQL password

Never commit real credentials to GitHub.

If the application fails to start with a database connection error, verify that MySQL is running and both environment variables are set correctly.

## Run the Application

Clone the repository:

```bash
git clone https://github.com/anubhavjain702-boop/travel-booking-backend.git
cd travel-booking-backend
```

Replace `<repository-name>` with the name of this repository.

Set the environment variables and run the application using the Maven Wrapper.

**Windows (PowerShell):**

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your-local-password"
.\mvnw.cmd spring-boot:run
```

**macOS / Linux:**

```bash
export DB_USERNAME=root
export DB_PASSWORD=your-local-password
./mvnw spring-boot:run
```

Alternatively, if Maven is installed:

```bash
mvn spring-boot:run
```

Replace `your-local-password` with your local MySQL password.

The application runs at `http://localhost:8080`.

## API Endpoints

### Hotel APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/hotels` | Add a hotel |
| `GET` | `/api/hotels` | Retrieve all hotels |
| `GET` | `/api/hotels/search?city=Mumbai` | Search hotels by city |

### Booking APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/bookings` | Create a booking |
| `GET` | `/api/bookings` | Retrieve all bookings |
| `GET` | `/api/bookings/{id}` | Retrieve a booking by ID |
| `DELETE` | `/api/bookings/{id}` | Cancel a booking |

Cancelling a booking changes its status to `CANCELLED` and restores the associated room availability. The booking record is retained rather than physically deleted.

### Payment APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/payments` | Create a payment |
| `GET` | `/api/payments` | Retrieve all payments |
| `GET` | `/api/payments/{id}` | Retrieve a payment by ID |

## Request Examples

Use IDs and data that exist in your local database when testing.

### 1. Add a Hotel

`POST /api/hotels`

```json
{
  "name": "Taj Hotel",
  "city": "Mumbai",
  "pricePerNight": 5000.0,
  "availableRooms": 10
}
```

### 2. Create a Booking

`POST /api/bookings`

```json
{
  "customerName": "Rahul Sharma",
  "checkIn": "2027-01-10",
  "checkOut": "2027-01-15",
  "hotel": {
    "id": 1
  }
}
```

This example assumes hotel ID `1` exists and has available rooms. Use valid future dates when testing.

**Booking amount calculation:**

`Total amount = Number of nights × Price per night`

For example, five nights at ₹5,000 per night result in a total of ₹25,000.

### 3. Make a Payment

`POST /api/payments`

```json
{
  "bookingId": 1,
  "paymentMethod": "UPI"
}
```

Use an existing, unpaid, non-cancelled booking ID. The payment amount is taken from the associated booking rather than trusted from the client request.

Supported payment methods:

- `UPI`
- `CARD`
- `NET_BANKING`

**Payment limitation:** Payments are simulated and stored in the database. No real payment gateway or actual money transfer is integrated. After a successful payment, the associated booking status changes to `CONFIRMED`.

## Response Examples

These examples are based on the response format observed during local testing. IDs and values may differ in another database.

### Successful Payment

```json
{
  "id": 1,
  "bookingId": 1,
  "amount": 25000.0,
  "status": "success",
  "paymentMethod": "UPI"
}
```

### Duplicate Payment — `409 Conflict`

```json
{
  "status": 409,
  "message": "Payment Already exist for this booking"
}
```

### Booking Not Found — `404 Not Found`

```json
{
  "message": "Booking not found",
  "status": 404,
  "timestamp": "2026-10-11T01:41:58.042841"
}
```

### Invalid Payment Method — `400 Bad Request`

The application returns a validation error when an unsupported payment method such as `CASH` is provided. The exact response body depends on the global exception handler.

## Error Handling

| Situation | HTTP Status |
|---|---:|
| Invalid request or validation error | `400 Bad Request` |
| Payment for a cancelled booking | `400 Bad Request` |
| Booking, hotel, or payment not found | `404 Not Found` |
| Duplicate payment attempt | `409 Conflict` |

The application uses custom exceptions and a global exception handler to return structured error responses.

## Screenshots

### 1. Create Booking

[Create Booking](./screenshorts/create-booking.png)

### 2. Successful Payment

[Successful Payment](./screenshorts/successful-payment.png)

### 3. Error Response

[Error Response](./screenshorts/error-response.png)

## Testing

The APIs have been tested with Postman for scenarios including:

- Retrieving hotels, bookings, and payments
- Searching hotels by city
- Creating and cancelling bookings
- Booking amount calculation
- Successful simulated payment
- Invalid payment method
- Nonexistent booking
- Duplicate payment prevention
- Booking confirmation after payment

## Known Limitations

- Payments are simulated; no real payment gateway is connected.
- Authentication and authorization are not implemented.
- Automated test coverage and API documentation can be expanded.
- Production use would require additional safeguards for concurrent bookings and payment processing.

## Future Improvements

- Add authentication and authorization using Spring Security and JWT
- Integrate a real payment gateway
- Add unit and integration tests
- Add Swagger/OpenAPI documentation
- Improve transaction handling and concurrency control
- Add pagination and filtering

## Author

**Anubhav Jain**  
B.Tech — Computer Science and Engineering
LNCT Group of Colleges, Bhopal

- **GitHub:** [anubhavjain702-boop](https://github.com/anubhavjain702-boop)
- **LinkedIn:** [Anubhav Jain](https://www.linkedin.com/in/anubhav-jain-43b087321/)
