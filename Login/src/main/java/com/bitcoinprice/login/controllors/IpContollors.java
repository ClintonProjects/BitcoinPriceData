package com.bitcoinprice.login.controllors;

import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.bitcoinprice.dataparsing.user.Login;
import com.bitcoinprice.login.services.UserLoginService;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class IpContollors {

	@Autowired
	UserLoginService userLoginService;
	
	@GetMapping(value = "/t")
	public String t() {
		return "test";
	}

	@PostMapping(value = "/login")
	public response Login(@RequestBody Login login)
			throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
		return new response(userLoginService.Login(login.getEmail(), login.getPassword()));
	}

	@PostMapping(value = "/loggedInAs")
	public String loggedInAs(@RequestBody ObjectId SesionId)
			throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
		return userLoginService.getEmailBySessionId(SesionId);
	}

	@PostMapping(value = "/Register")
	public Object RegisterUser(@RequestBody Login login)
			throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
		String result = userLoginService.RegisterUser(login.getEmail(), login.getPassword());
		if (result.equals("error"))
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return new response(userLoginService.Login(login.getEmail(), login.getPassword()));
	}

	@GetMapping("/session/{id}")
	@ResponseBody
	public response getFooById(@PathVariable String id) {
		return new response(userLoginService.getSessionActive(id));
	}

}

class response {
	String id = "";
	boolean session = false;

	response(String id) {
		this.id = id;
	}

	response(boolean session) {
		this.session = session;
	}

	public boolean isSession() {
		return session;
	}

	public void setSession(boolean session) {
		this.session = session;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
