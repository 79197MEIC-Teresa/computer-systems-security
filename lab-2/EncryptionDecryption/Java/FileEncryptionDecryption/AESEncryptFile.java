import java.io.File;
 
/*
 *  Main class for encryption/decryption
 *  using CryptoStuff Class
 */
public class AESEncryptFile {
   public static void main(String[] args) {
      if (args.length != 2) {
	      System.out.println("Use: AESEncrypFile <aes-key> <file>");
         System.exit(-1);
      }
   
      String key = args[0];   // Need AES Key, 128 or 256 bits
                              // We could generate from a seed or pwd
			                     // Later ...
      File inputFile = new File(args[1]);
      String encryptedfile=args[1]+".enc";
      File encryptedFile = new File(encryptedfile);

      try {
            CryptoStuff.encrypt(key, inputFile, encryptedFile);
      } catch (CryptoException ex) {
         System.out.println(ex.getMessage());
         ex.printStackTrace();
      }
   }
}