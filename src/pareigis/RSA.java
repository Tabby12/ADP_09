package pareigis;

import java.math.BigInteger;

public class RSA { public BigInteger prime1;
  public BigInteger prime2;
  public BigInteger modulus;
  public BigInteger phi;
  public BigInteger publickey;
  public BigInteger privatekey;
  
  RSA() {}
  
  public void createRSAwithPubKey(String hispubkey) throws BoundException { byte[] pubkeyall = HAWcipher.base64Decode(hispubkey);
    byte[] pubkeykey = new byte[8];
    byte[] pubkeymod = new byte[16];
    if ((pubkeyall.length < 24) && (pubkeyall.length >= 8)) {
      java.util.Arrays.fill(pubkeymod, (byte)57);
      System.arraycopy(pubkeyall, 0, pubkeykey, 0, 8);
      publickey = HAWcipher.Byte2BigInt(pubkeykey);
      modulus = BigInteger.ZERO;
      throw new BoundException("Public Key has " + pubkeyall.length + " Bytes. Expected are 24 Bytes. The modulus will be set to zero.");
    }
    if (pubkeyall.length < 8) {
      publickey = BigInteger.ZERO;
      modulus = BigInteger.ZERO;
      throw new BoundException("Public Key has " + pubkeyall.length + " Bytes. Expected are 24 Bytes. Both key and modulus will be set to zero.");
    }
    if (pubkeyall.length > 24) {
      System.arraycopy(pubkeyall, 0, pubkeykey, 0, 8);
      System.arraycopy(pubkeyall, 8, pubkeymod, 0, 16);
      publickey = HAWcipher.Byte2BigInt(pubkeykey);
      modulus = HAWcipher.Byte2BigInt(pubkeymod);
      throw new BoundException("Public Key has " + pubkeyall.length + " Bytes. Expected are 24 Bytes. Key and modulus will be set using the first 24 Bytes.");
    }
    
    System.arraycopy(pubkeyall, 0, pubkeykey, 0, 8);
    System.arraycopy(pubkeyall, 8, pubkeymod, 0, 16);
    publickey = HAWcipher.Byte2BigInt(pubkeykey);
    modulus = HAWcipher.Byte2BigInt(pubkeymod);
  }
  



  public String generateKeys(int bytelength)
  {
    prime1 = createPrime(bytelength / 2);
    prime2 = createPrime(bytelength / 2);
    

    modulus = prime1.multiply(prime2);
    phi = prime1.subtract(BigInteger.ONE).multiply(prime2.subtract(BigInteger.ONE));
    



    java.util.Random rnd = new java.util.Random();
    BigInteger key = BigInteger.probablePrime(64, rnd);
    while (!phi.gcd(key).equals(BigInteger.ONE)) {
      key = key.add(BigInteger.ONE);
    }
    publickey = key;
    


    privatekey = extEuklid(publickey, phi).s;
    



    byte[] outbyte = new byte[bytelength + bytelength / 2];
    

    byte[] keybyte = HAWcipher.BigInt2Byte(publickey, bytelength / 2);
    byte[] modulusbyte = HAWcipher.BigInt2Byte(modulus, bytelength);
    System.arraycopy(keybyte, 0, outbyte, 0, bytelength / 2);
    System.arraycopy(modulusbyte, 0, outbyte, bytelength / 2, bytelength);
    
    return HAWcipher.base64Encode(outbyte);
  }
  

  private BigInteger createPrime(int bytelength)
  {
    java.util.Random rnd = new java.util.Random();
    BigInteger bi = BigInteger.probablePrime(bytelength * 8, rnd);
    
    return bi;
  }
  
  public BigInteger encipherRSA(BigInteger in) {
    return in.modPow(publickey, modulus);
  }
  
  public BigInteger decipherRSA(BigInteger in) throws BoundException {
    if ((modulus == null) || (privatekey == null) || (modulus == BigInteger.ZERO)) {
      throw new BoundException("private key = " + privatekey + ", N = " + modulus + ". Expecting actual numbers.");
    }
    return in.modPow(privatekey, modulus);
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
}