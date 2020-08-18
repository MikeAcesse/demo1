package com.example.demo.entity;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author fanzk
 * @version 1.8
 * @date 2020/8/14 17:44
 */
public class User {
	private Long id;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	private String email;
	private String password;
	private String name;

	private long createdAt;
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedDateTime(){
		return Instant.ofEpochMilli(this.createdAt).atZone(ZoneId.systemDefault())
				.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	public String getImageUrl(){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(this.email.trim().toLowerCase().getBytes(StandardCharsets.UTF_8));
			return "https://www.gravatar.com/avatar/" +String.format("%032x",new BigInteger(1,hash));
		}catch (NoSuchAlgorithmException e){
			throw  new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return String.format("User[id=%s,email=%s,password=%s,createAt=%s,CreateDateTime=%s]",getId(),getEmail(),
				getPassword(),getCreatedAt(),getCreatedDateTime());
	}
}
