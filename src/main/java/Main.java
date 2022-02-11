import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.management.openmbean.InvalidKeyException;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

import jdk.internal.util.xml.impl.Input;

public class Main {

	
	static SecretKey mainKey;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		
		copyFilesOver("Input.txt","Output.txt");
		System.out.println("Enter a String to encrypt");
		String input = scanner.nextLine();
		encrypter(input, "EncryptedUserInfo.txt");
		System.out.println("Decrypting String: ");
		decrypter("EncryptedUserInfo.txt");
		
	}
	
	public static void copyFilesOver(String inputFile, String outputFile)
	{
		try
		{
			FileInputStream reader = new FileInputStream(inputFile);
			FileOutputStream writer = new FileOutputStream(outputFile);
			int next = reader.read();
			while(next != -1)
			{
				writer.write(next);
				next = reader.read();
			}
			writer.flush();
			writer.close();
			reader.close();
			
		}
		catch(FileNotFoundException fne)
		{
			fne.printStackTrace();
			System.out.print("NotFound");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("IO");
		}
		
		
	}
	public static  void encrypter(String toEncrypte, String outputFile)
	{
		try {
			mainKey = KeyGenerator.getInstance("AES").generateKey();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // not likely to use this specific encryption instance/type in practice
			cipher.init(Cipher.ENCRYPT_MODE, mainKey);
			FileOutputStream writer = new FileOutputStream(outputFile);
			CipherOutputStream cWriter = new CipherOutputStream(writer,cipher);
			cWriter.write(cipher.getIV());
			cWriter.write(toEncrypte.getBytes());
			cWriter.flush();
			cWriter.close();
			writer.close();
			System.out.println("String Encrypted to file");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (java.security.InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void decrypter(String inputFile)
	{
		try {
			FileInputStream input = new FileInputStream(inputFile);
			byte[] byteInput = new byte[16];
			input.read(byteInput);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // not likely to use this specific encryption instance/type in practice
			cipher.init(Cipher.DECRYPT_MODE, mainKey,new IvParameterSpec(byteInput));
			CipherInputStream cInput = new CipherInputStream(input, cipher);
			InputStreamReader iReader = new InputStreamReader(cInput);
			BufferedReader reader = new BufferedReader(iReader);
			
			
			String next;
			while((next = reader.readLine()) != null)
			{
				System.out.print(next);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.security.InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
