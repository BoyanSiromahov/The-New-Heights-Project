package Util;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class UDPHelper {
	

	private DatagramSocket socket;
	private DatagramPacket sendPacket, receivePacket;
	private byte[] data;
	
	public UDPHelper(int portNumber) {
		// Construct DatagramSocket and bind it to portNumber
		try {
			socket = new DatagramSocket(portNumber);
		} catch (SocketException se) {
			// Error creating DatagramSocket
			System.out.println("Error creating DatagramSocket:");
			se.printStackTrace();
			System.exit(1);
		}	
	}

	public void sendAndReceive(byte[] message, int destinationPort) {
		
		// Construct DataPacket to send message
		try {
			sendPacket = new DatagramPacket(message, message.length, InetAddress.getLocalHost(), destinationPort);
		} catch (UnknownHostException e) {
			System.out.println("Error constructing sendPacket:");
			e.printStackTrace();
			System.exit(1);
		}
				
		// Print packet information
		System.out.println("Port " + socket.getPort() + ": sending packet");
		System.out.println("To host: " + sendPacket.getAddress());
		System.out.println("Destination host port: " + sendPacket.getPort());
		System.out.println("Data as string: ");
		System.out.println(new String(sendPacket.getData(), 0, sendPacket.getLength()));
		System.out.println("Data in bytes: ");
		System.out.println(Arrays.toString(data));
				
		// Send the DatagramPacket 
		try {
			socket.send(sendPacket);
		} catch (IOException e) {
			System.out.println("Error sending DatagramPacket:");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Packet sent.\n");
			
		
		// Construct DatagramPacket to receive data
		data = new byte[100];
		receivePacket = new DatagramPacket(data, data.length);
		
		// Block until a DatagramPacket response is received
		try { 
			System.out.println("UDP Handler: waiting for packet");
			socket.receive(receivePacket);
			data = receivePacket.getData();
		} catch(IOException e) {
			System.out.println("Error receiving DatagramPacket response:");
			e.printStackTrace();
			System.exit(1);
		}
		 	
		// Process the received DataPacket
		System.out.println("Port " + socket.getPort() + ": packet received");
		System.out.println("From host: " + receivePacket.getAddress());
		System.out.println("Host port: " + receivePacket.getPort());
		System.out.println("Data as string: ");
		System.out.println(new String(receivePacket.getData(), 0, receivePacket.getLength()));
		System.out.println("Data in bytes: ");
		System.out.println(Arrays.toString(receivePacket.getData()));
	}
	
}
