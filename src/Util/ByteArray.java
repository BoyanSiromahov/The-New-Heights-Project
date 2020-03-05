package Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * This Class contains method that can be used to manipulate arrays.
 * @author Samantha Tripp
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
		return bytesToString(message);
	
	}
	

	/*
	 * Parses the byte array so that it can show the first two bits (01, 02 or 03)
	 * then formats a new string based off of the other bits.
	 */
	public static String bytesToString(byte[] b) {
		String s = "";
		s += new String(b, 0, b.length);
		return s;
	}
	

	/*
	 * Helper method to print arrays of bytes.
	 * Caps long strings to 80chars to make 100 byte arrays easier to read.
	 */
	public static void printBytes(byte[] bytes) {
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
