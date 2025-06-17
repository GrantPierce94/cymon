# Cymon - A Turn-Based Creature Adventure Game (Full-Stack Java)

**Cymon** is a full-stack, Pokémon-inspired adventure game that blends secure user interaction with dynamic, turn-based gameplay. Built with Android (Java) on the client side and Spring Boot (Java) on the backend, Cymon provides a persistent, real-time experience where players can explore environments, capture creatures, and battle using strategic mechanics — all while emphasizing secure architecture and responsible session handling.

This project was developed collaboratively under an Agile workflow, with distinct front-end, back-end, and game logic components. Security was integrated from the ground up, including proper authentication flows, scoped access control, and protections against common web threats.

## Security-Conscious Design Principles

- **Authentication & Session Security**  
  Cymon employs hashed credential storage, secure session tokens, and scoped access to ensure each user’s data is isolated and protected.

- **Scoped Authorization**  
  All game actions are validated server-side to enforce strict ownership rules and prevent privilege escalation (e.g., no unauthorized Cymon manipulation).

- **Input and Payload Validation**  
  Defensive coding was applied at API boundaries to mitigate injection attacks, malformed requests, or state corruption.

- **Secure Communication Design**  
  Engineered for HTTPS and WebSocket encryption to safeguard real-time battles and gameplay data in transit.

- **Session Integrity for Game Logic**  
  Real-time features (e.g., battles) are bound to authenticated sessions and undergo consistent verification to protect against spoofed commands or ghost clients.

## Game Features

- **User Account System**  
  Players register and log in to persist their game progress and Cymon collections.

- **Creature Capture & Evolution**  
  Explore, encounter, and capture unique Cymons — each with distinct abilities and the potential to evolve.

- **Turn-Based Strategy Combat**  
  Engage in tactical battles using elemental matchups, move priorities, and game state transitions.

- **Map-Based Exploration**  
  Navigate a multi-zone game world with dynamic event triggers and environmental logic.

- **Custom Team Building**  
  Assemble and optimize your Cymon roster to adapt to increasingly complex battle scenarios.

## Architecture Overview

### Frontend

- **Platform**: Android (Java, Android Studio)  
- **Features**:
  - Modular screens for Login, Map, Team, Battle, and Capture
  - Game logic tied to server validation
  - Real-time updates with WebSockets

### Backend

- **Platform**: Java (Spring Boot)  
- **Database**: MySQL  
- **Components**:
  - REST APIs for player actions, capture logic, and data retrieval
  - WebSocket battle engine for live combat sessions
  - Persistent storage for players, Cymons, and match history
  - Secure handling of login state, access scopes, and game events

## Setup Instructions

### Android Client

1. Clone the repository:
   ```bash
   git clone https://github.com/GrantPierce94/cymon.git
   ```
2. Open the `/frontend` folder in Android Studio.  
3. Sync Gradle and deploy the app on an emulator or Android device.

### Backend Server

1. Install Java 11+ and MySQL.  
2. Use `/backend/sql/` for initial schema setup.  
3. Build and run the server:
   ```bash
   cd backend
   ./gradlew build
   java -jar build/libs/cymon-server.jar
   ```

## Repository Structure

```
├── frontend/              # Android client
│   ├── activities/        # Login, Battle, Map, Team UI
│   ├── models/            # Cymon, Moves, Combat logic
│   └── utils/             # Game session & state management
│
├── backend/               # Spring Boot API and WebSocket server
│   ├── controllers/       # REST endpoints
│   ├── services/          # Game logic, state resolution
│   ├── repository/        # MySQL integration
│   └── websocket/         # Real-time communication
│
├── sql/                   # Database setup scripts
└── README.md              # Project overview
```

## Testing & Development Approach

- Unit tests for battle mechanics, login logic, and backend services  
- Integration tests for REST endpoints and data persistence  
- Manual functional testing across Android devices and emulators  
- Code reviews and iterative feedback as part of Agile sprints

## Tools & Technologies

- **Frontend**: Android SDK, Java, XML Layouts  
- **Backend**: Spring Boot, RESTful APIs, WebSockets  
- **Database**: MySQL  
- **DevOps**: GitHub, GitHub Actions  
- **Workflow**: Trello (Agile), Git Flow, Code Reviews

## Contributors

- **Grant Pierce** ([GitHub: @GrantPierce94](https://github.com/GrantPierce94)) – *Full-stack Developer*  
  - Led Android UI development and built five key gameplay screens  
  - Implemented core combat mechanics, session management, and user login flows  
  - Collaborated on backend integration and security modeling

- **Danny** – *Backend Developer*  
  - Designed and implemented the REST API and database schema  
  - Developed turn-resolution logic and real-time WebSocket messaging

- **Jack** – *Frontend and Gameplay Systems Developer*  
  - Developed social and multiplayer components such as chat, friend system, and in-game notifications  
  - Assisted with UI flow, state handling, and gameplay integration across Android activities  
  - Participated in design reviews and overall application cohesion

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.
