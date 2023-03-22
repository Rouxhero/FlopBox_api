package com.flopbox.app.util.tools;

import jakarta.ws.rs.core.MediaType;

/**
 * <h1>Class TypeUtils</h1>
 *
 * La classe `TypeUtils` permet de gérer les types des fichiers
 *
 * @author Leo Le Van Canhd dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
public class TypeUtils {

	/**
	 * Retourne le type d'un fichier
	 *
	 * @param name l'extension du fichier
	 * @return
	 */
	public static MediaType get_type(String name) {
		switch (name) {
		case "txt":
		case "md":
		case "js":
			return MediaType.TEXT_PLAIN_TYPE;
		case "html":
			return MediaType.TEXT_HTML_TYPE;
		case "json":
			return MediaType.APPLICATION_JSON_TYPE;
		case "xml":
			return MediaType.APPLICATION_XML_TYPE;
		default:
			return MediaType.APPLICATION_OCTET_STREAM_TYPE;
		}
	}

	/**
	 * Retourne le type d'un fichier
	 *
	 * @param file le fichier
	 * @return le type du fichier
	 */
	public static String set_type(String file) {
		String[] split = file.split("\\.");
		String extension = split[split.length - 1];
		return String.valueOf(get_type(extension));
	}
}
