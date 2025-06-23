import java.util.ArrayList;
import java.util.List;

class RingProcess {
    private int id;
    private boolean isAlive;
    private boolean isCoordinator;
    private List<RingProcess> processes;

    public RingProcess(int id) {
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

    public void setProcesses(List<RingProcess> processes) {
        this.processes = processes;
    }

    public void startElection() {
        System.out.println("Process " + id + " is starting an election (Ring Algorithm)");
        List<Integer> electionIds = new ArrayList<>();
        int n = processes.size();
        int current = (id % n); // next process in the ring

        // Add self to the election message
        electionIds.add(this.id);

        // Pass the election message around the ring
        while (true) {
            RingProcess next = processes.get(current);
            if (next.isAlive) {
                System.out.println("Process " + id + " sends election message to Process " + next.id);
                if (next.id == this.id) {
                    break; // Election message has returned to the initiator
                }
                electionIds.add(next.id);
            }
            current = (current + 1) % n;
        }

        // Find the highest id among alive processes
        int newCoordinatorId = -1;
        for (int pid : electionIds) {
            RingProcess p = processes.get(pid - 1);
            if (p.isAlive && pid > newCoordinatorId) {
                newCoordinatorId = pid;
            }
        }
        declareCoordinator(newCoordinatorId);
    }

    private void declareCoordinator(int coordinatorId) {
        System.out.println("Process " + coordinatorId + " is elected as the new coordinator (Ring Algorithm)");
        for (RingProcess p : processes) {
            p.setCoordinator(p.id == coordinatorId);
        }
        // Notify all alive processes in the ring
        int n = processes.size();
        int current = (coordinatorId % n);
        int start = current;
        do {
            RingProcess next = processes.get(current);
            if (next.isAlive) {
                System.out.println("Coordinator message sent to Process " + next.id);
            }
            current = (current + 1) % n;
        } while (current != start);
    }
}

public class RingAlgorithm {
    public static void main(String[] args) {
        // Create processes
        List<RingProcess> processes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            processes.add(new RingProcess(i));
        }

        // Set the process list for each process
        for (RingProcess p : processes) {
            p.setProcesses(processes);
        }

        // Set the initial coordinator (process 5)
        processes.get(4).setCoordinator(true);
        System.out.println("Initial coordinator is Process 5");

        // Simulate coordinator failure
        System.out.println("\nSimulating coordinator failure...");
        processes.get(4).setAlive(false);
        processes.get(4).setCoordinator(false);

        // Process 2 detects the failure and starts an election
        System.out.println("\nProcess 2 detects coordinator failure and starts election:");
        processes.get(1).startElection();

        // Simulate recovery of the original coordinator
        System.out.println("\nOriginal coordinator (Process 5) recovers:");
        processes.get(4).setAlive(true);
        processes.get(4).startElection();
    }
}