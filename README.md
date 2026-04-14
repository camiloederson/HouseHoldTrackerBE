# HouseholdTracker Backend

## 📌 Overview
This is the backend service for HouseholdTracker — a full‑stack application designed to help families and individuals manage budgets and daily expenses.  
It provides secure APIs for households, budgets, categories, and expenses, with validation rules to prevent overspending.

## 🛠 Tech Stack
- Java 17, Spring Boot, Hibernate
- MySQL
- DTOs, manual mappers, layered services, repositories, controllers
- Deployed on Railway

## ✨ Features
- JWT authentication for secure login and registration
- CRUD operations for households, budgets, categories, and expenses
- Validation rules (expenses ≤ income)
- RESTful API endpoints

## 🚀 Setup
```bash
git clone <repo-url>
cd householdtracker-backend
mvn clean install
java -jar target/app.jar
