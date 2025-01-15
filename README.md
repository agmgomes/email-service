# Email Service Application

This project is an email service designed to send and manage emails. It is built using
**Spring Boot**, with integration to **AWS SES** for sending emails, and uses **PostgreSQL**
as the database. The application follows a **Hexagonal Architecture** and supports queueing with
**RabbitMQ** for better scalability and reliability.

## Features

- Send emails through AWS SES.
- Store email information in a PostgreSQL database.
- Manage email statuses (e.g., PENDINT, SENT, FAILED).
- Support for scheduled emails using a cron job for periodic processing.
- Integration with RabbitMQ for asynchronous processing.

## Architecture

The purpose of this project was to learn more about the **Hexagonal Architecture**
(also know as Ports and Adapters), where the core business logic is isolated from the external
dependencies such as database, email providers and message queues. The project is divided into
three main layers, following **Hexagonal Architecture**:
    
1. **Application**:
    - Contains the **use cases** and **services** that define the business logic
    and orchestrate interactions between the domain and external layers.
    - This layer acts as a bridge between the **domain** and **infrastructure** layers.
    - Example: `SendEmailUseCase`, `EmailService`.
2. **Domain**:
    - Contains the **core business logic** and **entities**.
    - Independent of any frameworks or external dependencies.
    - Example: `EmailMessage`, `EmailStatus`.
3. **Infrastructure**
    - Contains the **adapters** for interacting with external systems such as database,
    message queues, and third-party APIs.
    - This layer also includes configurations and implementations for repositories and
    external services.
    - Example: `EmailRestAdapter`, `AwsSesAdapter`, `RabbitMqEmailSchedulerAdapter`.

## Technologies Used

- **Spring Boot**: Framework for building the backend service.
- **PostgreSQL**: Relational database to store emails and email status information.
- **RabbitMQ**: Message broker for asynchronous email processing.
- **AWS SES**: Email service provider for sending emails.
- **Docker**: For containerizing the application and its dependencies.

## Setup

### Requirements

- **Docker** installed on your machine.

### Configuration

1. Clone the repository:

```bash
git clone https://github.com/agmgomes/email-service.git
cd email-service
```

2. Create a `.env` file in `src/main/resources/` to configure your AWS credentials:

```bash
AWS_VERIFIED_SENDER=
AWS_ACCESS_KEY_ID=
AWS_SECRET_ACCESS_KEY=
AWS_REGION=
```

3. Build and run the application with Docker Compose:

```bash
docker compose up --build
```
This command will:

- Start the Spring Boot application.
- Spin up a PostgreSQL container.
- Spin up a RabbitMQ container.

4. Access the application:

- The service will be available at `http://localhost:8080`.

5. Access the database:

- The PostgreSQL database will be available at `localhost:5432`.
- Database credentials:
    - Username: `postgres`
    - Password: `password`
- Use any PostgreSQL client (e.g., Beekeper Studio, pgAdmin, or psql) to connect
and query the database.

6. RabbitMQ Management Console:

- Available at `http://localhost:15672`
- RabbitMQ credentials:
    - Username: `admin`
    - Password: `password`



## Endpoints

The following endpoints are available:

- **POST /api/emails/send**: Sends an email immediately.
    - Example request for `/api/emails/send`:

```json
{
  "recipient": "test@example.com",
  "subject": "Send Email Test",
  "body": "Some text"
}
```
- **POST /api/emails/schedule**: Schedules an email to be sent later.
    - Example request for `/api/emails/send`:

```json
{
  "recipient": "test@example.com",
  "subject": "Send Email Test",
  "body": "Some text"
}
```
- **GET /api/emails/id/{id}**: Retrieves the status of a specific email.
    - Example response for `/api/emails/id/{id}`:

```json
{
  "id": 2,
  "status": "success",
  "createdAt": "2025-01-14T18:13:20.841991",
  "updatedAt": "2025-01-14T18:13:59.141008",
  "sendAt": "2025-01-14T18:13:20.841991"
}
```

# Contribution

Feel free to fork the repository, create issues, and submit pull requests.
Contributions are welcome!