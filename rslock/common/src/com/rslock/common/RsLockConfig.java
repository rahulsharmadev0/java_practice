package com.rslock.common;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RsLockConfig {
	private final List<Path> sourceFiles;
	private final Path destinationDir;
	private final Path keystorePath;
	private final String alias;

	public RsLockConfig(List<Path> sourceFiles, Path destinationDir, Path keystorePath, String alias) {
		this.sourceFiles = sourceFiles;
		this.destinationDir = destinationDir;
		this.keystorePath = keystorePath;
		this.alias = alias;
	}

	// Ensure all required parameters are present
	public void validate() {
		if (sourceFiles == null || sourceFiles.isEmpty()) {
			throw new IllegalArgumentException("At least one source file must be specified.");
		}

		// Validate source files exist
		for (Path source : sourceFiles) {
			Path normalized = source.toAbsolutePath().normalize();
			if (!Files.exists(normalized)) {
				throw new IllegalArgumentException(
						"Source file does not exist: " + normalized);
			}
		}

		if (destinationDir != null) {
			if (!Files.exists(destinationDir)) {
				throw new IllegalArgumentException("Destination directory does not exist: " + destinationDir);
			}
			if (!Files.isDirectory(destinationDir)) {
				throw new IllegalArgumentException("Destination must be a directory: " + destinationDir);
			}
		}

		// Validate keystore path exists (if provided)
		if (keystorePath != null && !Files.exists(keystorePath)) {
			throw new IllegalArgumentException("Keystore file does not exist: " + keystorePath);
		}
	}

	public int getSourceFileCount() {
		return sourceFiles.size();
	}

	public List<Path> getSourceFiles() {
		return sourceFiles;
	}

	public Path getDestinationDir() {
		return destinationDir;
	}

	public Path getKeystorePath() {
		return keystorePath;
	}

	public String getAlias() {
		return alias;
	}

	// Generate destination directory for a given source file
	// so, that if destinationDir is null, it returns the source file's parent directory
	public Path generateDestinationDir(Path sourceFile) {
		return destinationDir != null ? destinationDir : sourceFile.toAbsolutePath().getParent();
	}

	@Override
	public String toString() {
		return "RsLockConfig{" + // Updated name here
				"sourceFiles=" + sourceFiles +
				", destinationDir=" + destinationDir +
				", keystorePath=" + keystorePath +
				", alias='" + alias + '\'' + // Added alias to toString
				'}';
	}

	// Use this method to parse command line arguments
	public static RsLockConfig fromArgs(String[] args)
			throws IllegalArgumentException {
		List<Path> sources = new ArrayList<>();
		Path destination = null;
		Path keystore = null;
		Flag currentFlag = null;
		String alias = null;

		for (String arg : args) {
			Flag detected = Flag.parser(arg);
			if (detected != null) {
				currentFlag = detected;
				continue;
			}

			if (currentFlag == null) {
				throw new IllegalArgumentException("Value without flag: " + arg);
			}

			switch (currentFlag) {
				case SOURCE -> {
					for (String part : arg.split(",")) {
						String value = part.trim();
						if (!value.isEmpty())
							sources.add(Path.of(value));
					}
				}
				case DESTINATION -> {
					destination = Path.of(arg);
					currentFlag = null;
				}
				case KEYSTORE -> {
					keystore = Path.of(arg);
					currentFlag = null;
				}
				case alias -> {
					alias = arg;
					currentFlag = null;
				}
			}

		}

		// Use Default parameter if not provided
		if (alias == null || alias.isEmpty())
			alias = RsConstraints.DEFAULT_KEYSTORE_ALIAS;

		if (keystore == null) {
			keystore = Path.of(RsConstraints.DEFAULT_KEYSTORE_FILENAME);
		}
		RsLockConfig commandLineArgs = new RsLockConfig(sources, destination, keystore, alias);
		return commandLineArgs;

	}

	public boolean isKeystoreExists() {
		return keystorePath != null && Files.exists(keystorePath);
	}

	/// --------
	enum Flag {
		SOURCE("-s"), DESTINATION("-d"), KEYSTORE("-k"), alias("-a");

		final String token;

		Flag(String token) {
			this.token = token;
		}

		static Flag parser(String str) {
			for (Flag f : Flag.values())
				if (f.token.equals(str))
					return f;

			return null;
		}
	}

}
