package org.agecraft;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

public class ACLog {

	private static Logger logger = Logger.getLogger(ACReference.MOD_ID);
	
	public static void init() {
		logger.setParent(FMLLog.getLogger());
	}
	
	public static void log(Level level, String message) {
		logger.log(level, message);
	}
	
	public static void config(String message) {
		logger.log(Level.CONFIG, message);
	}
	
	public static void fine(String message) {
		logger.log(Level.FINE, message);
	}
	
	public static void finer(String message) {
		logger.log(Level.FINER, message);
	}
	
	public static void finest(String message) {
		logger.log(Level.FINEST, message);
	}
	
	public static void info(String message) {
		logger.log(Level.INFO, message);
	}
	
	public static void severe(String message) {
		logger.log(Level.SEVERE, message);
	}
	
	public static void warning(String message) {
		logger.log(Level.WARNING, message);
	}
}
