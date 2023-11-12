package com.bitcoinprice.login.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bitcoinprice.login.repository.SessionIdTable;
import com.bitcoinprice.dataparsing.encryption.Encryption;
import com.bitcoinprice.dataparsing.user.Login;
import com.bitcoinprice.dataparsing.user.UserSession;
import com.bitcoinprice.login.repository.UserLoginTable;

@Service
@ComponentScan({ "com.bitcoinprice", "com.bitcoinprice.repository" })
public class UserLoginService {

	@Autowired
	private UserLoginTable UserLoginTable;

	@Autowired
	private SessionIdTable SessionIdTable;

	public String Login(String email, String password)
			throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
		Encryption encrypt = new Encryption(password);
		//System.out.println(password);
		encrypt.sha256();
		encrypt.hmac256calculateHMAC();
		password = encrypt.getStringToEncrypt();

		Login User = UserLoginTable.findByemail(email);

		if (User != null && password.equals(User.getPassword()) && email.equalsIgnoreCase(email)) {
//			System.out.println("Login Worked");
			UserSession createUserSession = new UserSession(User.get_id());
			SessionIdTable.insert(createUserSession);
			return createUserSession.get_id().toString();
		} else {
//			System.out.println("user found");
			return "";
		}
	}

//	@Scheduled(fixedRate = 1000 * 60 * 60 * 24 * 30)
	@Bean
	public void updateActiveSessions() {
		System.out.println("ran");
		List<UserSession> createUserSession = SessionIdTable.findAll();
		List<UserSession> r = new ArrayList<UserSession>();
		for (UserSession i : createUserSession) {
			i.updateActiveSession();
			r.add(i);
			System.out.println(i.isActiveSession());
			SessionIdTable.save(i);
		}
	}

	public String RegisterUser(String email, String password)
			throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
		System.out.println(email + " " + password);
		Encryption encrypt = new Encryption(password);
		encrypt.sha256();
		encrypt.hmac256calculateHMAC();
		password = encrypt.getStringToEncrypt();

		if (UserLoginTable.findByemail(email) == null) {
			Login login = new Login(email, password);
			login.setUserToUser();
			UserLoginTable.insert(login);
			return "worked";
		} else {
			System.out.println("user found");
			return "error";
		}
	}

	public String getEmailBySessionId(ObjectId sessionIdObj) {
		List<UserSession> createUserSession = SessionIdTable.findAll();
		for (UserSession i : createUserSession) {
			if (i.get_id().equals(sessionIdObj)) {
				System.out.println("Match");
			}
		}
		return null;
	}

	public boolean getSessionActive(String sessionIdObj) {
		ObjectId objId = new ObjectId(sessionIdObj);
		UserSession createUserSession = SessionIdTable.findBy_id(objId);
		if (createUserSession != null)
			return createUserSession.isActiveSession();
		else
			return false;
	}

}
