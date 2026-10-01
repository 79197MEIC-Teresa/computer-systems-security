import java.util.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

// Very simple (initial) code to check encryption/decryption with
// Symmetric Cryptographic Algorithms in Java.
// Support from JCA/JCE
// (Java Cryptography Archirecture / JCE Java Cryptographic Extensions).
// It is just to test and see how different algorithms and crypto configs
// can be easly used ...
// In next weeks we will learn many details on the correct use (practical)
// of Crypto in Java using the JCE

public class hjCipherTest {
    public static void main(String args[]) {
        try {

            if (args.length != 3) {
				System.out.println("Use: java hjCipherTest <data> <alg> <cipherconf>");
				System.out.println("ex: java hjCipherTest secretmessage  AES AES/CBC/PKCS5Padding");
				System.out.println("  Other options (try to modify te progam to support): ");
				System.out.println("  AES AES/CTR/PKCS5Padding");
				System.out.println("  BLOWFISH BLOWFISH/CFB/NoPadding");
				System.out.println("  AES AES/OFB/NoPadding");
				System.exit(-1);
			}

			// Important: different symmetric algorithms use different
			// sizes for base-operated blocks and also different keysizes

			// Can also note that in general you need to operate the
			// symmetric encryption using a srandardized padding method
			// In above examples ths is the role pk PKCS#5 for example.
			// PKCS#5 in a standardized padding.


			// Initialization Vector (IV)
			// In some situations (certain Encryption Modes of
			// Operation, we need IVs as initial paraleters for
			// Symmetric encryption/decryption
			// Important: must use IV size depending on the block size
			// for the used algorithm
			
			byte[] iv = {
				0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
				0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f
			};

            IvParameterSpec dps= new IvParameterSpec(iv);
			// Comment last line for modes that don't operate with IVs

			// We need a key
			// We will have different ways ... 
			// We can use a key that we already have ... ex, stored in a
				// a file (as our keyring) or in a keystore (standard java file repository)

			// We can also to generate the key to use ...
			// Or we can obtain it from a "trusted and secure"
			// key distribution service /or protocol)
			// Here we will generate the key (on-the-fly)

            KeyGenerator kg = KeyGenerator.getInstance(args[1]);
	    	kg.init(256);
	   
	    	// Initialize the cryptosuite parameterization
            Cipher c = Cipher.getInstance(args[2]);

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
			
			c.init(Cipher.ENCRYPT_MODE, key, dps);
			//c.init(Cipher.ENCRYPT_MODE, key);	    

	   
            byte plaintext[] = args[0].getBytes(); // input plaintext
            System.out.println("Plaintext in Hexadecimal:\n" +new String(Utils.toHex(plaintext)));
	    
            byte ciphertext[] = c.doFinal(plaintext);  // out ciphertext

            byte[] encryptedBase64 = Base64.getEncoder().encode(ciphertext);  

            System.out.println("Ciphertext in Base64:\n" +new String(encryptedBase64));
            System.out.println("Ciphertext in Hexadecimal:\n" +new String(Utils.toHex(ciphertext)));


			System.out.println("======================================");    
				System.out.println("Decrypt....: ");
			System.out.println("======================================");
			
			c.init(Cipher.DECRYPT_MODE, key, dps);
			//c.init(Cipher.DECRYPT_MODE, key);	    

            byte output[] = c.doFinal(ciphertext);
            System.out.println("Initial input plaintext: "
			       +new String (output));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
