package com.mohey.memberservice.ex;

public class CustomApiException extends RuntimeException {
	public CustomApiException(String message) {
		super(message);
	}
}
