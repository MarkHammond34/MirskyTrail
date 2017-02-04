package groupProject;

import java.io.*;

/**
 * @author Robert
 * 
 *         Logs the details of the game into a file in the same directory the
 *         game is stored in
 */
public class Logger {

	private File logFile = new File("game_log.txt");
	private FileWriter fileWriter;
	private BufferedWriter buffWriter;

	/**
	 * Creates a new instance of the logger. Multiple instances will not affect
	 * the ability to log events.
	 */
	public Logger() {
		// This constructor first attempts to create the log file if it does not
		// already exist
		try {
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Logs an event
	 * 
	 * @param event
	 *            The event to be logged
	 * @return true if the event was successfully logged, false if not
	 */
	public boolean logEvent(String event) {
		// Simply logging the event into the log file, then going to the next
		// line.
		try {
			fileWriter = new FileWriter(logFile.getAbsoluteFile(), true);
			buffWriter = new BufferedWriter(fileWriter);
			buffWriter.write(event);
			buffWriter.newLine();
			buffWriter.close();
			fileWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
