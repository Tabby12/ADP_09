package AB9;

import java.math.BigInteger;

public class FeistelChiffrierVerfahren {
	
	public byte[] left;
	
	public byte[] right;

		    public FeistelChiffrierVerfahren(byte[] b, int start) {
		    	left = new byte[8];
		    	right = new byte[8];
		    	System.arraycopy(b, start, left, 0, 8);
		    	System.arraycopy(b, start + 8, right, 0, 8);
		    }
		        
//		    public void print()
//		    {
//		      System.out.print(HAWcipher.base64Encode(left) + " ");
//		      System.out.println(HAWcipher.base64Encode(right));
//		      System.out.println(java.util.Arrays.toString(left) + " " + java.util.Arrays.toString(right));
//		      String tmpleft = new String(left);
//		      String tmpright = new String(right);
//		      System.out.println(tmpleft + " " + tmpright);
//		      System.out.println("--------------------------------");
//		    }
		    
		    public FeistelChiffrierVerfahren swap() {
		      byte[] tmp = left;
		      left = right;
		      right = tmp;
		      return this;
		    }
		    
		    public byte[] toByte() {
		      byte[] out = new byte[16];
		      System.arraycopy(left, 0, out, 0, 8);
		      System.arraycopy(right, 0, out, 8, 8);
		      return out;
		    }
		    
		    public void feistelRound(byte[] key) {
		    	byte[] temp = right;
		    	right = xorWithKey(left, F(right, key));
		        left = temp;
		    }
		    
		    public byte[] getBlock() {
		    	byte[] res = new byte[16];
		    	System.arraycopy(left, 0, res, 0, 8);
		    	System.arraycopy(right, 0, res, 8, 8);
		    	return res;
		    }
		    
		    private byte[] xorWithKey(byte[] a, byte[] key)
		    {
		      byte[] out = new byte[a.length];
		      for (int i = 0; i < a.length; i++) {
		        out[i] = ((byte)(a[i] ^ key[(i % key.length)]));
		      }
		      return out;
		    }
		    
		    private byte[] F(byte[] R, byte[] key)
		    {
		      BigInteger mod = new BigInteger("2");
		      mod = mod.pow(64);
		      mod = mod.subtract(BigInteger.ONE);
		      BigInteger Rint = Hybride_Verschluesselung.Byte2BigInt(R);
		      BigInteger Kint = Hybride_Verschluesselung.Byte2BigInt(key);
		      
		      BigInteger result = Rint.multiply(Rint).mod(mod);
		      result = result.add(Kint).mod(mod);
		      return Hybride_Verschluesselung.BigInt2Byte(result, 8);
		    }
}
