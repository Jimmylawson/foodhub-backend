# Zomato Backend Project Plan

## Project Overview
Backend service for Zomato-like food delivery platform built with Spring Boot.

## Working on UserDetailService
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
