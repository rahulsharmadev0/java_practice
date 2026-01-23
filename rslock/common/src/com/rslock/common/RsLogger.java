package com.rslock.common;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class RsLogger {
	private RsLogger() {
	}

	static private DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

	public static void init(String logFileName, Level consoleLevel, Level fileLevel) {
		try {
			Logger root = Logger.getLogger("");
			 root.setLevel(Level.ALL);

			// Removes all handlers attached directly
			for (Handler h : root.getHandlers())
				root.removeHandler(h);

			// Console
			ConsoleHandler console = new ConsoleHandler();
			console.setLevel(consoleLevel);
			console.setFormatter(new RsConsoleFormatter());

			String timestamp = LocalDateTime.now().format(TS);

			int dot = logFileName.lastIndexOf('.');
			if (dot > 0) {
				logFileName = logFileName.substring(0, dot);
			}

			// File
			Path logPath = Path.of(logFileName + "-" + timestamp + ".log").toAbsolutePath();
			FileHandler file = new FileHandler(logPath.toString(), true);
			file.setLevel(fileLevel);
			file.setFormatter(new RsFileFormatter());

			root.addHandler(file);
			root.addHandler(console);
			String line = "=".repeat(10);
			root.info(line + "Logging initialized" + line);
			root.info("Log file: " + logPath.toString() + "\n");
		} catch (IOException e) {
			throw new RuntimeException("Failed to initialize logging", e);
		}

	}

	static class RsConsoleFormatter extends Formatter {
		@Override
		public String format(LogRecord log) {
			return log.getMessage() + System.lineSeparator();
		}
	}

	static class RsFileFormatter extends Formatter {


		@Override
		public String format(LogRecord record) {
			 String threadName = Thread.currentThread().getName();
			return String.format("[%1$tF %1$tT] [%2$s] [%3$s] [%4$s] %5$s%n",
					record.getMillis(),
					record.getLevel().getLocalizedName(),
					threadName,
					record.getSourceClassName() != null
							? record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf('.') + 1)
							: "Unknown",
					formatMessage(record));
		}
	}

}
