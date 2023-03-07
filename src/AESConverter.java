/*
 * Name: Justin Ho
 * Date: 3/2/2022
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.*;

public class AESConverter {

	public static void main(String[] args) throws FileNotFoundException{
		initialize(); //Runs the program
	}

	/*
	 * Prompts the user for the path of a text file that they wish to encrypt and decrypt.
	 * Then, generates a random 256 bit key and performs the encryption and decryption.
	 * The results of both operations are stored in text files, ciphertext.txt and decryptedtext.txt.
	 */
	public static void initialize() throws FileNotFoundException {
		String fileLocation = ""; //Holds file path
		Scanner scan = new Scanner(System.in); //Used to scan user input for file path
		String text = ""; //Stores text from specified file.
		
		//Prompts user for file path and checks if file exists.
		System.out.print("Please enter the path to the file you wish to encrypt/decrypt: ");
		fileLocation = scan.next();
		File file = new File(fileLocation);
		while (!file.exists()) {
			System.out.println("The path does not exist!");
			System.out.print("Please enter the path to the file you wish to encrypt/decrypt: ");
			fileLocation = scan.next();
			file = new File(fileLocation);
		}
		
		//Reads file, stores content in string, and prints out the text
		Scanner fileScan = new Scanner(file);
		System.out.println("Reading file for plaintext...");
		while (fileScan.hasNextLine()) {
			text += fileScan.nextLine();
		}
		System.out.println("\nPlaintext: " + text);
		
		//Converts text to array of bytes to make AES process easier
		byte[] plaintext = text.getBytes();
		
		try {
			//Generates random 256 bit key for AES and prints it out for user reference
			System.out.println("\nStart generating AES key...");
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
	        SecureRandom secureRand = new SecureRandom();
	        int keyBitSize = 256;
	        keyGen.init(keyBitSize, secureRand);
	        SecretKey secretKey = keyGen.generateKey();
	        System.out.println("Finished generating random key!");
	        System.out.println("Key: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
			
	        //Encrypts the plaintext using AES with default mode and padding and writes the ciphertext
	        //to ciphertext.txt
			Cipher cipher = Cipher.getInstance("AES");
			System.out.println("\nStarting encryption process...");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] cipherText = cipher.doFinal(plaintext);
			System.out.println("Writing to ciphertext.txt...");
			FileOutputStream cipherTextFile = new FileOutputStream("ciphertext.txt");
			cipherTextFile.write(cipherText);
			cipherTextFile.close();
			System.out.println("Encryption complete!");
			
			//Reads and stores the contents of ciphertext.txt for the decryption process later on
			System.out.println("\nReading ciphertext.txt...");
			FileInputStream cipherTextInput = new FileInputStream("ciphertext.txt");
			cipherTextInput.read(cipherText);
			cipherTextInput.close();
			System.out.println("File read successfully!");
			
			//Decrypts the ciphertext and stores the plaintext in decryptedtext.txt
			System.out.println("\nStarting decryption process...");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] newPlainText = cipher.doFinal(cipherText);
			System.out.println("Writing to decryptedtext.txt...");
			FileOutputStream decryptedTextFile = new FileOutputStream("decryptedtext.txt");
			decryptedTextFile.write(newPlainText);
			decryptedTextFile.close();
			System.out.println("Decryption complete!");
			
		//Catches various exceptions and prints stack trace.
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			System.out.println("Invalid padding.");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("Invalid key.");
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			System.out.println("Invalid block size.");
			e.printStackTrace();
		} catch (BadPaddingException e) {
			System.out.println("Bad padding.");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Invalid input or output.");
			e.printStackTrace();
		}
		
		//Prevent resource leaks
		scan.close();
		fileScan.close();
	}
}
