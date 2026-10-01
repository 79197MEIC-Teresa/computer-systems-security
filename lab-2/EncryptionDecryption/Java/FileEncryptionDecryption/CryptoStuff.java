/**
/** A utility class to manage crypto
**/


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.Key;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;


public class CryptoStuff {
   
   private static final String ALGORITHM = "AES";
   private static final String CRYPTOCONFIG = "AES/ECB/PKCS5Padding";    
   // private static final String CRYPTOCONFIG = "AES/CTR/NoPadding";

   //Initializaton vector (fixed) we will use ...
   private static final byte[] ivBytes  = new byte[] {
	   0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 ,
      0x0f, 0x0d, 0x0e, 0x0c, 0x0b, 0x0a, 0x09, 0x08 
   };

    
     public static void encrypt(String key, File inputFile, File outputFile)
                 throws CryptoException 
     {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
     }
    
     private static void doCrypto(int cipherMode, String key, File inputFile,
				                File outputFile) throws CryptoException
     {
        try 
	  {
	     
              IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
              Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
	      Cipher cipher = Cipher.getInstance(CRYPTOCONFIG);
	      //  cipher.init(cipherMode, secretKey, ivSpec);
	      cipher.init(cipherMode, secretKey);	      

	                  
              FileInputStream inputStream = new FileInputStream(inputFile);
              byte[] inputBytes = new byte[(int) inputFile.length()];
	                 inputStream.read(inputBytes);
	                  
              byte[] outputBytes = cipher.doFinal(inputBytes);
	                  
              FileOutputStream outputStream = new FileOutputStream(outputFile);
	                 outputStream.write(outputBytes);
	                  
              inputStream.close();
              outputStream.close();
	                  
	  }
 	  catch (NoSuchPaddingException | NoSuchAlgorithmException
	          | InvalidKeyException | BadPaddingException
	          | IllegalBlockSizeException
		  | IOException ex)
	  {
	    throw new CryptoException("Error encrypting/decrypting file", ex);
	  }
	
     }
   
}
