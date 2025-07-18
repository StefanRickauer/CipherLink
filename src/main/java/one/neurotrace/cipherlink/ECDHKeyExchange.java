package one.neurotrace.cipherlink;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ECDHKeyExchange {
	
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static KeyPair generateECDHKeyPair() throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "BC");
		ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
		keyGen.initialize(ecSpec, new SecureRandom());
		
		return keyGen.generateKeyPair();
	}
	
	public static SecretKey deriveSharedSecret(PrivateKey privateKey, PublicKey publicKey) throws Exception {
		KeyAgreement ka = KeyAgreement.getInstance("ECDH", "BC");
		ka.init(privateKey);
		ka.doPhase(publicKey, true);
		byte[] sharedSecret = ka.generateSecret();
		
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte[] hashedSecret = sha256.digest(sharedSecret);
		
		return new SecretKeySpec(hashedSecret, 0, 16, "AES");
	}
	
	public static String keyToBase64(PublicKey key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
}
