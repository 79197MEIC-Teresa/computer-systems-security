
// SendEncrypt.java
// Encrypt and send encrypted message using TCP

import java.io.*;
import java.net.*;
import java.security.spec.KeySpec;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

/**
 * Encrypt and send
 */
public class SendEncrypt {

	public static void main(String args[]) throws Exception {
    	// Defaults para host e porto destino.
		// Se quizer passe em parametro a seguir

		if (args.length != 2) {
			System.out.println("Usar: SendEncrypt <hostname> <port>");
			System.exit(-1);
		}

		String desthost= args[0];
		Integer destport=Integer.parseInt(args[1]);

		// Siphersuite config
		//String ciphersuite="AES/CBC/PKCS5Padding";
		//String ciphersuite="AES/CTR/NoPadding";
		String ciphersuite="AES/ECB/PKCS5Padding";      

		byte[] ivBytes= new byte[] {
			0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
			0x08, 0x09, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15 
		};

      	IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

		System.out.println("\nDestino:" +desthost + " Porto:" +destport);
		System.out.println("Ciphersuite a usar: " 
		+ ciphersuite);

		String plaintext;
		// Obtain key stored in my keyring
		SecretKey key = KeyRing.readSecretKey(); 

		for(;;)  {
			plaintext = prompt("Message: ");
			if (plaintext.equals("exit!")) break;
			byte[] ptextbytes= plaintext.getBytes();

			System.out.println("--------------------------------------------");
			System.out.println("Plaintext message:");
				System.out.println(plaintext);
			System.out.println("Size: " +ptextbytes.length +" bytes");
			System.out.println();
			
			System.out.println("Plaintext message (in HEX):"); 
			System.out.println(Utils.toHex(ptextbytes, ptextbytes.length));
			System.out.println("Size: "+ptextbytes.length +" bytes");
			System.out.println();
			
			Cipher cipher = Cipher.getInstance(ciphersuite);
			//	   cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] ciphertext = cipher.doFinal(plaintext.getBytes());

			System.out.println("Encrypted message to send (in HEX):");
			System.out.println(Utils.toHex(ciphertext, ciphertext.length));
			System.out.println("Size: " +ciphertext.length + " bytes");
			System.out.println();
			
			System.out.println("Encrypted message to send (in Base64):");
			String b64ciphertext
				= Base64.getEncoder().encodeToString(ciphertext);
			byte[] b64rep = b64ciphertext.getBytes();
			System.out.println(b64ciphertext );
			System.out.println("Size: " + b64rep.length + " bytes");
			System.out.println("----------------------------------------------");

			// Enviar cyphertext por um socket !
			Socket s = new Socket(desthost, destport);
			try {
				DataOutputStream os = new DataOutputStream(s.getOutputStream());
				os.writeInt(ciphertext.length);
				os.write(ciphertext);
				os.close();
			} finally {
				try {
				s.close();
				} catch (Exception e) {

				// ... Se quiser trate aqui a excepção
				}
			}
		}
      	System.exit(0);
  	}


	/**
	 * Mostra um prompt e captura a resposta numa String.
	 */
	public static String prompt(String prompt) throws IOException {
		System.out.print(prompt);
		System.out.flush();
		BufferedReader input = 
		new BufferedReader(new InputStreamReader(System.in));
		String response = input.readLine();
		System.out.println();
		return response;
	} 
}

