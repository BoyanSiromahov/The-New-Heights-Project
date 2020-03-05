package Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This Class contains method that can be used to manipulate arrays.
 * @author Sam Trip
 * @author BritneyBaker
 *
 */
public class ByteArray {
	
	public ByteArray() {}
	
	/**
	 * Helper method to convert CallEvent objects to byte array messages for
	 * use in constructing DatagramPackets.
	 * 
	 * @param floorEvent
	 */
	public byte[] createMessage(CallEvent floorEvent) {
		
		byte[] sendData;
		
		// Prepare byte array of data to send
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			
			
			// Convert floor event to bytes
			byteStream.write(floorEvent.toString().getBytes());
			
			// Create byte array
			sendData = byteStream.toByteArray();
			byteStream.close();
			
			return sendData;
		
		} catch (IOException e) {
			System.out.println("Create Message Error: ");
			e.printStackTrace();
			System.exit(1);
		}
		
		
		return null;
	}
	
	/**
	 * Private method to decode byte array received in DatagramPacket to
	 * a CallEvent object.
	 * 
	 * @param message
	 * @return CallEvent
	 */
	public synchronized String decodeMessage(byte[] message) {
		return printBytes(message);
	
	}
	
	/*
	 * Helper method to print arrays of bytes.
	 * Caps long strings to 80chars to make 100 byte arrays easier to read.
	 */
	private static String printBytes(byte[] b) {
		String s = "";
		s += new String(b, 0, b.length);
		return s;
	}
}
