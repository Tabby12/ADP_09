package AB9l;

import java.util.Arrays;

/**
 * Chiffrierung und Dechiffrierung von Strings mit einer hybriden Chiffrierungsmethode.
 * Hier fuer wird der Text mit einem symmetrischen Verfahren chiffriert und die Keys von diesem Verfahren noch mit RSA cheffriert.
 *
 * Created by Hauke Goldhammer, Martin Witte, Nils Eggebrecht
 */
public class Hybrid_Verschluesselung {

    private static final int BASIS_95 = 95;

    private RSA_Algorithm rsa_algorithm;

    public Hybrid_Verschluesselung(){
        this.rsa_algorithm = new RSA_Algorithm();
    }

    public int[] getMyPublicKey(){
        return rsa_algorithm.getPublicKey();
    }

    /**
     * Ein übergebener Text wird mit einen publicKey und dem dazu gehoerigen RSA-Modul verschluesselt.
     *
     * @param text, zu verschluesselnder Text
     * @param publicKey, oeffentlicher Schluessel des Empfaengers
     * @param rsaModul, RSA-Modul des Empfaengers
     * @return verschluesselter Text
     */
    public String chiffrierenHybrid(String text, int publicKey, int rsaModul){
        int[] rsaArray = {publicKey, rsaModul};
        int[] intKryptArray = BlockChiffrierVerfahren.verschluesseln(text);
        int sessionKey0 = rsa_algorithm.chiffrieren(intKryptArray[0], rsaArray)[0];
        int sessionKey1 = rsa_algorithm.chiffrieren(intKryptArray[1], rsaArray)[0];
        int[] tempKeyArrayKey0 = splitIntBase95(sessionKey0);
        int[] tempKeyArrayKey1 = splitIntBase95(sessionKey1);
        for (int i = 0; i < 4 ; i++){
            intKryptArray[i] = tempKeyArrayKey0[i];
            intKryptArray[i+4] = tempKeyArrayKey1[i];
        }

        char[] charKryptArray = new char[intKryptArray.length];
        for (int i = 0; i < intKryptArray.length; i++) {
            charKryptArray[i] = (char) (intKryptArray[i] + 32);
        }
        return String.copyValueOf(charKryptArray);
    }

    /**
     *
     * @param text, zu entschluesselnder Text, verschluesselt mit "meinem" publicKey und dazu gehoerigenden RSA-Modul.
     * @return entschluesselter Text
     */
    public String dechiffrierenHybrid(String text){
        char[] charKryptArray = text.toCharArray();
        int[] intKryptArray = new int[charKryptArray.length];
        for (int i = 0; i < intKryptArray.length; i++) {
            intKryptArray[i] = (int) charKryptArray[i] - 32;
        }
        int sessionKey0 = deSplitIntBase95(intKryptArray[0], intKryptArray[1], intKryptArray[2], intKryptArray[3]);
        int sessionKey1 = deSplitIntBase95(intKryptArray[4], intKryptArray[5], intKryptArray[6], intKryptArray[7]);
        intKryptArray[0] = rsa_algorithm.deChiffrieren(new int[]{sessionKey0})[0];
        intKryptArray[1] = rsa_algorithm.deChiffrieren(new int[]{sessionKey1})[0];
        return BlockChiffrierVerfahren.entschluesseln(intKryptArray);
    }

    /**
     * Methode zum Aufteilen einer vierstelligen Zahl auf vier Integars zur Basis 95.
     * @param key, zu aufteilende Zahl
     * @return IntArray mit den vier Stellen der Zahl zur Basis 95.
     */
    private int[] splitIntBase95(int key){
        int temp[] = new int[4];
        temp[0] = key/ ((int) Math.pow(BASIS_95, 3));
        temp[1] = (key % ((int) Math.pow(BASIS_95, 3)))/ ((int) Math.pow(BASIS_95,2));
        temp[2] = (key % ((int) Math.pow(BASIS_95, 2)))/ BASIS_95;
        temp[3] = key % BASIS_95;
        return temp;
    }

    /**
     * Methode zum Zusammenfueren von vier Zahlen zu einer vierstelligen Zahl zur Basis 95.
     *
     * @param tausenderStelle, tausender Stelle der Zahl
     * @param hunderterStelle, hunderter Stelle der Zahl
     * @param zehnerStelle, zehner Stelle der Zahl
     * @param einerStelle, einer Stelle der Zahl
     * @return die zu einer Zahl zusammengefasste Zahl
     */
    private int deSplitIntBase95(int tausenderStelle, int hunderterStelle, int zehnerStelle, int einerStelle){
        return tausenderStelle * ((int) Math.pow(BASIS_95, 3)) + hunderterStelle * ((int) Math.pow(BASIS_95, 2)) + zehnerStelle * BASIS_95 + einerStelle;
    }

    public static void main(String[] args) {
        Hybrid_Verschluesselung hybridVerschluesselung = new Hybrid_Verschluesselung();
        int[] myKey = hybridVerschluesselung.getMyPublicKey();
        String m = "foo 1337";
        String crypted = hybridVerschluesselung.chiffrierenHybrid(m, myKey[0], myKey[1]);
        String decrypted = hybridVerschluesselung.dechiffrierenHybrid(crypted);
        if(!decrypted.equals(m)) {
            System.err.println("ERROR!");
        }
        System.out.println("Ausgangstext: " + m);
        System.out.println("Decrypted: " + decrypted);
        System.out.println("Crypted: " + crypted);
        System.out.println(Arrays.toString(crypted.chars().toArray()));
        for(int i : crypted.toCharArray()) {
            System.out.print(i+" ");
        }
        System.out.println();
        System.out.println(hybridVerschluesselung.chiffrierenHybrid("Moin Nils, hoffentlich kannst du das lesen.", 47429, 116003));

    }
}
