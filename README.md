# TaskFlow

A comprehensive project and task management system built with Spring Boot, featuring role-based access control, company management, project tracking, and task assignment capabilities. Currently evolving into a distributed microservices architecture with React frontend.

## Features

- 🔐 **Advanced Authentication** - JWT with refresh token support and company switching
- 🏢 **Company Management** - Multi-company support with hierarchical roles
- 👥 **Role-Based Access Control** - ADMIN, OWNER, MANAGER, MEMBER, and USER roles
- 📊 **Project Management** - Create and manage projects with team assignments
- ✅ **Task Management** - Create, assign, and track tasks within projects
- 🔄 **Company Switching** - Users can switch between multiple companies
- 🐳 **Dockerized** - Fully containerized application with Docker Compose
- 🎯 **RESTful API** - Clean, well-structured API with consistent responses

## Tech Stack

### Current Stack
- **Spring Boot** - REST API framework
- **Spring Security** - Authentication and authorization with method-level security
- **JWT** - Access and refresh token implementation
- **MySQL** - Primary database
- **Docker & Docker Compose** - Containerization
- **Maven** - Dependency management

### Upcoming Features 🚧
- **React** - Frontend UI (In Development)
- **Apache Kafka** - Event-driven architecture and distributed messaging
- **Redis** - Caching layer for performance optimization
- **MinIO** - S3-compatible object storage for images and files
- **Microservices Architecture** - Service decomposition

## API Endpoints

### Authentication
```
POST /api/v1/auth/register         - User registration
POST /api/v1/auth/login            - User login
POST /api/v1/auth/switch-company   - Switch active company
POST /api/v1/auth/refresh          - Refresh access token
```

### Users
```
GET    /api/v1/users/me           - Get current user profile
GET    /api/v1/users/{userId}     - Get user by ID [ADMIN]
PUT    /api/v1/users/profile      - Update user profile
DELETE /api/v1/users/{userId}     - Delete user [ADMIN]
```

### Companies
```
POST   /api/v1/companies                      - Create company [ADMIN]
GET    /api/v1/companies/{id}                 - Get company details [ADMIN]
PUT    /api/v1/companies/{id}/owner           - Update company owner [ADMIN]
POST   /api/v1/companies/{id}/members         - Add member to company [ADMIN/OWNER/MANAGER]
PUT    /api/v1/companies/{id}/members/{memberId}  - Promote to MEMBER [ADMIN/OWNER/MANAGER]
PUT    /api/v1/companies/{id}/managers/{memberId} - Promote to MANAGER [ADMIN/OWNER]
```

### Projects
```
POST   /api/v1/projects                - Create project [ADMIN/OWNER/MANAGER]
GET    /api/v1/projects/{projectId}    - Get project details [ADMIN/OWNER/MANAGER]
PUT    /api/v1/projects/{projectId}    - Update project [ADMIN/OWNER/MANAGER]
PUT    /api/v1/projects/{projectId}/members - Add member to project [ADMIN/OWNER/MANAGER]
```

### Tasks
```
POST   /api/v1/task                          - Create task [ADMIN/OWNER/MANAGER]
GET    /api/v1/task/{taskId}                 - Get task details
PUT    /api/v1/task/{taskId}                 - Update task
DELETE /api/v1/task/{taskId}                 - Delete task [ADMIN/OWNER/MANAGER]
PUT    /api/v1/task/{taskId}/member/{memberId} - Assign task to member [ADMIN/OWNER/MANAGER]
```

## Getting Started

### Prerequisites
- Java 17+
- Docker & Docker Compose
- Maven
- Node.js 16+ (for upcoming frontend)

### Running with Docker Compose

1. Clone the repository
```bash
git clone https://github.com/rgl456/taskflow.git
cd taskflow
```

2. Create `.env` file in the project root
```env
MYSQL_PASSWORD=your_secure_password
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION=3600000
REFRESH_TOKEN_EXPIRATION=86400000
```

3. Start the application
```bash
docker-compose up -d
```

The application will be available at:
- **API**: `http://localhost:8080`
- **MySQL**: `localhost:3307`

### Running Locally (Without Docker)

1. Configure `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/task_flow
spring.datasource.username=root
spring.datasource.password=your_password

# JWT Configuration
jwt.secret=your_jwt_secret_key
jwt.expiration=3600000
jwt.refresh.expiration=86400000
```

2. Run the application
```bash
mvn spring-boot:run
```

## Docker Configuration

### Services
- **task-flow-db**: MySQL 8.0 database container
  - Port: `3307:3306`
  - Volume: `task-flow-data` for data persistence
  - Network: `task-flow-net`

