package edu.cmu.sv.ws.ssnoc.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.h2.util.StringUtils;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.po.UserPO;
import edu.cmu.sv.ws.ssnoc.data.po.UserProfileAdminPO;

/**
 * This is a utility class which is used to encrypt and decrypt passwords. This
 * is primarily used in user sign up and login verification functions.
 * 
 */
public class SSNCipher {
	private static final String ENCRYPTION_ALGORITHM = "AES";
	private static final String CHAR_SET = "UTF8";

	/**
	 * This method will generate a random key. This can be used to encrypt a new
	 * user's password before saving.
	 * 
	 * @return - SecretKey object
	 * @throws NoSuchAlgorithmException
	 */
	public static final SecretKey generateRandomKey()
			throws NoSuchAlgorithmException {
		return KeyGenerator.getInstance(ENCRYPTION_ALGORITHM).generateKey();
	}

	/**
	 * This method will restore the Key from the byte[]. This can be used when
	 * trying to decrypt the password for validation during login.
	 * 
	 * @param key
	 *            - Key as a byte[]
	 * 
	 * @return - SecretKey object
	 */
	public static final SecretKey getKey(byte[] key) {
		return new SecretKeySpec(key, ENCRYPTION_ALGORITHM);
	}

	/**
	 * Utility method to encrypt any String with the given Key. Note that this
	 * uses AES encryption algorithm and UTF8 character set.
	 * 
	 * @param password
	 *            - String to encrypt
	 * @param key
	 *            - Key to be used for encryption
	 * 
	 * @return - Encrypted text
	 * 
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws UnsupportedEncodingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static final byte[] encrypt(String password, SecretKey key)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);

		byte[] utf8 = password.getBytes(CHAR_SET);
		byte[] enc = cipher.doFinal(utf8);

		return enc;
	}

	/**
	 * Utility method to decrypt any String with the given Key. Note that this
	 * uses AES encryption algorithm and UTF8 character set.
	 * 
	 * @param password
	 *            - Password to be decrypted
	 * @param key
	 *            - Key to be used for decryption
	 * 
	 * @return - Decrypted Plain text password
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 */
	public static final String decrypt(byte[] password, SecretKey key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] utf8 = cipher.doFinal(password);
		return new String(utf8, CHAR_SET);
	}

	/**
	 * This method will encrypt the password, and set the salt used to the
	 * UserPO so that it can be saved to the DB.
	 * 
	 * @param po
	 *            - UserPO object.
	 */
	public static UserPO encryptPassword(UserPO po) {
		if (po == null) {
			return null;
		}
		try {
			SecretKey key = SSNCipher.generateRandomKey();
			byte[] encryptedPassword = SSNCipher.encrypt(po.getPassword(), key);
			po.setPassword(StringUtils.convertBytesToHex(encryptedPassword));
			po.setSalt(StringUtils.convertBytesToHex(key.getEncoded()));
		} catch (Exception e) {
			Log.error("An Error occured when trying to encrypt password", e);
			throw new RuntimeException(
					"An Error occured when trying to encrypt password");
		}

		return po;
	}
	
	/**
	 * This method will encrypt the password, and set the salt used to the
	 * UserProfileAdminPO so that it can be saved to the DB.
	 * 
	 * @param po
	 *            - UserPO object.
	 */
	public static UserProfileAdminPO encryptPassword(UserProfileAdminPO po) {
		if (po == null) {
			return null;
		}
		try {
			SecretKey key = SSNCipher.generateRandomKey();
			byte[] encryptedPassword = SSNCipher.encrypt(po.getPassword(), key);
			po.setPassword(StringUtils.convertBytesToHex(encryptedPassword));
			po.setSalt(StringUtils.convertBytesToHex(key.getEncoded()));
		} catch (Exception e) {
			Log.error("An Error occured when trying to encrypt password", e);
			throw new RuntimeException(
					"An Error occured when trying to encrypt password");
		}

		return po;
	}
	
}
