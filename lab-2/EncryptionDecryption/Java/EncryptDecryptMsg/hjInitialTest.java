import java.util.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class hjInitialTest {
    public static void main(String args[]) {
        try {

            KeyGenerator kg = KeyGenerator.getInstance("AES");  //the code that implements a key generator
            kg.init(256);
        
            // Initialize the cryptosuite parameterization
            //Cipher c = Cipher.getInstance("AES/ECB/NoPadding");
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");

            
            Key key = kg.generateKey();

            // If you want to see the generated Key bytes and size ...
            byte[] keyBytes= key.getEncoded();
            int keyLen= keyBytes.length;

            byte[] keyBytesBase64= Base64.getEncoder().encode(keyBytes);

            System.out.println();
                System.out.println("Key in Base64:\n" +new String(keyBytesBase64));
                System.out.println("Key in Hex:\n" +new String(Utils.toHex(keyBytes)));	    
                System.out.println("Key Size in Bytes:\n" +keyLen);	    


            System.out.println("======================================");
            System.out.println("Encrypt...:");
            System.out.println("======================================");
            
            c.init(Cipher.ENCRYPT_MODE, key);

            byte plaintext[] = args[0].getBytes(); // input plaintext
            System.out.println("Plaintext in Hexadecimal:\n" +new String(Utils.toHex(plaintext)));
        
            byte ciphertext[] = c.doFinal(plaintext);  // out ciphertext

            byte[] encryptedBase64 = Base64.getEncoder().encode(ciphertext);  

            System.out.println("Ciphertext in Base64:\n" +new String(encryptedBase64));
            System.out.println("Ciphertext in Hexadecimal:\n" +new String(Utils.toHex(ciphertext)));


            System.out.println("======================================");    
                System.out.println("Decrypt....: ");
            System.out.println("======================================");
            
            c.init(Cipher.DECRYPT_MODE, key);

            byte output[] = c.doFinal(ciphertext);  // out original initial plaintext
            System.out.println("Initial input plaintext: "
			       +new String (output));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
