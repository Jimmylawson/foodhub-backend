# Zomato Backend Project Plan

## Project Overview
Backend service for Zomato-like food delivery platform built with Spring Boot.

## Working on UserDetailService(Done)
 #User Roles
- USER,/// Regular customer placing orders
- ADMIN, /// Admin with platform privileges
- DELIVERY, ///  Delivery personnel
- OWNER /// Restaurant owner


## Current Implementation Status
- [x] Basic project setup with Spring Boot
- [x] Security configuration with Spring Security
  - [x] Basic authentication setup
  - [x] Public endpoints configuration
  - [ ] JWT authentication
  - [ ] Role-based access control

## API Endpoints
### Public Endpoints (No Auth Required)
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/login` - User login
- `GET /api/v1/public/restaurants` - List all restaurants
- `GET /api/v1/public/restaurants/{id}` - Get restaurant details
- `GET /api/v1/public/menu/{restaurantId}` - View restaurant menu

### Protected Endpoints (Requires Authentication)
- `POST /api/v1/orders` - Place new order
- `GET /api/v1/users/me` - Get current user profile
- `PUT /api/v1/users/me` - Update user profile
- `GET /api/v1/orders` - View order history

## Upcoming Features
### High Priority
- [ ] User Authentication
  - [ ] JWT implementation
  - [ ] Refresh token mechanism
  - [ ] Password reset flow
- [ ] Restaurant Management
  - [ ] CRUD operations for restaurants
  - [ ] Menu management
  - [ ] Search and filter restaurants

### Medium Priority
- [ ] Order Management
  - [ ] Order placement
  - [ ] Order status updates
  - [ ] Order history
- [ ] Payment Integration
  - [ ] Payment gateway integration
  - [ ] Payment history

### Low Priority
- [ ] Reviews and Ratings
- [ ] Push Notifications
- [ ] Analytics Dashboard

## Technical Debt & Refactoring
- [ ] Move PasswordEncoder to a separate configuration class
- [ ] Add request/response DTOs
- [ ] Implement proper exception handling
- [ ] Add API documentation with Swagger/OpenAPI

## Notes
- Using Spring Boot 3.x
- Database: PostgreSQL
- Authentication: JWT (to be implemented)
- API Versioning: v1

## Recent Changes
- 2025-07-16: Initial project setup and security configuration

## Upcoming Features & Tasks

### 1. JWT Authentication
- [ ] **JWT Utility Class**
  - Generate JWT tokens
  - Validate tokens
  - Extract claims and expiration
- [ ] **JWT Filter**
  - Implement OncePerRequestFilter
  - Extract and validate tokens from requests
  - Set authentication in security context
- [ ] **Security Chain Integration**
  - Add JWT filter before UsernamePasswordAuthenticationFilter
  - Configure token endpoints

### 2. Password Reset Flow
- [ ] **Endpoints**
  - `POST /api/v1/auth/forgot-password` - Initiate password reset
  - `POST /api/v1/auth/reset-password` - Complete password reset
- [ ] **Token Management**
  - Generate secure reset tokens
  - Set token expiration (e.g., 1 hour)
  - Store tokens securely
- [ ] **Email Service**
  - Send password reset emails
  - HTML email templates
  - Email queue for better performance

### 3. Refresh Tokens
- [ ] **Token Generation**
  - Create long-lived refresh tokens
  - Store hashed tokens in database
- [ ] **Secure Storage**
  - HttpOnly cookies for web clients
  - Secure flag for HTTPS
- [ ] **Refresh Endpoint**
  - `POST /api/v1/auth/refresh-token`
  - Validate refresh token
  - Issue new access token

### 4. Security Hardening
- [ ] **CSRF Protection**
  - Enable/configure based on client type
  - Cookie-based CSRF tokens
- [ ] **CORS Configuration**
  - Configure allowed origins
  - Set allowed methods and headers
- [ ] **Session Management**
  - Stateless session policy
  - Concurrent session control
- [ ] **Rate Limiting**
  - Implement per-endpoint limits
  - Block suspicious IPs

### 5. Testing
- [ ] **Unit Tests**
  - Authentication service
  - JWT utilities
  - Password encoding
- [ ] **Integration Tests**
  - Protected endpoints
  - Role-based access
  - Token refresh flow
- [ ] **Edge Cases**
  - Expired tokens
  - Invalid tokens
  - Rate limiting
  - Concurrent logins

## Recent Changes
- 2025-07-16: Implemented custom AuthenticationProvider with exception handling
- 2025-07-16: Initial project setup and security configuration