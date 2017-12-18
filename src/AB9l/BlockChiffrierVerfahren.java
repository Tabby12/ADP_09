package AB9l;

public class BlockChiffrierVerfahren {

	/**
	 * Verschluesselt einen String mit Symmetrischen Verfahren in ein Int[].
	 * 
	 * @param input
	 *            String welcher verschluesslt werden soll.
	 * @return verschluesseltes Int[]
	 */
	public static int[] verschluesseln(String input) {
		char[] charClearArray = input.toCharArray();
		int[] intClearArray = new int[charClearArray.length];

		for (int i = 0; i < charClearArray.length; i++) {
			intClearArray[i] = (int) charClearArray[i] - 32;
		}
		int s0, s1;
		s0 = (int) ((Math.random() * 94) + 1);
		s1 = (int) ((Math.random() * 94) + 1);
		int[] intKryptArray = new int[charClearArray.length + 8];
		intKryptArray[0] = s0;
		intKryptArray[1] = s1;

		for (int i = 0; i < charClearArray.length; i++) {
			if (i % 2 == 0) {
				intKryptArray[i + 8] = (intClearArray[i] + s0) % 95;
			} else {
				intKryptArray[i + 8] = (intClearArray[i] + s1) % 95;
			}
		}
		return intKryptArray;
	}

	/**
	 * Entschluesselt ein Int[] mit Symmetrischen Verfahren in ein String.
	 * 
	 * @param intKryptArray
	 *            Int[] welches entschluesslt werden soll.
	 * @return entschluesselten String
	 */
	public static String entschluesseln(int[] intKryptArray) {
		int s0, s1;
		s0 = intKryptArray[0];
		s1 = intKryptArray[1];
		int[] intClearArray = new int[(intKryptArray.length - 8)];
		char[] charClearArray = new char[intClearArray.length];
		for (int i = 0; i < intClearArray.length; i++) {
			if (i % 2 == 0) {
				intClearArray[i] = (intKryptArray[i + 8] + (95 - s0)) % 95;
			} else {
				intClearArray[i] = (intKryptArray[i + 8] + (95 - s1)) % 95;
			}
		}
		for (int i = 0; i < intClearArray.length; i++) {
			charClearArray[i] = (char) (intClearArray[i] + 32);
		}
		return String.valueOf(charClearArray);
	}
	
	 public static void main(String[] args) {
	 String text = "abcdefghijklmnopqrstuvwxyz, ABCDEFGHIJKLMNOPQRSTUVXYZ. 1234567890!";
	 System.out.println("zuverschlüssener Text: " + text);
	 String a = entschluesseln((verschluesseln(text)));
	 System.out.println("entschlüselter Text:   " + a);
	 }
}
