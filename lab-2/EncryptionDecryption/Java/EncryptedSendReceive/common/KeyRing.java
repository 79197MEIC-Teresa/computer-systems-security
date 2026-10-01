/**
 * Material/Labs para SRSC 21/22, Sem-1
 * hj
 **/

// KeyRing.java
// A class to manage the keyring where the symmetric key is stored


import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;


public class KeyRing {
    /** Initializations
     *  According to the Crypto we will use
    **/

    // public static final String ALGORITHM = "DESede"; // This is for Triple DES Alg.
    // public static final String ALGORITHM = "Blowfish"; // This is for Blowfish Alg. 
    // etc ... You can use different algorithms ...

    public static final String ALGORITHM = "AES"; // We will use AES
    public static final String KEYRING = "keyring"; // A file to store Key Objects

   // Later on we will use keystores or keyrings to store/manage keys
   
   
  /**
   * Get key from keyring
   * @return :  secret key (generated for the Alg we want to use
   * @throws Exception if something is wrong
   */
  public static SecretKey readSecretKey() throws Exception {

    // Read the key
    // System.out.println("reading the key from the keyring ...");
    File f=new File(KEYRING);
    long fl= f.length();
    byte[] keyBuffer = new byte[(int)fl];

    InputStream is = new FileInputStream(KEYRING);
    try {
      is.read(keyBuffer);
    } 
    finally {
      try {
        is.close();
      } catch (Exception e) {

        // Nothing by now ... Exception handler if you want
      } 
    } 
    // The key is repersenet din its "internal" formar as a Java Object
    // Type:  SecretKey 

    return new SecretKeySpec(keyBuffer, ALGORITHM);
  } 
}

