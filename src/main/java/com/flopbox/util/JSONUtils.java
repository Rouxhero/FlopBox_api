package com.flopbox.util;

import org.apache.commons.net.ftp.FTPFile;

import com.google.gson.JsonObject;

/**
 * <h1>Class JSONUtils</h1>
 *
 * Cette classe permet de gerer le format JSON
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 *
 * @see JsonObject
 */
public class JSONUtils {

	/**
	 * Cette methode permet d'extraire le contenu d'un fichier
	 *
	 * @param jsonObject le JsonObject
	 * @param file       le fichier
	 * @param uniqueId   l'identifiant unique du fichier
	 */
	public static void extract_contentJson(JsonObject jsonObject, FTPFile file, String uniqueId) {
		JsonObject fileJson = new JsonObject();
		fileJson.addProperty("id", uniqueId);
		fileJson.addProperty("name", file.getName());
		fileJson.addProperty("size", file.getSize());
		fileJson.addProperty("type", file.getType());
		fileJson.addProperty("date", file.getTimestamp().getTime().toString());
		jsonObject.add(file.getName(), fileJson);
	}

	/**
	 * Cette methode permet de lister le contenu d'un dossier
	 *
	 * @param files le contenu du dossier
	 * @param root  le chemin du dossier
	 */
	public static String listContentJson(FTPFile[] files, String root) {
		JsonObject jsonObject = new JsonObject();
		for (FTPFile file : files) {
			String uniqueID = FTPUtils.generateUniqueID(root, file);
			extract_contentJson(jsonObject, file, uniqueID);
		}
		return jsonObject.toString();
	}
}
