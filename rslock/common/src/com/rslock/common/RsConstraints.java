package com.rslock.common;

public final class RsConstraints {

	// Prevent instantiation
	private RsConstraints() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	// AES Constraints
	public static final String AES_ALGORITHM = "AES";
	public static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
	public static final int AES_KEY_SIZE = 256; // bits

	// RSA Constraints
	public static final String RSA_ALGORITHM = "RSA";
	public static final String RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
	public static final int RSA_KEY_SIZE = 2048; // bits

	// KeyStore Constraints
	public static final String KEYSTORE_TYPE = "PKCS12";
	public static final String KEYSTORE_PROVIDER = "BC"; // Bouncy Castle
	public static final String DEFAULT_KEYSTORE_ALIAS = "rslock-key";
	public static final String DEFAULT_KEYSTORE_FILENAME = "rskeystore.p12";
	public static final char[] DEFAULT_KEYSTORE_PASSWORD = "rslock-password".toCharArray();

	public static final String DEFAULT_ENCRYPTED_FILE_EXTENSION = ".rslocked";

	// Buffer size for file streams operations
	public static final int DEFAULT_BUFFER_SIZE = 8192;

	// Initialization Vector 16 byte (128 bits) size for AES
	public static final int IV_SIZE = 16; 

}
