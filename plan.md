# Zomato Backend

## Project Overview
Backend service for Zomato-like food delivery platform built with Spring Boot.

## Recent Changes
- 2025-07-16: Implemented custom AuthenticationProvider with exception handling
- 2025-07-16: Initial project setup and security configuration
- 2025-07-16: JWT Authentication implemented

## Next Changes
- 2025-07-17: Password implementation
- 2025-07-17: Make sure JWT is working 
- 2025-07-17: Write Testing for all endpoints 


## Core Backend Features

### 1. Authentication & Security
- [ ] JWT Authentication
  - [x] Basic JWT implementation
  - [ ] Password reset functionality
    - [ ] Email service integration
    - [ ] Token-based password reset flow
    - [ ] Rate limiting for reset requests
  - [ ] Email verification for new users
  - [ ] Refresh token rotation
  - [ ] Account lockout after failed attempts
  - [ ] Session management
  - [ ] Two-factor authentication (2FA)
  - [ ] Social login (Google, Facebook, etc.)
  - [ ] Terms of service acceptance tracking

### 2. User Management
- [ ] User profile management
  - [ ] CRUD operations
  - [ ] Update profile information
  - [ ] Change password
  - [ ] Upload profile picture
- [ ] Role-based access control
  - [ ] User roles and permissions
  - [ ] Admin dashboard for user management

### 3. Restaurant & Menu Management
- [ ] Restaurant CRUD operations
- [ ] Menu management
- [ ] Categories & items
- [ ] Search & filtering
- [ ] Ratings & reviews

### 4. Order Management
- [ ] Cart management
- [ ] Order placement
- [ ] Order status updates
- [ ] Order history
- [ ] Payment integration

## Technical Implementation

### 1. API Development
- [ ] RESTful endpoints
- [ ] Request validation
- [ ] Error handling
- [ ] API versioning
- [ ] Rate limiting
- [ ] Swagger/OpenAPI documentation

### 2. Security
- [ ] CORS configuration
- [ ] CSRF protection
- [ ] Security headers
- [ ] Input validation
- [ ] SQL injection prevention
- [ ] XSS protection
- [ ] Audit logging

### 3. Performance
- [ ] Caching for frequently accessed data
- [ ] Database indexing
- [ ] Query optimization
- [ ] Load testing
- [ ] Connection pooling

### 4. Testing
- [ ] Unit tests
  - [ ] Authentication service
  - [ ] User service
  - [ ] Order service
- [ ] Integration tests
  - [ ] Auth endpoints
  - [ ] Protected routes
  - [ ] Token expiration and refresh flow
- [ ] Security testing
  - [ ] OWASP ZAP
  - [ ] Dependency scanning

### 5. Monitoring & Logging
- [ ] Centralized logging (ELK stack)
- [ ] Application metrics
- [ ] Health checks
- [ ] Error tracking
- [ ] Performance monitoring

### 6. Deployment & DevOps
- [ ] Docker configuration
- [ ] CI/CD pipeline
- [ ] Environment configuration
- [ ] Database migrations
- [ ] Blue-green deployment

## Documentation
- [ ] API documentation (Swagger/OpenAPI)
- [ ] Database schema
- [ ] Setup instructions
- [ ] Deployment guide
- [ ] Troubleshooting guide
- [ ] API usage examples

## Project Management
- [ ] GitHub project board
- [ ] Issue templates
- [ ] Pull request templates
- [ ] Code review guidelines

## Future Enhancements
- [ ] Real-time order tracking
- [ ] Push notifications
- [ ] Analytics dashboard
- [ ] Recommendation engine
- [ ] Multi-language support

## Technical Stack
- **Backend**: Spring Boot 3.x
- **Database**: PostgreSQL
- **Authentication**: JWT
- **Build Tool**: Maven/Gradle
- **Containerization**: Docker
- **Testing**: JUnit, Mockito, TestContainers
- **Documentation**: Swagger/OpenAPI
- **CI/CD**: GitHub Actions/Jenkins