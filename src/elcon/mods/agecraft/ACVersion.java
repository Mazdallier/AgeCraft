package elcon.mods.agecraft;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;

import cpw.mods.fml.common.Loader;
import elcon.mods.agecraft.lang.LanguageManager;

public class ACVersion implements Runnable {

	private static final String VERSION_CHECK_INIT_LOG_MESSAGE = "agecraft.version.init_log_message";
	private static final String UNINITIALIZED_MESSAGE = "agecraft.version.uninitialized";
	private static final String CURRENT_MESSAGE = "agecraft.version.current";
	private static final String OUTDATED_MESSAGE = "agecraft.version.outdated";
	private static final String GENERAL_ERROR_MESSAGE = "agecraft.version.general_error";
	private static final String FINAL_ERROR_MESSAGE = "agecraft.version.final_error";
	private static final String MC_VERSION_NOT_FOUND_MESSAGE = "agecraft.version.mc_version_not_found";
	
	private static ACVersion instance = new ACVersion();

	private static final String REMOTE_VERSION_XML_FILE = "https://raw.github.com/AgeCraft/AgeCraft/master/version.xml";

	public static Properties remoteVersionProperties = new Properties();

	public static final byte UNINITIALIZED = 0;
	public static final byte CURRENT = 1;
	public static final byte OUTDATED = 2;
	public static final byte ERROR = 3;
	public static final byte FINAL_ERROR = 4;
	public static final byte MC_VERSION_NOT_FOUND = 5;

	private static byte result = UNINITIALIZED;
	public static String remoteVersion = null;
	public static String remoteUpdateLocation = null;

	public static void checkVersion() {
		InputStream remoteVersionRepoStream = null;
		result = UNINITIALIZED;

		try {
			URL remoteVersionURL = new URL(REMOTE_VERSION_XML_FILE);
			remoteVersionRepoStream = remoteVersionURL.openStream();
			remoteVersionProperties.loadFromXML(remoteVersionRepoStream);

			String remoteVersionProperty = remoteVersionProperties.getProperty(Loader.instance().getMCVersionString());

			if(remoteVersionProperty != null) {
				String[] remoteVersionTokens = remoteVersionProperty.split("\\|");

				if(remoteVersionTokens.length >= 2) {
					remoteVersion = remoteVersionTokens[0];
					remoteUpdateLocation = remoteVersionTokens[1];
				} else {
					result = ERROR;
				}

				if(remoteVersion != null) {
					if(!ACConfig.LAST_DISCOVERED_VERSION.equalsIgnoreCase(remoteVersion)) {
						ACConfig.set(ACConfig.CATEGORY_VERSION, "last_discovered_version", remoteVersion);
					}
					
					if(remoteVersion.equalsIgnoreCase(getVersionForCheck())) {
						result = CURRENT;
					} else {
						result = OUTDATED;
					}
				}

			} else {
				result = MC_VERSION_NOT_FOUND;
			}
		} catch(Exception e) {
		} finally {
			if(result == UNINITIALIZED) {
				result = ERROR;
			}

			try {
				if(remoteVersionRepoStream != null) {
					remoteVersionRepoStream.close();
				}
			} catch(Exception ex) {
			}
		}
	}

	private static String getVersionForCheck() {
		String[] versionTokens = ACReference.VERSION.split(" ");

		if(versionTokens.length >= 1)
			return versionTokens[0];
		else
			return ACReference.VERSION;
	}

	public static void logResult() {
		if(result == CURRENT || result == OUTDATED) {
			ACLog.log(Level.INFO, getResultMessage());
		} else {
			ACLog.log(Level.WARNING, getResultMessage());
		}
	}

