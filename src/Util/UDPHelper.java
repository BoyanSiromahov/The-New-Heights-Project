package Util;

import java.io.IOException;
import java.net.*;

public class UDPHelper {
	

	private DatagramSocket socket;
	private DatagramPacket sendPacket, receivePacket;
	private int portNumber;
	private byte[] data;
	
	public UDPHelper(int portNumber) {
		// Construct DatagramSocket and bind it to portNumber
		this.portNumber = portNumber;
		try {
			socket = new DatagramSocket(portNumber);
		} catch (SocketException se) {
			// Error creating DatagramSocket
			System.out.println("Error creating DatagramSocket:");
			se.printStackTrace();
			System.exit(1);
		}	
	}

	public void send(byte[] message, int destinationPort) {
		
		// Construct DataPacket to send message
		try {
			sendPacket = new DatagramPacket(message, message.length, InetAddress.getLocalHost(), destinationPort);
		} catch (UnknownHostException e) {
			System.out.println("Error constructing sendPacket:");
			e.printStackTrace();
			System.exit(1);
		}
				
		// Print packet information
		System.out.println("Port " + portNumber + ": sending packet");
		System.out.println("To host: " + sendPacket.getAddress());
		System.out.println("Destination host port: " + sendPacket.getPort());
		System.out.print("Data as string: ");
		System.out.println(bytesToString(message));
		System.out.print("Data in bytes: ");
		printBytes(message);
		System.out.println();
				
		// Send the DatagramPacket 
		try {
			socket.send(sendPacket);
		} catch (IOException e) {
			System.out.println("Error sending DatagramPacket:");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Packet sent.\n");
	}
	
	public byte[] receive() {
		
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
		System.out.println("Port " + portNumber + ": packet received");
		System.out.println("From host: " + receivePacket.getAddress());
		System.out.println("Host port: " + receivePacket.getPort());
		System.out.print("Data as string: ");
		System.out.println(bytesToString(data));
		System.out.print("Data in bytes: ");
		printBytes(data);
		System.out.println();
		
		return data;
	}
	
	/*
	 * Parses the byte array so that it can show the first two bits (01, 02 or 03)
	 * then formats a new string based off of the other bits.
	 */
	private static String bytesToString(byte[] b) {
		String s = "";
		s += new String(b, 0, b.length);
		return s;
	}

	/*
	 * Helper method to print arrays of bytes.
	 * Caps long strings to 80chars to make 100 byte arrays easier to read.
	 */
	private static void printBytes(byte[] bytes) {
		String s = "";
		if (bytes.length <= 5) {
			for (int i = 0; i < bytes.length - 1; i++) {
				s += bytes[i] + ", ";
			}
			System.out.println( s);
			return;
		}
		for (int i = 0; i < bytes.length - 1; i++) {
			if (bytes[1] == 4) {
				s += bytes[0] + ", " + bytes[1] + ", " + bytes[2] + ", " + bytes[3];
				System.out.println( s);
				return;
			}
			if (bytes[i] == 0 && bytes[i + 1] == 0) {
				break;
			}
			s += bytes[i] + ", ";
		}
		System.out.println(s);
	}
	
}
