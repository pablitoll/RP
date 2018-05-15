package ar.com.rp.rpcutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * A utility class that encrypts or decrypts a file.
 * 
 * @author www.codejava.net
 *
 */
public class CryptoUtils {
	private static final String KEY = "Mary has one cat";

	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES";

	public static void encryptFile(String inputFile, String outputFile) throws CryptoException {
		doCryptoFile(Cipher.ENCRYPT_MODE, KEY, new File(inputFile), new File(outputFile));
	}

	public static void decryptFile(String inputFile, String outputFile) throws CryptoException {
		doCryptoFile(Cipher.DECRYPT_MODE, KEY, new File(inputFile), new File(outputFile));
	}

	// private static void doCrypto(int cipherMode, String key, File inputFile,
	// File outputFile) throws CryptoException {
	// try {
	// Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
	// Cipher cipher = Cipher.getInstance(TRANSFORMATION);
	// cipher.init(cipherMode, secretKey);
	//
	// FileInputStream inputStream = new FileInputStream(inputFile);
	// byte[] inputBytes = new byte[(int) inputFile.length()];
	// inputStream.read(inputBytes);
	//
	// byte[] outputBytes = cipher.doFinal(inputBytes);
	//
	// FileOutputStream outputStream = new FileOutputStream(outputFile);
	// outputStream.write(outputBytes);
	//
	// inputStream.close();
	// outputStream.close();
	//
	// } catch (NoSuchPaddingException | NoSuchAlgorithmException
	// | InvalidKeyException | BadPaddingException
	// | IllegalBlockSizeException | IOException ex) {
	// throw new CryptoException("Error encrypting/decrypting file", ex);
	// }
	// }
	//

	private static void doCryptoFile(int cipherMode, String key, File inputFile, File outputFile) throws CryptoException {
		try {

			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);

			byte[] outputBytes = doCrypto(cipherMode, key, inputBytes);

			FileOutputStream outputStream = new FileOutputStream(outputFile);
			outputStream.write(outputBytes);

			inputStream.close();
			outputStream.close();

		} catch (IOException ex) {
			throw new CryptoException("Error encrypting/decrypting file", ex);
		}
	}

	private static byte[] doCrypto(int cipherMode, String key, byte[] valor) throws CryptoException {
		try {
			Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(cipherMode, secretKey);

			return cipher.doFinal(valor);

		} catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex) {
			throw new CryptoException("Error encrypting/decrypting file", ex);
		}
	}

	public static String encryptText(String texto) throws CryptoException {
		byte[] respuesta = doCrypto(Cipher.ENCRYPT_MODE, KEY, texto.getBytes(StandardCharsets.UTF_8));
		String retorno = "";
		for(int i = 0; i < respuesta.length; i++){
			retorno += String.valueOf(respuesta[i]) + ";";
		}
		return retorno;
	}

	public static String decryptText(String texto) throws CryptoException {
		String[] lista = texto.split(";");
		byte[] respuesta = new byte[lista.length];
		int i = 0;
		for(String aux: lista){
			respuesta[i] = Byte.parseByte(aux);
			i++;
		}
		return new String(doCrypto(Cipher.DECRYPT_MODE, KEY,  respuesta), StandardCharsets.UTF_8);
	}
}