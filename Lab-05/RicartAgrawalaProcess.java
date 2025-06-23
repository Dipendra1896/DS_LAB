import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class RicartAgrawalaProcess {
    private int pid;
    private int clock;
    private List<Integer> otherPorts;
    private ServerSocket serverSocket;
    private boolean wantCS = false;
    private int repliesNeeded;
    private PriorityQueue<Request> deferredRequests = new PriorityQueue<>();
    
    // For tracking replies received
    private Set<Integer> repliesReceived = ConcurrentHashMap.newKeySet();

    public RicartAgrawalaProcess(int pid, int clock, List<Integer> otherPorts) {
        this.pid = pid;
        this.clock = clock;
        this.otherPorts = otherPorts;
        this.repliesNeeded = otherPorts.size();
    }

    public void start() {
        try {
            // Start server socket
            int port = 5000 + pid;
            serverSocket = new ServerSocket(port);
            System.out.println("Process " + pid + " listening on port " + port);

            // Start message listener thread
            new Thread(this::listenForMessages).start();

            // Start periodic CS requests
            new Thread(this::periodicallyRequestCS).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void periodicallyRequestCS() {
        Random random = new Random();
        while (true) {
            try {
                // Random delay between CS requests (5-15 seconds)
                Thread.sleep(5000 + random.nextInt(10000));
                
                // Request CS
                requestCriticalSection();
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void requestCriticalSection() {
        wantCS = true;
        clock++;
        repliesReceived.clear();
        
        System.out.println("Process " + pid + " requesting CS at time " + clock);
        
        // Send request to all other processes
        for (int port : otherPorts) {
            sendMessage(port, "REQUEST:" + clock + ":" + pid);
        }
    }

    private void listenForMessages() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClientConnection(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientConnection(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message = in.readLine();
            if (message != null) {
                processMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processMessage(String message) {
        String[] parts = message.split(":");
        String type = parts[0];
        int timestamp = Integer.parseInt(parts[1]);
        int senderPid = Integer.parseInt(parts[2]);
        
        // Update clock
        clock = Math.max(clock, timestamp) + 1;
        
        switch (type) {
            case "REQUEST":
                handleRequest(timestamp, senderPid);
                break;
            case "REPLY":
                handleReply(senderPid);
                break;
        }
    }

    private void handleRequest(int timestamp, int senderPid) {
        boolean shouldDefer = wantCS && (timestamp > clock || (timestamp == clock && senderPid > pid));
        
        if (shouldDefer) {
            // Defer the reply
            System.out.println("Process " + pid + " deferring reply to " + senderPid);
            deferredRequests.add(new Request(timestamp, senderPid));
        } else {
            // Reply immediately
            sendMessage(5000 + senderPid, "REPLY:" + clock + ":" + pid);
            System.out.println("Process " + pid + " sent reply to " + senderPid);
        }
    }

    private void handleReply(int senderPid) {
        System.out.println("Process " + pid + " received reply from " + senderPid);
        repliesReceived.add(senderPid);
        
        if (repliesReceived.size() == repliesNeeded && wantCS) {
            enterCriticalSection();
        }
    }

    private void enterCriticalSection() {
        wantCS = false;
        System.out.println("Process " + pid + " ENTERING critical section.");
        
        try {
            // Simulate CS work (3-5 seconds)
            Thread.sleep(3000 + new Random().nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        exitCriticalSection();
    }

    private void exitCriticalSection() {
        System.out.println("Process " + pid + " EXITING critical section.");
        
        
        // Reply to all deferred requests
        while (!deferredRequests.isEmpty()) {
            Request req = deferredRequests.poll();
            sendMessage(5000 + req.pid, "REPLY:" + clock + ":" + pid);
            System.out.println("Process " + pid + " sent deferred reply to " + req.pid);
        }
    }

    private void sendMessage(int port, String message) {
        try (Socket socket = new Socket("localhost", port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(message);
        } catch (IOException e) {
            System.err.println("Process " + pid + " failed to send message to port " + port);
        }
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: java RicartAgrawalaProcess <pid> <clock> <port1> <port2> ...");
            System.exit(1);
        }
        
        int pid = Integer.parseInt(args[0]);
        int clock = Integer.parseInt(args[1]);
        List<Integer> otherPorts = new ArrayList<>();
        
        for (int i = 2; i < args.length; i++) {
            otherPorts.add(Integer.parseInt(args[i]));
        }
        
        RicartAgrawalaProcess process = new RicartAgrawalaProcess(pid, clock, otherPorts);
        process.start();
    }
    
    private static class Request implements Comparable<Request> {
        int timestamp;
        int pid;
        
        Request(int timestamp, int pid) {
            this.timestamp = timestamp;
            this.pid = pid;
        }
        
        @Override
        public int compareTo(Request other) {
            if (this.timestamp != other.timestamp) {
                return Integer.compare(this.timestamp, other.timestamp);
            }
            return Integer.compare(this.pid, other.pid);
        }
    }
}