package one.neurotrace.cipherlink;

import java.security.KeyPair;

import javax.crypto.SecretKey;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		// Each communication partner creates their own ECDH key pair
		KeyPair aliceKeys = ECDHKeyExchange.generateECDHKeyPair();
		KeyPair bobKeys = ECDHKeyExchange.generateECDHKeyPair();
		
		// Alice derives the shared secret using Bobs public key
		SecretKey aliceSharedSecret = ECDHKeyExchange.deriveSharedSecret(aliceKeys.getPrivate(), bobKeys.getPublic());
		
		// Bob derives the shared secret using Alices public key
		SecretKey bobSharedSecret = ECDHKeyExchange.deriveSharedSecret(bobKeys.getPrivate(), aliceKeys.getPublic());
		
		// Check if both secrets are identical
		System.out.println("Secret comparison:");
		System.out.println("Alice: " + bytesToHex(aliceSharedSecret.getEncoded()));
		System.out.println("Bob:   " + bytesToHex(bobSharedSecret.getEncoded()));
		
		// Alice encrypts message
		String message = "Hi Bob, here is Alice!";
		String encrypted = SecureCommunication.encrypt(message, aliceSharedSecret);
		System.out.println("\nEncrpted message (Alice): " + encrypted);
		
		// Bob decrypts message
		String decrypted = SecureCommunication.decrypt(encrypted, bobSharedSecret);
		System.out.println("Decrypted message (Bob): " + decrypted);
	}
	
	private static String bytesToHex(byte[] bytes) {
		
		StringBuilder sb = new StringBuilder();
		
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		
		return sb.toString();
	}
	
}
