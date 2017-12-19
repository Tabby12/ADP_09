package AB9;

import java.math.BigInteger;



public class Hybride_Verschluesselung {
	
	byte[] sessionkey;
	
	byte[] verschluesselterSessionkey;
	
	public String verschluesseln(String s, RSA_Algorithmus yourRSA) {
	    byte[] in = paddingRechts(s, 16);
	    byte[] out = new byte[in.length + 16];
	    
	    sessionkey = erzeugeSymKey(8);
	    
	    for (int i = 0; i < in.length; i += 16) {
	      byte[] block = blockencrypt(in, i, sessionkey);
	      System.arraycopy(block, 0, out, i + 16, 16);
	    }
	    
	    verschluesselterSessionkey = BigInt2Byte(yourRSA.encipherRSA(Byte2BigInt(sessionkey)), 16);
	    
	    System.arraycopy(verschluesselterSessionkey, 0, out, 0, 16);
	    return base64Encode(out);
	}
	
	
	
	private byte[] paddingRechts(String s, int blocksize) {
		int padsize = s.length() + blocksize - s.length() % blocksize;
	    byte[] out = new byte[padsize];
	    for (int i = 0; i < padsize; i++) {
	    	if (i < s.length()) out[i] = s.getBytes()[i]; else
	    		out[i] = 32;
	    }
	    return out;
	}
	
	private byte[] blockencrypt(byte[] b, int start, byte[] key){
		FeistelChiffrierVerfahren ftmp = new FeistelChiffrierVerfahren(b, start);
	    for (int i = 0; i < 12; i++) {
	      ftmp.feistelRound(key);
	    }
	    return ftmp.getBlock();
	}
	
	private byte[] blockdecrypt(byte[] b, int start, byte[] key) {
		    FeistelChiffrierVerfahren ftmp = new FeistelChiffrierVerfahren(b, start);
		    ftmp.swap();
		    for (int i = 0; i < 12; i++) {
		      ftmp.feistelRound(key);
		    }
		    ftmp.swap();
		    return ftmp.getBlock();
	}
	
	private byte[] erzeugeSymKey(int size) {
		byte[] out = new byte[size];
		for (int i = 0; i < size; i++) {
			out[i] = ((byte)randInt(0, 255));
		}
			return out;
	}
	
	private static int randInt(int min, int max) {
		java.util.Random rand = new java.util.Random();
		return rand.nextInt(max - min + 1) + min;
	}
	
	static String base64Encode(byte[] bytes) {
		return java.util.Base64.getEncoder().encodeToString(bytes);
	}
	
	static byte[] base64Decode(String s) {
	    return java.util.Base64.getDecoder().decode(s);
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



	public String entschluesseln(String s, RSA_Algorithmus yourRSA) {
		byte[] in = base64Decode(s);
		  if (in.length <= 16) {
		  }
		  byte[] out = new byte[in.length - 16];
		  
		  verschluesselterSessionkey = new byte[16];
		  System.arraycopy(in, 0, verschluesselterSessionkey, 0, 16);
		 
		  sessionkey = BigInt2Byte(yourRSA.decipherRSA(Byte2BigInt(verschluesselterSessionkey)), 8);
	
		  for (int i = 16; i < in.length; i += 16) {
			  if (i + 16 > in.length) {
			  }
			  byte[] block = blockdecrypt(in, i, sessionkey);
			  System.arraycopy(block, 0, out, i - 16, 16);
		  }
		  return new String(out);
	}

}
