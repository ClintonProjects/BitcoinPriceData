//package com.bitcoinprice.login.services;
//
//import java.nio.charset.StandardCharsets;
//import java.security.InvalidKeyException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.security.SignatureException;
//import java.util.Formatter;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
//
//import com.bitcoinprice.dataparsing.encryption.Encryption;
//import com.bitcoinprice.dataparsing.user.Login;
//import com.bitcoinprice.login.repository.UserLoginTable;
//
//@Service
//@ComponentScan({ "com.bitcoinprice", "com.bitcoinprice.repository" })
//public class UserRegisterService {
//	
//	@Autowired
//	private UserLoginTable UserLoginTable;
//	
//	public String RegisterUser(String email, String password) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
//		Encryption encrypt = new Encryption(password);
//		encrypt.sha256();
//		encrypt.hmac256calculateHMAC();
//		password = encrypt.getStringToEncrypt();
//		
//		if (UserLoginTable.findByemail(email) != null) {
//			return "login successful";
//		} else {
//			System.out.println("user not found");
//			return "login failure";
//		}
//	}
//}
