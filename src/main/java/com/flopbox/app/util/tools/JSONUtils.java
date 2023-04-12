package com.flopbox.app.util.tools;

import com.google.gson.JsonArray;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.google.gson.JsonObject;
import org.glassfish.grizzly.compression.lzma.impl.Base;

import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
	public static void extract_contentJson(JsonArray jsonArray, FTPFile file, String uniqueId) {
		JsonObject fileJson = new JsonObject();
		fileJson.addProperty("id", uniqueId);
		fileJson.addProperty("name", file.getName());
		fileJson.addProperty("path", FTPUtils.decode_path(uniqueId));
		fileJson.addProperty("size", file.getSize());
		fileJson.addProperty("type", file.getType());
		Calendar calendar = file.getTimestamp();
		fileJson.addProperty("date", String.valueOf(calendar.getTimeInMillis()));
		jsonArray.add(fileJson);
	}

	/**
	 * Cette methode permet de lister le contenu d'un dossier
	 *
	 * @param files le contenu du dossier
	 * @param root  le chemin du dossier
	 */
	public static String listContentJson(FTPFile[] files, FTPClient client) throws IOException {
		JsonObject jsonObject = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		String root = client.printWorkingDirectory();
		for (FTPFile file : files) {

			String uniqueID = FTPUtils.generateUniqueID(root, file);

			String date = client.getModificationTime(FTPUtils.decode_path(uniqueID));
			if (date != null) {
				Calendar cal = Calendar.getInstance();
				String year = date.substring(0, 4); // 2018
				String month = date.substring(4, 6); // 01
				String day = date.substring(6, 8);
				String hour = date.substring(8, 10);
				String minute = date.substring(10, 12);
				String second = date.substring(12, 14);
				System.out.println(year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + second);
				cal.set(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day), Integer.parseInt(hour),
						Integer.parseInt(minute), Integer.parseInt(second));
				file.setTimestamp(cal);
			}
			extract_contentJson(jsonArray, file, uniqueID);
		}
		jsonObject.add("content", jsonArray);
		return jsonObject.toString();
	}

	public static Object mapToJson(Map<String, List<String>> res) {
		JsonObject jsonObject = new JsonObject();
		for (String key : res.keySet()) {
			JsonArray jsonArray = new JsonArray();
			for (String value : res.get(key)) {
				JsonObject jsonObject1 = new JsonObject();
				value = value.replaceAll("//","/");
				String uniqueID = Base64.getEncoder().encodeToString(value.getBytes());
				jsonObject1.addProperty("path", value);
				jsonObject1.addProperty("id", uniqueID);
				JsonArray jsonArray1 = new JsonArray();
				String[] split = value.split("/");
				for (String s : split) {
					if (s.equals(""))
						continue;
					s = "/" + s;
					jsonArray1.add(Base64.getEncoder().encodeToString(s.getBytes()));
				}
				jsonObject1.add("fullPath", jsonArray1);
				jsonArray.add(jsonObject1);
			}
			jsonObject.add(key, jsonArray);
		}
		return jsonObject.toString();
	}
}
