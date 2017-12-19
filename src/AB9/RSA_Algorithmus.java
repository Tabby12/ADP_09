package AB9;

import java.math.BigInteger;
import java.util.Random;

/**
 * Implementierung des RSA-Verschluesselungsverfahren(Rivest, Shamir und Adleman).
 */
public class RSA_Algorithmus {
	
	/**
	 * Erste zuf�llig erzeugte Primzahl.
	 */
	private BigInteger prim1;
	

	/**
	 * Zweite zuf�llig erzeugte Primzahl.
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
	 * �ffentlicher Schl�ssel.
	 */
	private BigInteger publicKey;
	
	/**
	 * Privater Schl�ssel.
	 */
	private BigInteger privateKey;

	public String ErzeugeKeys(int bytelength)
	{
		//Zuf�llige primzahlen erzeugen
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
	public void erzeugeRSAmitPubKey(String yourPubKey){ 
		byte[] pubkeyall = Hybride_Verschluesselung.base64Decode(yourPubKey);
		byte[] pubkeykey = new byte[8];
		byte[] pubkeymod = new byte[16];
		
		if ((pubkeyall.length < 24) && (pubkeyall.length >= 8)) {
			java.util.Arrays.fill(pubkeymod, (byte)57);
			System.arraycopy(pubkeyall, 0, pubkeykey, 0, 8);
			publicKey = Hybride_Verschluesselung.Byte2BigInt(pubkeykey);
			modulusN = BigInteger.ZERO;
			
		}
		if (pubkeyall.length < 8) {
			publicKey = BigInteger.ZERO;
			modulusN = BigInteger.ZERO;
			
		}
		if (pubkeyall.length > 24) {
			System.arraycopy(pubkeyall, 0, pubkeykey, 0, 8);
			System.arraycopy(pubkeyall, 8, pubkeymod, 0, 16);
			publicKey = Hybride_Verschluesselung.Byte2BigInt(pubkeykey);
			modulusN = Hybride_Verschluesselung.Byte2BigInt(pubkeymod);
		}
	
		System.arraycopy(pubkeyall, 0, pubkeykey, 0, 8);
		System.arraycopy(pubkeyall, 8, pubkeymod, 0, 16);
		publicKey = Hybride_Verschluesselung.Byte2BigInt(pubkeykey);
		modulusN = Hybride_Verschluesselung.Byte2BigInt(pubkeymod);
	}
	
		public BigInteger encipherRSA(BigInteger in) {
		return in.modPow(publicKey, modulusN);
		}
	
		public BigInteger decipherRSA(BigInteger in){
			System.out.println("Private Key: " + privateKey + "N: " + modulusN);
			return in.modPow(privateKey, modulusN);
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
	  

}
