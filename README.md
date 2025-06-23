Lab Report-02




Gandaki College of Engineering and Science
Distributed System 
Lab Experiment: UDP Protocol
                                                                                  
Submitted By:                                                                                      
Name: Dipendra Raut Kurmi
Roll.No: 18
Batch: 2021SE

Submitted To:
Er. Amrit Poudel
Lecturer at Gandaki College Of         
Engineering and Science


Objective:
To implement data communication between a client and server using the UDP protocol in Java.
Theory:
UDP (User Datagram Protocol) is a connectionless transport layer protocol that sends data as independent packets called datagrams. It does not guarantee delivery, order, or duplicate protection, but is faster and has lower overhead than TCP.

Features of UDP:
- No connection setup (connectionless)
- No guarantee of delivery
- No ordering of packets
- Fast and low latency
- Suitable for real-time apps (VoIP, video streaming, online gaming)

Result:
When running the UDPServer.java and UDPClient.java programs:
1. The server listens on port 9876.
2. The client sends a message to the server using UDP datagrams.
3. The server receives the message, processes it (converts it to uppercase), and sends it back to the client.
4. The client receives and displays the server's response.
Server:
      
Client:



Conclusion:
Hence, we successfully connected client and server through UDP protocol.In this lab, we implemented a basic UDP client-server program where the client sends a message to the server, and the server responds after processing it.
