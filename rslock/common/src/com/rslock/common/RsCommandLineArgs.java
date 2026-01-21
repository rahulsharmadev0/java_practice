package com.rslock.common;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RsCommandLineArgs {

	private final List<Path> sourceFiles;
	private final Path destinationDir;
	private final Path keystorePath;

	public RsCommandLineArgs(List<Path> sourceFiles, Path destinationDir, Path keystorePath) {
		this.sourceFiles = sourceFiles;
		this.destinationDir = destinationDir;
		this.keystorePath = keystorePath;
	}

	private void validate() {
		// Validate source files exist
		for (Path source : sourceFiles) {
			Path normalized = source.toAbsolutePath().normalize();
			if (!Files.exists(normalized)) {
				throw new IllegalArgumentException(
						"Source file does not exist: " + normalized);
			}
		}

		// Validate destination directory exists
		if (destinationDir != null && !Files.exists(destinationDir)) {
			throw new IllegalArgumentException("Destination directory does not exist: " + destinationDir);
		}

		// Validate keystore path exists
		if (keystorePath != null && !Files.exists(keystorePath)) {
			throw new IllegalArgumentException("Keystore file does not exist: " + keystorePath);
		}
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

	@Override
	public String toString() {
		return "RsCommandLineArgs{" +
				"sourceFiles=" + sourceFiles +
				", destinationDir=" + destinationDir +
				", keystorePath=" + keystorePath +
				'}';
	}

	public static RsCommandLineArgs parse(String[] args) {

		List<Path> sources = new ArrayList<>();
		Path destination = null;
		Path keystore = null;
		Flag currentFlag = null;

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
			}

		}

		RsCommandLineArgs commandLineArgs = new RsCommandLineArgs(sources, destination, keystore);
		commandLineArgs.validate();
		return commandLineArgs;

	}

	/// --------
	enum Flag {
		SOURCE("-s"), DESTINATION("-d"), KEYSTORE("-k");

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
