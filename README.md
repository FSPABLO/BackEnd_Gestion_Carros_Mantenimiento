# BackEnd_Gestion_Carros_Mantenimiento
# 🛠️ Backend – Java Sockets + Hibernate

## ¿De qué trata?
Servidor backend ligero que expone “endpoints” por **sockets** (no HTTP) para gestionar:
- **Auth** (login/registro)
- **Cars** (CRUD de vehículos)
- **Maintenance** (CRUD y listado por carro)

Los clientes envían/reciben JSON con `RequestDto` y `ResponseDto`.

## Tecnologías usadas
- **Java 21**, **Maven**
- **Hibernate ORM** + **MySQL**
- **Gson** (JSON)
- **Sockets TCP** (I/O con `SocketServer` y `ClientHandler`)
- Canal de **broadcast** con `MessageBroadcaster` (notificaciones, p. ej. login)

## ¿Cómo funciona?
- Escucha en **puerto 7070** (peticiones request/response).
- Emite mensajes en **puerto 7001** (suscripción persistente).
- Enrutamiento por `controller` y `request`:
  - `Auth`: `login`, `register`
  - `Cars`: `add`, `update`, `delete`, `list`, `get`
  - `Maintenance`: `add`, `update`, `delete`, `get`, `list_by_car`
- Persistencia con Hibernate (entidades: `User`, `Car`, `Maintenance`, enum `MaintenanceType {REPAIR, MOD, ROUTINE}`).

## Estructura breve
API/controllers/         AuthController, CarController, MaintenanceController
DataAccess/services/     AuthService, CarService, MaintenanceService (Hibernate)
Domain/models/           Entidades JPA + enum
Domain/dtos/             RequestDto / ResponseDto + DTOs específicos
Server/                  SocketServer, ClientHandler, MessageBroadcaster

## Arranque rápido
1. Configura `src/main/resources/hibernate.properties` con tu MySQL.
2. Crea la DB (p. ej. `javamvc`).
3. `mvn clean package`
4. Ejecuta `org.example.Main`  
   - Requests: **7070**  
   - Broadcast: **7001**
