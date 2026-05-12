<div align="center">

# DailyCart

### A Modern Grocery Delivery Android App built with Kotlin

**DailyCart is a professional e-commerce application designed for rapid grocery delivery, featuring real-time search, category-based filtering, and a robust order tracking system—built using modern Android development practices.**

Focused on clean architecture, local data persistence, and scalable UI patterns, this project demonstrates practical Android app development for B.Tech academic and professional portfolios.

[Features](#-key-features) • [Architecture](#-architecture--flow) • [Tech Stack](#-tech-stack--architecture) • [Getting Started](#-getting-started)

</div>

---

##  Project Overview

Many e-commerce applications fail to maintain data integrity when product details change over time. **DailyCart** addresses this by implementing professional data management and a high-performance UI matching a modern brand identity.

**Key Objectives:**
- **Fast Navigation**: A clean, Green-Orange themed UI matching the brand logo.
- **Data Integrity**: Order history that preserves exact prices and addresses at the time of purchase via JSON serialization.
- **Intelligent Search**: Real-time filtering with custom empty-state handling.

---

##  Architecture & Flow

The app follows the **MVVM (Model-View-ViewModel)** pattern to ensure scalability and ease of testing, which is critical for complex backend-integrated systems.

```mermaid
flowchart TD
    A[User Interface - Fragment] --> B[ViewModel]
    B --> C[Repository]
    C --> D[Local DB - Room]
    D --> C
    C --> E[Static Data - Constants]
    B --> A
 ```

### Architectural Highlights

* **Repository Pattern**: Acts as a single source of truth for both static constants and Room database data.
* **Reactive UI**: Uses LiveData to ensure the UI updates instantly when products are filtered or added to the cart.
* **Type-Safe Navigation**: Implementation of the Jetpack Navigation Component for structured fragment transitions.

---

##  Key Features

* **Real-time Search**: Instant filtering of the master product list with dedicated "No Results" iconography.
* **Category Browsing**: Thematic exploration for Vegetables, Fruits, Dairy, Snacks, and more.
* **Custom Splash Screen**: A professional entry point featuring smooth fade-in and scale animations.
* **Bill Summary**: A detailed breakdown of costs including item totals, delivery charges, and grand totals.

---

##  Tech Stack

* **Language**: Kotlin
* **Local Database**: Room Persistence Library with JSON TypeConverters
* **Architecture**: MVVM with Coroutines for asynchronous operations
* **Design**: Material3 components with a custom brand color palette

---

##  Author

**Maksud Rahman**

GitHub: [https://github.com/iRahmanG](https://github.com/iRahmanG)