	public static String getResultMessage() {
		if(result == UNINITIALIZED) {
			return LanguageManager.getLocalization(UNINITIALIZED_MESSAGE);
		} else if(result == CURRENT) {
			String returnString = LanguageManager.getLocalization(CURRENT_MESSAGE);
			returnString = returnString.replace("@REMOTE_MOD_VERSION@", remoteVersion);
			returnString = returnString.replace("@MINECRAFT_VERSION@", Loader.instance().getMCVersionString());
			return returnString;
		} else if(result == OUTDATED && remoteVersion != null && remoteUpdateLocation != null) {
			String returnString = LanguageManager.getLocalization(OUTDATED_MESSAGE);
			returnString = returnString.replace("@MOD_NAME@", ACReference.NAME);
			returnString = returnString.replace("@REMOTE_MOD_VERSION@", remoteVersion);
			returnString = returnString.replace("@MINECRAFT_VERSION@", Loader.instance().getMCVersionString());
			returnString = returnString.replace("@MOD_UPDATE_LOCATION@", remoteUpdateLocation);
			return returnString;
		} else if(result == OUTDATED && remoteVersion != null && remoteUpdateLocation != null) {
			String returnString = LanguageManager.getLocalization(OUTDATED_MESSAGE);
			returnString = returnString.replace("@MOD_NAME@", ACReference.NAME);
			returnString = returnString.replace("@REMOTE_MOD_VERSION@", remoteVersion);
			returnString = returnString.replace("@MINECRAFT_VERSION@", Loader.instance().getMCVersionString());
			returnString = returnString.replace("@MOD_UPDATE_LOCATION@", remoteUpdateLocation);
			return returnString;
		} else if(result == ERROR) { 
			return LanguageManager.getLocalization(GENERAL_ERROR_MESSAGE);
		} else if(result == FINAL_ERROR) {
			return LanguageManager.getLocalization(FINAL_ERROR_MESSAGE);
		} else if(result == MC_VERSION_NOT_FOUND) {
			String returnString = LanguageManager.getLocalization(MC_VERSION_NOT_FOUND_MESSAGE);
			returnString = returnString.replace("@MOD_NAME@", ACReference.NAME);
			returnString = returnString.replace("@MINECRAFT_VERSION@", Loader.instance().getMCVersionString());
			return returnString;
		} else {
			result = ERROR;
			return LanguageManager.getLocalization(GENERAL_ERROR_MESSAGE);
		}
	}

	public static String getResultMessageForClient() {
		String returnString = LanguageManager.getLocalization(OUTDATED_MESSAGE);
		returnString = returnString.replace("@MOD_NAME@", ACColors.TEXT_COLOUR_PREFIX_YELLOW + ACReference.NAME + ACColors.TEXT_COLOUR_PREFIX_WHITE);
		returnString = returnString.replace("@REMOTE_MOD_VERSION@", ACColors.TEXT_COLOUR_PREFIX_YELLOW + ACVersion.remoteVersion + ACColors.TEXT_COLOUR_PREFIX_WHITE);
		returnString = returnString.replace("@MINECRAFT_VERSION@", ACColors.TEXT_COLOUR_PREFIX_YELLOW + Loader.instance().getMCVersionString() + ACColors.TEXT_COLOUR_PREFIX_WHITE);
		returnString = returnString.replace("@MOD_UPDATE_LOCATION@", ACColors.TEXT_COLOUR_PREFIX_YELLOW + ACVersion.remoteUpdateLocation + ACColors.TEXT_COLOUR_PREFIX_WHITE);
		return returnString;
	}

	public static byte getResult() {
		return result;
	}

	@Override
	public void run() {
		int count = 0;

		ACLog.log(Level.INFO, LanguageManager.getLocalization(VERSION_CHECK_INIT_LOG_MESSAGE) + " " + REMOTE_VERSION_XML_FILE);

		try {
			while(count < ACReference.VERSION_CHECK_ATTEMPTS - 1 && (result == UNINITIALIZED || result == ERROR)) {

				checkVersion();
				count++;
				logResult();

				if(result == UNINITIALIZED || result == ERROR) {
					Thread.sleep(10000);
				}
			}

			if(result == ERROR) {
				result = FINAL_ERROR;
				logResult();
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void execute() {
		new Thread(instance).start();
	}
}
