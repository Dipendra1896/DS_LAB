# Bully Algorithm for Leader Election

This project implements the Bully Algorithm, a method for leader election in distributed systems. The algorithm allows processes in a distributed system to elect a coordinator among themselves, ensuring that there is always one active leader.

## Project Structure

```
bully-algorithm-java
└── BullyAlgorithm.java
├── README.md
```

- `BullyAlgorithm.java`: Contains the implementation of the Bully Algorithm, including the `Process` class and the main simulation.
- `README.md`: Documentation for the project.

## How to Run

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Compile the Java source file:
   ```
   javac BullyAlgorithm.java
   ```
4. Run the compiled Java program:
   ```
   java  BullyAlgorithm
   ```

## Description

The `Process` class represents a process in the distributed system, with properties such as:
- `id`: Unique identifier for the process.
- `isAlive`: Status indicating if the process is alive.
- `isCoordinator`: Status indicating if the process is the current coordinator.

The class includes methods for:
- Starting an election when a coordinator fails.
- Receiving election messages from other processes.
- Declaring a new coordinator when an election is successful.

The `BullyAlgorithm` class contains the `main` method, which simulates the creation of processes, the failure of a coordinator, and the election process.

## Requirements

- Java Development Kit (JDK) installed on your machine.
- Basic understanding of Java and distributed systems concepts.

## License

This project is open-source and available for modification and distribution under the MIT License.