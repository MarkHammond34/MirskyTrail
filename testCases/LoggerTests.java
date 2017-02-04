package testCases;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.Test;

import groupProject.Logger;

public class LoggerTests {

	/**
	 * This test checks if a file is created when a log file does not exist from
	 * a previous run of the game
	 */
	@Test
	public void testFileWasCreated() {
		File logFile = new File("game_log.txt");
		if (logFile.exists())
			logFile.delete();
		Logger logger = new Logger();
		assertTrue(logFile.exists());
		logFile.delete();
	}

	/**
	 * This test checks to see that data can be written to and read from the log
	 * file
	 */
	@Test
	public void testDataWasWritten() {
		File logFile = new File("game_log.txt");
		if (logFile.exists())
			logFile.delete();
		Logger logger = new Logger();
		String expected = "Test data";
		logger.logEvent(expected);
		String actual = "";

		try {
			FileReader fReader = new FileReader("game_log.txt");
			BufferedReader bReader = new BufferedReader(fReader);
			actual = bReader.readLine();
		} catch (Exception e) {
			fail("Could not read file");
		}

		logFile.delete();
		assertEquals(expected, actual);
	}

}
