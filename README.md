# Chat WebSocket (Spring Boot)

Real-time chat application built with Spring Boot, STOMP over WebSocket, and Thymeleaf. Users can join with a name and exchange messages in a shared room with live updates.

## Features

- **Real-time messaging** with STOMP/WebSocket
- **SockJS fallback** for browsers with limited WebSocket support
- **In-memory message broker** broadcasting to all connected clients
- **Simple UI** with Bootstrap 5, auto-scroll, and distinct styling for own vs other messages
- **Server-side timestamping** of messages

## Tech Stack

| Technology | Purpose |
|------------|---------|
| [Java 21](https://openjdk.org/projects/jdk/21/) | Runtime |
| [Spring Boot 4.1.2](https://spring.io/projects/spring-boot) | Framework |
| [Spring WebSocket](https://docs.spring.io/spring-framework/reference/web/websocket.html) | STOMP/WebSocket messaging |
| [Thymeleaf](https://www.thymeleaf.org/) | Server-side templating |
| [Lombok](https://projectlombok.org/) | Boilerplate reduction |
| [SockJS](https://github.com/sockjs/sockjs-client) & [STOMP.js](https://github.com/stomp-js/stompjs) | Client-side WebSocket/STOMP |
| [Bootstrap 5](https://getbootstrap.com/) | UI styling |
| [Maven](https://maven.apache.org/) | Build tool |

## Architecture

```
src/main/java/com/bryandev/chat/
├── ChatWebsocketApplication.java  # Spring Boot entrypoint
├── config/
│   └── WebSocketConfig.java       # STOMP broker & endpoint configuration
├── controller/
│   ├── ChatController.java        # Message routing (@MessageMapping / @SendTo)
│   └── ViewController.java        # View routing (GET /chat)
└── model/
    └── Message.java               # Message DTO (from, text, timestamp)
```

### Message Flow

1. Client connects to `SockJS('/chat-websocket')` and establishes STOMP connection
2. Client subscribes to `/topic/messages` to receive broadcast messages
3. Client sends message to `/app/sendMessage` with `{ from, text }`
4. `ChatController` sets `timestamp = LocalDateTime.now()` and broadcasts to `/topic/messages`
5. All subscribed clients receive the message and render it in the UI

### WebSocket Configuration

- **Endpoint**: `/chat-websocket` (with SockJS, allowed origins: `*`)
- **Application destination prefix**: `/app` (client → server)
- **Broker destination prefix**: `/topic` (server → clients, in-memory simple broker)

## Prerequisites

- [Java 21 JDK](https://adoptium.net/) installed and on `PATH`
- No external database or message broker required (uses in-memory broker)

## Getting Started

### Clone the repository

```bash
git clone <repository-url>
cd chat-websocket
```

### Run in development mode

**Linux/macOS:**
```bash
./mvnw spring-boot:run
```

**Windows:**
```cmd
mvnw.cmd spring-boot:run
```

The application will start on `http://localhost:8080` by default.

### Access the chat

Open your browser and navigate to [http://localhost:8080/chat](http://localhost:8080/chat). Enter your name and start chatting!

## Build & Test

| Command | Description |
|---------|-------------|
| `./mvnw clean compile` | Compile the project |
| `./mvnw test` | Run all tests |
| `./mvnw test -Dtest=ChatWebsocketApplicationTests` | Run a specific test class |
| `./mvnw clean package` | Build JAR in `target/` |
| `./mvnw clean package -DskipTests` | Build without running tests |
| `./mvnw spring-boot:run` | Run the application |

## Configuration

Configuration is minimal in `src/main/resources/application.yaml`:

```yaml
spring:
  application:
    name: chat-websocket
```

To change the server port, add:
```yaml
server:
  port: 8080
```

## Future Improvements

### Core Functionality
- [ ] **User presence** - Show active users/online count using `SimpUserRegistry` or presence tracking
- [ ] **Private messaging** - Add user-specific queues (`/user/{username}/queue/messages`) for direct messages
- [ ] **Chat rooms/channels** - Support multiple rooms with dynamic subscriptions
- [ ] **Message persistence** - Store messages in a database (e.g., PostgreSQL/H2) with chat history
- [ ] **Typing indicators** - Show when users are typing
- [ ] **Message edit/delete** - Allow users to modify or remove their messages
- [ ] **Reactions/emojis** - Add emoji reactions to messages

### Security & Auth
- [ ] **Authentication** - Integrate Spring Security with login/session management
- [ ] **Authorization** - Control access to rooms/features
- [ ] **Rate limiting** - Prevent message spam
- [ ] **Input validation** - Sanitize/validate message content on server side
- [ ] **CORS restrictions** - Replace `setAllowedOriginPatterns("*")` with specific allowed origins

### UX/UI
- [ ] **Responsive design** - Improve mobile experience
- [ ] **Dark mode** - Add theme toggle
- [ ] **Message formatting** - Support markdown, links, or code blocks
- [ ] **File/image sharing** - Allow uploading and sharing media
- [ ] **Notifications** - Browser notifications for new messages
- [ ] **Message timestamps localization** - Improve timezone handling
- [ ] **Auto-reconnect UI feedback** - Better connection status indicators

### Technical Enhancements
- [ ] **External message broker** - Use RabbitMQ/ActiveMQ for scalability across instances
- [ ] **Redis pub/sub** - Enable horizontal scaling with multiple server instances
- [ ] **WebSocket configuration** - Add heartbeat, message size limits, and better error handling
- [ ] **Logging** - Add structured logging and monitoring (Actuator, Micrometer)
- [ ] **Testing** - Add integration tests for WebSocket/STOMP endpoints
- [ ] **Dockerization** - Add `Dockerfile` and `docker-compose.yml`
- [ ] **CI/CD** - Add GitHub Actions for build/test
- [ ] **Configuration profiles** - Support dev/prod environments

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is available for educational and personal use.
