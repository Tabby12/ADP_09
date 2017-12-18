package pareigis;

import java.math.BigInteger;

public class HAWcipher {
  final int BLOCKSIZE = 16;
  final int ROUNDS = 12;

  
  public void printKeys()
  {
    System.out.println("session key = " + base64Encode(sessionkey));
    System.out.println("cipher session key = " + base64Encode(cipheredsessionkey));
  }
  

  public byte[] right;
  
  public byte[] left;

  final int OFFSET = 16;
  


  byte[] sessionkey;
  


  byte[] cipheredsessionkey;
  


  public String encrypt(String s, RSA rsa)
  {
    byte[] in = padRight(s, 16);
    byte[] out = new byte[in.length + 16];
    
    sessionkey = createSymKey(8);
    
    for (int i = 0; i < in.length; i += 16) {
      byte[] block = blockencrypt(in, i, sessionkey);
      System.arraycopy(block, 0, out, i + 16, 16);
    }
    cipheredsessionkey = BigInt2Byte(rsa.encipherRSA(Byte2BigInt(sessionkey)), 16);
    
    System.arraycopy(cipheredsessionkey, 0, out, 0, 16);
    return base64Encode(out);
  }
  










  private byte[] blockencrypt(byte[] b, int start, byte[] key)
  {
    FeistelBlock ftmp = new FeistelBlock(b, start);
    for (int i = 0; i < 12; i++) {
      ftmp = feistelRound(ftmp, key);
    }
    return ftmp.toByte();
  }
  
  private byte[] blockdecrypt(byte[] b, int start, byte[] key) {
    FeistelBlock ftmp = new FeistelBlock(b, start);
    ftmp.swap();
    for (int i = 0; i < 12; i++) {
      ftmp = feistelRound(ftmp, key);
    }
    ftmp.swap();
    return ftmp.toByte();
  }
  
  private byte[] xorWithKey(byte[] a, byte[] key)
  {
    byte[] out = new byte[a.length];
    for (int i = 0; i < a.length; i++) {
      out[i] = ((byte)(a[i] ^ key[(i % key.length)]));
    }
    return out;
  }
  






  public String decrypt(String s, RSA rsa)
    throws BoundException
  {
    byte[] in = base64Decode(s);
    if (in.length <= 16) {
      throw new BoundException("Cipher text has " + in.length + " Bytes. At least " + 16 + " Bytes expected.");
    }
    byte[] out = new byte[in.length - 16];
    

    cipheredsessionkey = new byte[16];
    System.arraycopy(in, 0, cipheredsessionkey, 0, 16);
    try {
      sessionkey = BigInt2Byte(rsa.decipherRSA(Byte2BigInt(cipheredsessionkey)), 8);
    } catch (BoundException e) {
      e.printStackTrace();
      throw e;
    }
    for (int i = 16; i < in.length; i += 16) {
      if (i + 16 > in.length) {
        throw new BoundException("Ciphertext has " + in.length + " Bytes. Expecting multiple of " + 16 + ".");
      }
      byte[] block = blockdecrypt(in, i, sessionkey);
      System.arraycopy(block, 0, out, i - 16, 16);
    }
    return new String(out);
  }
  
  private FeistelBlock feistelRound(FeistelBlock in, byte[] key) {
    FeistelBlock fout = new FeistelBlock();
    left = right;
    right = xorWithKey(left, F(right, key));
    return fout;
  }
  
  private byte[] F(byte[] R, byte[] key)
  {
    BigInteger mod = new BigInteger("2");
    mod = mod.pow(64);
    mod = mod.subtract(BigInteger.ONE);
    BigInteger Rint = Byte2BigInt(R);
    BigInteger Kint = Byte2BigInt(key);
    
    BigInteger result = Rint.multiply(Rint).mod(mod);
    result = result.add(Kint).mod(mod);
    return BigInt2Byte(result, 8);
  }
  
  static BigInteger Byte2BigInt(byte[] b) {
    return new BigInteger(1, b);
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
  
  final class FeistelBlock { public byte[] left;
    public byte[] right;
    
    public FeistelBlock(byte[] b, int start) { left = java.util.Arrays.copyOfRange(b, start, start + 8);
      right = java.util.Arrays.copyOfRange(b, start + 8, start + 16);
    }
    

    public FeistelBlock() {}
    
    public void print()
    {
      System.out.print(HAWcipher.base64Encode(left) + " ");
      System.out.println(HAWcipher.base64Encode(right));
      System.out.println(java.util.Arrays.toString(left) + " " + java.util.Arrays.toString(right));
      String tmpleft = new String(left);
      String tmpright = new String(right);
      System.out.println(tmpleft + " " + tmpright);
      System.out.println("--------------------------------");
    }
    
    public FeistelBlock swap() {
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
  
  static byte[] base64Decode(String s)
  {
    return java.util.Base64.getDecoder().decode(s);
  }
  
  static String base64Encode(byte[] bytes) {
    return java.util.Base64.getEncoder().encodeToString(bytes);
  }
  
  private byte[] padRight(String s, int blocksize)
  {
    int padsize = s.length() + blocksize - s.length() % blocksize;
    byte[] out = new byte[padsize];
    for (int i = 0; i < padsize; i++) {
      if (i < s.length()) out[i] = s.getBytes()[i]; else
        out[i] = 32;
    }
    return out;
  }
  
  private byte[] createSymKey(int size) {
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
}
