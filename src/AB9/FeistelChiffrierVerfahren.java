package AB9;

public class FeistelChiffrierVerfahren {
	
	public byte[] left;
	
	public byte[] right;

		    public FeistelChiffrierVerfahren(byte[] b, int start) { 
		      left = java.util.Arrays.copyOfRange(b, start, start + 8);
		      right = java.util.Arrays.copyOfRange(b, start + 8, start + 16);
		    }
		    
		    public FeistelChiffrierVerfahren() {
		    	left = new byte[8];
				right = new byte[8];
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
}
