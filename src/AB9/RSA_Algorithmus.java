package AB9;

import java.math.BigInteger;
import java.util.Random;

/**
 * Implementierung des RSA-Verschluesselungsverfahren(Rivest, Shamir und Adleman).
 */
public class RSA_Algorithmus {
	
	private BigInteger p1;
	
	private BigInteger p2;
	
	private BigInteger modulusN;
	
	private BigInteger phiVonN;
	
	private BigInteger e;  // public Key
	
	private BigInteger d;  // Private Key
	
	
	public RSA_Algorithmus() {
		
		//Zufällige primzahlen erzeugen
		p1 = BigInteger.probablePrime(64, new Random());
		p2 = BigInteger.probablePrime(64, new Random());
		
		// Zweite Primzahl aendern, wenn sie = der Ersten ist.
		while (p1 == p2) {
            p2 = BigInteger.probablePrime(64, new Random());
        }
		
		// RSA_Modulodivisor errechnen
		modulusN = p1.multiply(p2);
		
		// phiVonN (Teilerfremde Zahl)
		phiVonN = p1.subtract(BigInteger.valueOf(1).multiply(p2.subtract(BigInteger.valueOf(1))));
		
		// e(Public key) suchen (1 < e < phiVonN und ggT(e, phiVonN) = 1)
        BigInteger temp = BigInteger.probablePrime(64, new Random());

        // solange suchen, bis ggT = 1 ist.
        while (temp.gcd(phiVonN) != BigInteger.valueOf(1)) {
            temp.add(BigInteger.valueOf(1));
        }
        e = temp;
        if(e.compareTo(phiVonN.subtract(BigInteger.valueOf(1))) == 0||e.compareTo(phiVonN.subtract(BigInteger.valueOf(1))) ==1){
            throw new IllegalArgumentException("e konnte nicht gefunden werden!");
        }
        d = BigInteger.valueOf(2);
        BigInteger modErg;
        do {
            d.add(BigInteger.valueOf(1));
            // (e*d)%phiVonN
            modErg = e.multiply(d).mod(phiVonN);
        } while (modErg != BigInteger.valueOf(1));
		
	}
	
	public static void main(String[] args) {
		
	}
	
	

}
