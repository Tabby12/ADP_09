package AB9;

import java.math.BigInteger;
import java.util.Random;

/**
 * Implementierung des RSA-Verschluesselungsverfahren(Rivest, Shamir und Adleman).
 */
public class RSA_Algorithmus {
	
	/**
	 * Erste zufällig erzeugte Primzahl.
	 */
	private BigInteger prim1;
	

	/**
	 * Zweite zufällig erzeugte Primzahl.
	 */
	private BigInteger prim2;
	
	/**
	 * Hauptmodul.
	 */
	private BigInteger modulusN;
	
	/**
	 * Nebenmodul.
	 */
	private BigInteger phiVonN;
	
	/**
	 * Öffentlicher Schlüssel.
	 */
	private BigInteger publicKey;
	
	/**
	 * Privater Schlüssel.
	 */
	private BigInteger privateKey;

	public String ErzeugeKeys(int bytelength)
	{
		//Zufällige primzahlen erzeugen
		prim1 = BigInteger.probablePrime(64, new Random());
		prim2 = BigInteger.probablePrime(64, new Random());
		
		// Zweite Primzahl aendern, wenn sie = der Ersten ist.
		while (prim1 == prim2) {
			prim2 = BigInteger.probablePrime(64, new Random());
		}
		
		// RSA_Modulodivisor errechnen
		modulusN = prim1.multiply(prim2);
		
		// phiVonN (Teilerfremde Zahl)
		phiVonN = prim1.subtract(BigInteger.ONE).multiply(prim2.subtract(BigInteger.ONE));
	
		//public Key
		BigInteger key = BigInteger.probablePrime(64, new Random());
		while (!phiVonN.gcd(key).equals(BigInteger.ONE)) {
			key = key.add(BigInteger.ONE);
		}
		publicKey = key;
		
		//private Key
		privateKey = extEuklid(publicKey, phiVonN).s;
		
		byte[] outbyte = new byte[bytelength + bytelength / 2];
		byte[] keybyte = Hybride_Verschluesselung.BigInt2Byte(publicKey, bytelength / 2);
		byte[] modulusbyte = Hybride_Verschluesselung.BigInt2Byte(modulusN, bytelength);
		System.arraycopy(keybyte, 0, outbyte, 0, bytelength / 2);
		System.arraycopy(modulusbyte, 0, outbyte, bytelength / 2, bytelength);
		
		return Hybride_Verschluesselung.base64Encode(outbyte);
	}
	
	public static Triple extEuklid(BigInteger a, BigInteger b) {
		if (b.equals(BigInteger.ZERO)) {
			return new Triple(a, BigInteger.ONE, BigInteger.ZERO);
		}
		Triple extension = extEuklid(b, a.mod(b));
		return new Triple(extension.d, extension.t, extension.s.subtract(a.divide(b).multiply(extension.t)));
	}
		
	private static class Triple {
		public final BigInteger d;
		public final BigInteger s;
		public final BigInteger t;
		
		private Triple(BigInteger d, BigInteger s, BigInteger t) {
			this.d = d;this.s = s;this.t = t;
		}
	}
	
	/**
	 * Getter.
	 */
	public BigInteger getPrim1() {
		return prim1;
	}
	
	public BigInteger getPrim2() {
		return prim2;
	}
	
	public BigInteger getModulusN() {
		return modulusN;
	}
	
	public BigInteger getPhiVonN() {
		return phiVonN;
	}
	
	public BigInteger getPublicKey() {
		return publicKey;
	}
	
	public BigInteger getPrivateKey() {
		return privateKey;
	}
	
	
	
	  
//	  public void createRSAwithPubKey(String hispubkey) throws BoundException { byte[] pubkeyall = HAWcipher.base64Decode(hispubkey);
//	    byte[] pubkeykey = new byte[8];
//	    byte[] pubkeymod = new byte[16];
//	    if ((pubkeyall.length < 24) && (pubkeyall.length >= 8)) {
//	      java.util.Arrays.fill(pubkeymod, (byte)57);
//	      System.arraycopy(pubkeyall, 0, pubkeykey, 0, 8);
//	      publickey = HAWcipher.Byte2BigInt(pubkeykey);
//	      modulus = BigInteger.ZERO;
//	      throw new BoundException("Public Key has " + pubkeyall.length + " Bytes. Expected are 24 Bytes. The modulus will be set to zero.");
//	    }
//	    if (pubkeyall.length < 8) {
//	      publickey = BigInteger.ZERO;
//	      modulus = BigInteger.ZERO;
//	      throw new BoundException("Public Key has " + pubkeyall.length + " Bytes. Expected are 24 Bytes. Both key and modulus will be set to zero.");
//	    }
//	    if (pubkeyall.length > 24) {
//	      System.arraycopy(pubkeyall, 0, pubkeykey, 0, 8);
//	      System.arraycopy(pubkeyall, 8, pubkeymod, 0, 16);
//	      publickey = HAWcipher.Byte2BigInt(pubkeykey);
//	      modulus = HAWcipher.Byte2BigInt(pubkeymod);
//	      throw new BoundException("Public Key has " + pubkeyall.length + " Bytes. Expected are 24 Bytes. Key and modulus will be set using the first 24 Bytes.");
//	    }
//	    
//	    System.arraycopy(pubkeyall, 0, pubkeykey, 0, 8);
//	    System.arraycopy(pubkeyall, 8, pubkeymod, 0, 16);
//	    publickey = HAWcipher.Byte2BigInt(pubkeykey);
//	    modulus = HAWcipher.Byte2BigInt(pubkeymod);
//	  }
//	  
//	  public BigInteger encipherRSA(BigInteger in) {
//	    return in.modPow(publickey, modulus);
//	  }
//	  
//	  public BigInteger decipherRSA(BigInteger in) throws BoundException {
//	    if ((modulus == null) || (privatekey == null) || (modulus == BigInteger.ZERO)) {
//	      throw new BoundException("private key = " + privatekey + ", N = " + modulus + ". Expecting actual numbers.");
//	    }
//	    return in.modPow(privatekey, modulus);
//	  }

}
