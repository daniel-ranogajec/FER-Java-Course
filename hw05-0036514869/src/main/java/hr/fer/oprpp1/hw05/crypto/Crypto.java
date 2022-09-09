package hr.fer.oprpp1.hw05.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program that allows user to encrypt/decrypt given file using the AES crypto-algorithm 
 * and the 128-bit encryption key or calculate and check the SHA-256 file digest.
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Crypto {
	
	public static void main(String[] args) {	
		if (args.length < 2 || args.length > 3) {
			System.out.println("Please enter correct arguments!");
			return;
		}
		
		try (Scanner sc = new Scanner(System.in)) {		
			if (args[0].equalsIgnoreCase("checksha")) {
				if (!(args[1].endsWith(".bin") && args.length == 2)) {
					System.out.println("Please enter correct arguments!");
					return;
				}
				
				System.out.println("Please provide expected sha-256 digest for " + args[1] + ":");
				System.out.print("> ");
				String s = sc.nextLine();
				
				try {
					checksha(Paths.get(args[1]),s);
				} catch (IOException ex) {
					System.out.println(ex);
				}
				
			} else if (args.length == 3) {
				System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
				System.out.print("> ");
				String keyText = sc.nextLine();
				System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
				System.out.print("> ");
				String ivText = sc.nextLine();
								
				try {
					encrypt(keyText, ivText, args[0], Paths.get(args[1]), Paths.get(args[2]));
				} catch (IllegalBlockSizeException | BadPaddingException | IOException ex) {
					System.out.println(ex);
				}
				
			} else {
				System.out.println("Please enter correct arguments!");
				return;
			}
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | 
				InvalidKeyException | InvalidAlgorithmParameterException ex) {
			System.out.println(ex);
		}
	}

	/**
	 * Private method used for encrypting/decrypting a file
	 * @param keyText password as hex-encoded text
	 * @param ivText initialization vector as hex-encoded text
	 * @param s expected sha-256 digest
	 * @param path file used for reading
	 * @param pathWrite file for writing
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private static void encrypt(String keyText, String ivText, String s, Path path, Path pathWrite) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		Boolean encrypt;
		if (s.equalsIgnoreCase("encrypt"))
			encrypt = true;
		else if (s.equalsIgnoreCase("decrypt"))
			encrypt = false;
		else {
			System.out.println("Please enter correct arguments!");
			return;
		}
		
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);		
		
		try (InputStream in = Files.newInputStream(path);
			OutputStream os = Files.newOutputStream(pathWrite)) {
			byte[] b = new byte[4096];
			
			int k;
			while (true) {
				k = in.read(b);
				if (k == -1) {
					os.write(cipher.doFinal());
					break;
				}
				os.write(cipher.update(b, 0, k));
			}
		}
		
		System.out.println(encrypt ? "Encryption " : "Decryption " + "completed. Generated file " + pathWrite + " based on file " + path + ".");
		
	}

	/**
	 * Method used for calculating digest
	 * @param path file that you want to digest
	 * @param s expected sha-256 digest
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	private static void checksha(Path path, String s) throws IOException, NoSuchAlgorithmException {
		try (InputStream in = Files.newInputStream(path)) {
		MessageDigest md = MessageDigest.getInstance("sha256");
		byte[] b = new byte[4096];
		
		int k;
		while (true) {
			k = in.read(b);
			if (k == -1) break;
			md.update(b, 0, k);
		}
		
		byte[] bytearray = md.digest();
		String digest = Util.bytetohex(bytearray);
		
		if (digest.equals(s)) 
			System.out.println("Digesting completed. Digest of "+ path.toString() + " matches expected digest.");
		else 
			System.out.println("Digesting completed. Digest of " + path.toString() + " does not match the expected digest. "
					+ "Digest was: " + digest);	
		} 
	}
}
