# Bully and Ring Algorithms for Leader Election

This project implements two popular leader election algorithms for distributed systems: the **Bully Algorithm** and the **Ring Algorithm**. Both algorithms allow processes in a distributed system to elect a coordinator among themselves, ensuring that there is always one active leader.

## Project Structure

```
Lab-04/
├── BullyAlgorithm.java
├── RingAlgorithm.java
├── README.md
```

- `BullyAlgorithm.java`: Contains the implementation of the Bully Algorithm, including the `Process` class and the main simulation.
- `RingAlgorithm.java`: Contains the implementation of the Ring Algorithm, including the `RingProcess` class and the main simulation.
- `README.md`: Documentation for the project.

## How to Run

1. Clone the repository to your local machine.
2. Navigate to the project directory.

### To run the Bully Algorithm:
   ```
   javac BullyAlgorithm.java
   java BullyAlgorithm
   ```

### To run the Ring Algorithm:
   ```
   javac RingAlgorithm.java
   java RingAlgorithm
   ```

## Description

### Bully Algorithm

The `Process` class represents a process in the distributed system, with properties such as:
- `id`: Unique identifier for the process.
- `isAlive`: Status indicating if the process is alive.
- `isCoordinator`: Status indicating if the process is the current coordinator.

The class includes methods for:
- Starting an election when a coordinator fails.
- Receiving election messages from other processes.
- Declaring a new coordinator when an election is successful.

The `BullyAlgorithm` class contains the `main` method, which simulates the creation of processes, the failure of a coordinator, and the election process.

### Ring Algorithm

The `RingProcess` class represents a process in a logical ring, with similar properties:
- `id`: Unique identifier for the process.
- `isAlive`: Status indicating if the process is alive.
- `isCoordinator`: Status indicating if the process is the current coordinator.

The class includes methods for:
- Starting an election by passing a message around the ring.
- Declaring a new coordinator after collecting all alive process IDs.

The `RingAlgorithm` class contains the `main` method, which simulates the creation of processes, the failure of a coordinator, and the election process using the ring structure.

## Requirements

- Java Development Kit (JDK) installed on your machine.
- Basic understanding of Java and distributed systems concepts.

## License

This project is open-source and available for modification and distribution under the MIT License.