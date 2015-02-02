package edu.cmu.sv.ws.ssnoc.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.h2.util.StringUtils;

public class PasswordUtils {
	public static void main(String[] args) {
		if (args.length == 0 || args[0].trim().length() == 0) {
			System.out
					.println("Plain text password not passed. \n Usage: "
							+ "java edu.cmu.sv.ws.ssnoc.common.utils.PasswordUtils <plainTextPassword>");
			return;
		}

		try {
			SecretKey key = SSNCipher.generateRandomKey();
			byte[] encryptedPassword = SSNCipher.encrypt(args[0], key);

			System.out.println("Encrypted Password = "
					+ StringUtils.convertBytesToHex(encryptedPassword));
			System.out.println("Salt used to encrypt = "
					+ StringUtils.convertBytesToHex(key.getEncoded()));
		} catch (NoSuchAlgorithmException | InvalidKeyException
				| NoSuchPaddingException | UnsupportedEncodingException
				| IllegalBlockSizeException | BadPaddingException e) {
			System.out.println("Yikes !! We ran into an error when trying "
					+ "to encrypt your password. Following is more details "
					+ "on the error: ");
			e.printStackTrace();
		}

	}

}
