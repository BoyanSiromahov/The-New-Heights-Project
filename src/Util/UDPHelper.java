package Util;

import java.io.IOException;
import java.net.*;

/**
 * 
 * @author Samantha Tripp
 *
 */
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
		printUDPData(sendPacket, false);
		
				
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
		printUDPData(receivePacket, true);
		return data;
	}
	
	/**
	 * 
	 * @param packet
	 * @param dataReceived
	 */
	private void printUDPData(DatagramPacket packet, boolean dataReceived) {
		if (dataReceived) { // Print statements for received DatagramPacket
			System.out.println("Port " + portNumber + ": packet received");
			System.out.println("From host: " + packet.getAddress());
			System.out.println("Host port: " + packet.getPort());
		} else {
			System.out.println("Port " + portNumber + ": sending packet");
			System.out.println("To host: " + packet.getAddress());
			System.out.println("Destination host port: " + packet.getPort());
			
		}
		
		System.out.print("Data as string: ");
		System.out.println(ByteArray.bytesToString(packet.getData()));
		System.out.print("Data in bytes: ");
		ByteArray.printBytes(packet.getData());
		System.out.println();
	}
}
