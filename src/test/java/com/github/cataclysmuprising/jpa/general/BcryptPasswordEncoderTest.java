package com.github.cataclysmuprising.jpa.general;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptPasswordEncoderTest {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String inputText = "user3_P@ssw0rd";
		String encodedString = encoder.encode(inputText);
		System.out.println("Encoding '" + inputText + "' ==> " + encodedString);
	}
}
