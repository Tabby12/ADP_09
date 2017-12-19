package AB9;

import java.math.BigInteger;

public class Hybride_Verschluesselung {

	static String base64Encode(byte[] bytes) {
		return java.util.Base64.getEncoder().encodeToString(bytes);
	}
	
	static byte[] BigInt2Byte(BigInteger src, int bytesize) {
		byte[] out = new byte[bytesize];
		BigInteger mod = new BigInteger("2");
		mod = mod.pow(bytesize * 8);
		src = src.mod(mod);
		int startdst = bytesize - src.toByteArray().length;
		int cpylength = src.toByteArray().length;
		if (src.bitLength() % 8 != 0) {
			System.arraycopy(src.toByteArray(), 0, out, startdst, cpylength);
		}
		else {
			System.arraycopy(src.toByteArray(), 1, out, startdst + 1, cpylength - 1);
		}
			return out;
	}
	
	static BigInteger Byte2BigInt(byte[] b) {
		return new BigInteger(1, b);
	}

}
