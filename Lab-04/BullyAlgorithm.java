import java.util.ArrayList;
import java.util.List;

class Process {
    private int id;
    private boolean isAlive;
    private boolean isCoordinator;
    private List<Process> processes;

    public Process(int id) {
        this.id = id;
        this.isAlive = true;
        this.isCoordinator = false;
        this.processes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isCoordinator() {
        return isCoordinator;
    }

    public void setCoordinator(boolean coordinator) {
        isCoordinator = coordinator;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public void startElection() {
        System.out.println("Process " + id + " is starting an election");
        
        boolean higherProcessExists = false;
        
        for (Process p : processes) {
            if (p.id > this.id && p.isAlive) {
                higherProcessExists = true;
                System.out.println("Process " + id + " sends election message to Process " + p.id);
                // In a real implementation, this would be a remote message
                p.receiveElection(this.id);
            }
        }
        
        if (!higherProcessExists) {
            declareVictory();
        }
    }

    public void receiveElection(int senderId) {
        if (this.isAlive) {
            System.out.println("Process " + id + " received election message from Process " + senderId);
            startElection();
        }
    }

    private void declareVictory() {
        System.out.println("Process " + id + " declares itself as the new coordinator");
        this.isCoordinator = true;
        
        // Notify all other processes
        for (Process p : processes) {
            if (p.id != this.id && p.isAlive) {
                System.out.println("Process " + id + " sends coordinator message to Process " + p.id);
                p.receiveCoordinator(this.id);
            }
        }
    }

    public void receiveCoordinator(int coordinatorId) {
        System.out.println("Process " + id + " acknowledges Process " + coordinatorId + " as coordinator");
        // Reset coordinator status for all processes
        for (Process p : processes) {
            p.setCoordinator(p.id == coordinatorId);
        }
    }
}

public class BullyAlgorithm {
    public static void main(String[] args) {
        // Create processes
        List<Process> processes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            processes.add(new Process(i));
        }
        
        // Set the process list for each process
        for (Process p : processes) {
            p.setProcesses(processes);
        }
        
        // Set the initial coordinator (process 5)
        processes.get(4).setCoordinator(true);
        System.out.println("Initial coordinator is Process 5");
        
        // Simulate coordinator failure
        System.out.println("\nSimulating coordinator failure...");
        processes.get(4).setAlive(false);
        processes.get(4).setCoordinator(false);
        
        // Process 3 detects the failure and starts an election
        System.out.println("\nProcess 3 detects coordinator failure and starts election:");
        processes.get(2).startElection();
        
        // Simulate recovery of the original coordinator
        System.out.println("\nOriginal coordinator (Process 5) recovers:");
        processes.get(4).setAlive(true);
        processes.get(4).startElection();
    }
}