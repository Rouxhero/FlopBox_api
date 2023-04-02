package com.flopbox.app.util.tools;

/**
 * <h1>Class Logs</h1>
 *
 * Cette classe permet de gérer les logs
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 *
 */
public class Logs {

	/**
	 * Niveau de log DEBUG
	 */
	public static final int DEBUG = 0;
	/**
	 * Niveau de log INFO
	 */
	public static final int INFO = 1;
	/**
	 * Niveau de log WARNING
	 */
	public static final int WARNING = 2;
	/**
	 * Niveau de log ERROR
	 */
	public static final int ERROR = 3;

	/**
	 * Affiche un message de log
	 *
	 * @param level le niveau de log
	 * @param msg   le message
	 */
	public static void display(int level, String msg) {
		switch (level) {
		case DEBUG:
			display("[DEBUG] " + msg);
			break;
		case INFO:
			display("[INFO] " + msg);
			break;
		case WARNING:
			display("[WARNING] " + msg);
			break;
		case ERROR:
			display("[ERROR] " + msg);
			break;
		default:
			display("[UNKNOWN] " + msg);
			break;
		}
	}

	/**
	 * Affiche un message de log
	 *
	 * @param msg le message
	 */
	public static void display(String msg) {
		System.out.println(msg);
	}
}
