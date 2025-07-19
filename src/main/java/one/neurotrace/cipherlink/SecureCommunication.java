package one.neurotrace.cipherlink;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class SecureCommunication {
	
	private static final int IV_LENGTH = 12;
	private static final int TAG_LENGTH = 128;
	
	public static String encrypt(String plaintext, SecretKey key) throws Exception {
		byte[] iv = new byte[IV_LENGTH];
		new SecureRandom().nextBytes(iv);
		
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
		cipher.init(Cipher.ENCRYPT_MODE, key, spec);
		byte[] ciphertext = cipher.doFinal(plaintext.getBytes());
		
		byte[] combined = new byte[iv.length + ciphertext.length];
		System.arraycopy(iv, 0, combined, 0, iv.length);
		System.arraycopy(ciphertext, 0, combined, iv.length, ciphertext.length);
		
		return Base64.getEncoder().encodeToString(combined);
	}
	
	public static String decrypt(String encryptedBase64, SecretKey key) throws Exception {
		byte[] combined = Base64.getDecoder().decode(encryptedBase64);
		byte[] iv = new byte[IV_LENGTH];
		byte[] ciphertext = new byte[combined.length - IV_LENGTH];
		
		System.arraycopy(combined, 0, iv, 0, IV_LENGTH);
		System.arraycopy(combined, IV_LENGTH, ciphertext, 0, ciphertext.length);
		
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
		cipher.init(Cipher.DECRYPT_MODE, key, spec);
		byte[] plaintext = cipher.doFinal(ciphertext);
		
		return new String(plaintext);
	}
}