### Docker Commands
```bash
# Start services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

## Project Structure

```
taskflow/
├── src/main/java/
│   ├── controllers/
│   │   ├── AuthController.java
│   │   ├── UserController.java
│   │   ├── CompanyController.java
│   │   ├── ProjectController.java
│   │   └── TaskController.java
│   ├── services/
│   ├── repositories/
│   ├── models/
│   │   ├── User.java
│   │   ├── Company.java
│   │   ├── Project.java
│   │   └── Task.java
│   ├── security/
│   ├── dto/
│   └── config/
├── docker-compose.yml
├── Dockerfile
└── pom.xml
```

## Role Hierarchy & Permissions

### ADMIN (System Administrator)
- Full system access
- Create and manage companies
- Manage all users
- Override all permissions

### OWNER (Company Owner)
- Manage company settings
- Assign MANAGER and MEMBER roles
- Create and manage all projects
- Full access within their company

### MANAGER (Project Manager)
- Create and manage projects
- Assign MEMBER roles
- Create and assign tasks
- Manage project members

### MEMBER (Team Member)
- View assigned projects
- Create and update tasks
- View company information
- Update own profile

### USER (Basic User)
- Register and login
- Update own profile
- Join companies when invited

## Key Features Details

### Multi-Company Support
- Users can belong to multiple companies
- Switch between companies without re-authentication
- Company-specific role assignments
- Isolated data per company

### Authentication Flow
- JWT access token (short-lived, 1 hour)
- Refresh token (long-lived, 24 hours)
- Company context switching
- Role-based method-level security

### Task Assignment Workflow
```
1. MANAGER/OWNER creates project
2. MANAGER/OWNER adds members to project
3. MANAGER/OWNER creates tasks in project
4. MANAGER/OWNER assigns tasks to members
5. MEMBER updates task status
```

## API Response Format

All endpoints return a consistent response structure:

```json
{
  "message": "Operation successful",
  "data": {
    // Response data
  },
  "timestamp": "2024-01-01T00:00:00"
}
```

## Security Features

- Password hashing with BCrypt
- JWT access and refresh tokens
- Method-level security with `@PreAuthorize`
- Role-based access control (RBAC)
- Input validation with `@Valid`
- Email validation for owner assignments
- Secure company isolation

## Upcoming Features 🚀

### Frontend (React)
- Modern, responsive UI
- Real-time task updates
- Drag-and-drop task boards
- Project dashboards
- User management interface

### Distributed Architecture
- **Apache Kafka**: Event-driven communication between services
  - Task assignment notifications
  - Project updates
  - User activity logging
  - Real-time notifications

### Performance Optimization
- **Redis**: Caching layer
  - User session management
  - Frequently accessed data
  - Rate limiting
  - Task status caching

### File Management
- **MinIO**: S3-compatible object storage
  - User profile pictures
  - Project attachments
  - Task documents
  - Company logos

### Additional Features
- Real-time notifications
- Task comments and attachments
- Project analytics and reporting
- Time tracking
- Gantt charts
- Email notifications
- Activity logs

## Development Roadmap

- [x] Core authentication system
- [x] Role-based access control
- [x] Company management
- [x] Project management
- [x] Task management
- [x] Docker containerization
- [ ] React frontend development
- [ ] Kafka integration for messaging
- [ ] Redis caching implementation
- [ ] MinIO for file storage
- [ ] Microservices decomposition
- [ ] Real-time notifications
- [ ] Analytics dashboard
- [ ] Mobile app (Future)

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Environment Variables

```env
# Database
MYSQL_PASSWORD=your_db_password

# JWT
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=3600000
REFRESH_TOKEN_EXPIRATION=86400000

# Redis (Upcoming)
REDIS_HOST=localhost
REDIS_PORT=6379

# Kafka (Upcoming)
KAFKA_BOOTSTRAP_SERVERS=localhost:9092

# MinIO (Upcoming)
MINIO_ENDPOINT=localhost:9000
MINIO_ACCESS_KEY=your_access_key
MINIO_SECRET_KEY=your_secret_key
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

Email - [ragul7690@gmail.com](mailto:ragul7690@gmail.com)

Project Link: [https://github.com/yourusername/taskflow](https://github.com/rgl456/taskflow)

---

⚙️ **Status**: Active Development | 🎯 **Version**: 1.0.0-BETA | 🚀 **Next Release**: Q1 2024

Made with ❤️ using Spring Boot, Docker, and soon React + Kafka + Redis + MinIO
