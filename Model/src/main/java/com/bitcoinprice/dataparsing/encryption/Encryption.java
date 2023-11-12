package com.bitcoinprice.dataparsing.encryption;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

	private String stringToEncrypt;

	public Encryption(String stringToEncrypt) {
		this.stringToEncrypt = stringToEncrypt;
	}

	public void sha256() {
		try {
			System.out.println(stringToEncrypt);
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			final byte[] hash = digest.digest(stringToEncrypt.getBytes("UTF-8"));
			final StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < hash.length; i++) {
				final String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			stringToEncrypt = hexString.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void hmac256calculateHMAC() throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec secretKeySpec = new SecretKeySpec("Thishidesaredatabyencrpytingitheheheheehehehe".getBytes(),
				"HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(secretKeySpec);
		stringToEncrypt = toHexString(mac.doFinal(stringToEncrypt.getBytes()));
	}

	private static String toHexString(byte[] bytes) {
		Formatter formatter = new Formatter();
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	public String getStringToEncrypt() {
		return stringToEncrypt;
	}

}
