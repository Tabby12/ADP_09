package AB9l;

import java.math.BigInteger;

/**
 * Created by Martin Witte, Hauke Goldhammer und Niels Eggebrecht on 11.06.2017.
 *
 * In dieser Klasse ist das RSA-Verschlüsselungsverfahren(Rivest, Shamir und Adleman) implementiert.
 *
 */
public class RSA_Algorithm {

    /**
     * Array mit möglichen Primzahlen
     */
    private final int[] primArray = {101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179,
            181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293,
            307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431,
            433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499};
        /*
        ,503,509,521,523,541,547,557,563,569,
        571,577,587,593,599,601,607,613,617,619,631,641,643,647,653,659,661,673,677,683,691,
        701,709,719,727,733,739,743,751,757,761,769,773,787,797,809,811,821,823,827,829,839,
        853,857,859,863,877,881,883,887,907,911,919,929,937,941,947,953,967,971,977,983,991,997};
        */

    private int prim1;

    private int prim2;

    private int N; // RSA_Modulodivisor

    private int phiVonN; // Ergebnis der Eulerschen Phi-Funktion

    private int e;  // public Key

    private int d;  // Private Key

    public RSA_Algorithm() {
        // Primzahlen zufaellig auswaehlen
        prim1 = primArray[(int) (Math.random() * primArray.length)];
        prim2 = primArray[(int) (Math.random() * primArray.length)];
        // Zweite Primzahl aendern, wenn sie = der Ersten ist.
        while (prim1 == prim2) {
            prim2 = primArray[(int) (Math.random() * primArray.length)];
        }

        // RSA_Modulodivisor errechnen
        N = prim1 * prim2;

        // phiVonN (Teilerfremde Zahl)
        phiVonN = (prim1-1) * (prim2-1);

        // e(Public key) suchen (1 < e < phiVonN und ggT(e, phiVonN) = 1)
        int temp = (int) (Math.random() * (phiVonN / 2));

        // solange suchen, bis ggT = 1 ist.
        while (BigInteger.valueOf(temp).gcd(BigInteger.valueOf(phiVonN)).intValue() != 1) {
            temp++;
        }
        e = temp;
        if(e >= phiVonN-1){
            throw new IllegalArgumentException("e konnte nicht gefunden werden!");
        }

        // d suchen (Private key { d*e = 1 mod phiVonN})
        d = 2;
        int modErg;
        do {
            d++;
            // (e*d)%phiVonN
            modErg = BigInteger.valueOf(e).multiply(BigInteger.valueOf(d)).mod(BigInteger.valueOf(phiVonN)).intValue();
        } while (modErg != 1);
    }

    // Euklidscher Algorithmus zum finden des groessten, gemeinsamen Teilers
    private int ggt(int z1, int z2) {
        // groessere Zahl in Z1
        if (z1 == z2) {
            return -1;
        } else if (z1 > z2) {
            int temp = z1;
            z1 = z2;
            z2 = temp;
        }
        // z2 ist jetzt die groessere Zahl
        int restNeu = z1;
        int restAlt = z1;

        // so lange Teilen, bis der Rest 0 ist.
        do {
            restAlt = restNeu;
            restNeu = z2 % z1;
            z2 = restAlt;
            z1 = restNeu;
        } while (restNeu != 0);
        return restAlt;
    }

    public int[] chiffrieren(int[] massage) {
        int[] publicKey = {e,N};
        return this.chiffrieren(massage, publicKey);
    }

    public int[] chiffrieren(int massage, int[] publicKey) {

        return this.chiffrieren(new int[] {massage}, publicKey);
    }

    /**
     *
     * @param massage
     * @param publicKey [0] public Key, [1] RSA_Modul
     * @return verschlüsselte massage
     */
    public int[] chiffrieren(int[] massage, int[] publicKey){
        int[] ergArray = new int[massage.length];
        BigInteger exponentBI = BigInteger.valueOf(publicKey[0]);
        BigInteger RSA_moduloBI = BigInteger.valueOf(publicKey[1]);
        for (int i = 0;i < massage.length; i++) {
            BigInteger massageBI = BigInteger.valueOf(massage[i]);
            // ((massage)^exponent) % RSA_modulo
            ergArray[i] = massageBI.modPow(exponentBI, RSA_moduloBI).intValue();
        }
        return ergArray;
    }

    /**
     * @param intArray cheffrierte Nachricht
     * @return  intArray decheffriert
     */
    public int[] deChiffrieren(int[] intArray){

        return this.deChiffrieren(intArray, d);
    }

    /**
     * @param intArray cheffrierte Nachricht
     * @param privateKey wenn vorhanden
     * @return intArray decheffriert
     */
    public int[] deChiffrieren(int[] intArray, int privateKey){
        int[] ergArray = new int[intArray.length];
        BigInteger exponentBI = BigInteger.valueOf(privateKey);
        BigInteger RSA_moduloBI = BigInteger.valueOf(N);
        for (int i = 0; i < intArray.length; i++) {
            BigInteger massageBI = BigInteger.valueOf(intArray[i]);
            // ((massage)^exponent) % RSA_modulo
            ergArray[i] = massageBI.modPow(exponentBI, RSA_moduloBI).intValue();
        }
        return ergArray;
    }

    /**
     * @return [0] public Key, [1] RSA_Modul
     */
    public int[] getPublicKey(){
        int[] erg = new int[2];
        erg[0] = e;
        erg[1] = N;
        return erg;
    }

    public void generateNewKeys(){
        prim1 = primArray[(int) (Math.random() * primArray.length)];
        prim2 = primArray[(int) (Math.random() * primArray.length)];
        // Zweite Primzahl ändern, wenn sie = der ersten ist.
        while (prim1 == prim2) {
            prim2 = primArray[(int) (Math.random() * primArray.length)];
        }
        N = prim1 * prim2;
        phiVonN = (prim1-1) * (prim2-1);
        int temp = (int) (Math.random() * phiVonN);
        while (this.ggt(phiVonN, temp) != 1) {
            temp++;
        }
        e = temp;
        d = 0;
        int modErg;
        do{
            d++;
            modErg = (d*e)%phiVonN;
        } while (modErg != 1);
    }

    public void setKeys(int privateKey, int publicKey){
        d = privateKey;
        e = publicKey;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            int massage0 = 42;
            int massage1 = 230;
            RSA_Algorithm rsa = new RSA_Algorithm();
            int[] publicKey = rsa.getPublicKey();
            System.out.println(publicKey[0]);
            System.out.println(publicKey[1]);
            int[] chiffrierteMassage = rsa.chiffrieren(massage0, publicKey);
            int[] dechiffrierteMassage = rsa.deChiffrieren(chiffrierteMassage);
            if (dechiffrierteMassage[0] == massage0) {
                System.out.println(String.format("Yeah!!!!! %nMassage: %d%nCheffriert: %d%nDechiffriert: %d", massage0, chiffrierteMassage[0], dechiffrierteMassage[0]));
            } else {
                System.out.println(String.format("Noooooo!!!!! %nMassage: %d%nCheffriert: %d%nDechiffriert: %d", massage0, chiffrierteMassage[0], dechiffrierteMassage[0]));
            }
        }
    }
}
