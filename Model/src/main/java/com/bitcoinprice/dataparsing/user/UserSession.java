package com.bitcoinprice.dataparsing.user;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

public class UserSession {

	@Id
	private ObjectId _id;
	private ObjectId userId;
	private Date time;
	private boolean activeSession;

	public UserSession() {
	}

	public UserSession(ObjectId userId) {
		super();
		this.userId = userId;
		setTime();
		updateActiveSession();
	}

	public UserSession(ObjectId _id, ObjectId userId) {
		super();
		this._id = _id;
		this.userId = userId;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

	public void setTime() {
		Calendar calendar = Calendar.getInstance();
		time = calendar.getTime();
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setActiveSession(boolean activeSession) {
		this.activeSession = activeSession;
	}

	public boolean isActiveSession() {
		return activeSession;
	}

	public void updateActiveSession() {
		System.out.println(LocalDateTime.ofInstant(getTime().toInstant(), ZoneOffset.UTC));
		System.out.println(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).minus(30, ChronoUnit.DAYS));
		
		if (LocalDateTime.ofInstant(getTime().toInstant(), ZoneOffset.UTC)
				.isAfter(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).minus(30, ChronoUnit.DAYS))) {
			this.activeSession = false;
			System.out.println(false);
		} else {
			// in case of error cases
			this.activeSession = true;
			System.out.println(true);
		}
	}
}
