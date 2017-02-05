import java.util.logging.Level;
import java.util.logging.Logger;

public class InternetConnection {

	/**
	 * Operating System Name
	 */
	String osName = System.getProperty("os.name");

	/**
	 * Logger Name
	 */
	Logger logger = Logger.getLogger(getClass().getName());

	// ------------------------------------------------------------------------------------------------------------------

	/**
	 * Constructor
	 */
	public InternetConnection() {

		Thread thread = new Thread(() -> {

			// Infinity Loop
			while (true) {
				System.out.println("Internet Connection is available: [ " + isReachableByPing("www.google.com") + " ]");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});

		// If we don't do that the application will exit immediately
		thread.setDaemon(false);

		// Starts the Thread
		thread.start();

	}

	// ------------------------------------------------------------------------------------------------------------------

	/**
	 * Checks if a web site is reachable using ping command. It is recommended
	 * that you call this method from a Thread cause the application will lag
	 * until it returns
	 *
	 * @param host
	 *            the host
	 * @return <b> true </b> if Connected on Internet,<b> false </b> if not.
	 */
	public boolean isReachableByPing(String host) {
		try {

			String cmd;
			if (osName.toLowerCase().startsWith("windows")) {
				// For Windows CMD
				cmd = "ping -n 1 " + host;
			} else {
				// For Linux and OSX Terminal
				cmd = "ping -c 1 " + host;
			}

			// Start a new Process
			Process myProcess = Runtime.getRuntime().exec(cmd);
			myProcess.waitFor();

			// Return true if exitValue==0
			return myProcess.exitValue() == 0;
		} catch (Exception ex) {
			logger.log(Level.INFO, null, ex);
			return false;
		}
	}

	// ------------------------------------------------------------------------------------------------------------------

	/**
	 * Application Starter method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new InternetConnection();
	}

}
