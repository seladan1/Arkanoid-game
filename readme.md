# Arkanoid Game 🧱🏓

A classic Arkanoid (Breakout) style 2D game implemented in Java. 
This project was developed as part of an Object-Oriented Programming (OOP) course, focusing on software architecture, design patterns, and physics-based interactions.

## 🚀 Features

* **Classic Gameplay:** Control the paddle to bounce the ball and destroy all the blocks on the screen.
* **Score Tracking:** Real-time score updates as blocks are hit and removed.
* **Robust Physics & Geometry:** Custom implementation of 2D geometric primitives (Points, Lines, Rectangles) to calculate precise intersection points and velocity changes.
* **Event-Driven Mechanics:** Uses the **Observer Design Pattern** to handle events such as block destruction and score updating without coupling the game objects together.

## 🏗️ Architecture & Design

The project is heavily heavily modular and follows strong OOP principles:

* `geometry/` - Contains the mathematical backbone of the game (Lines, Points, Rectangles).
* `sprites/` - Manages all visual game objects (Ball, Paddle, Blocks) that implement the `Sprite` interface.
* `listeners/` - Implements the Observer pattern (`HitListener`, `HitNotifier`) to trigger actions when objects collide.
* `game/` - Orchestrates the game loop, animations, and environment initialization.

## ⚙️ How to Run

This project uses **Apache Ant** and relies on the `biuoop` library for GUI and animations.

1. Clone the repository.
2. Ensure you have the required `biuoop` jar in your project structure.
3. Open your terminal in the project's root directory and run the following commands:

To compile the source code:
```bash
ant compile
```

To run the game:
```bash
ant run
```

*(Note: Depending on your specific `build.xml` targets, the run command might be `ant run` or similar).*

## 🛠️ Technologies
* **Java** (JDK)
* **Apache Ant** (Build tool)
* **Object-Oriented Design Patterns** (Observer, Interfaces, Composition)