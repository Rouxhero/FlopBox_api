package com.flopbox.util;

import static com.flopbox.server.ServerCredential.update_user_server;

import java.io.IOException;
import java.util.List;

import com.flopbox.exception.ControllerException;
import com.flopbox.exception.FTPException;
import com.flopbox.server.CSVDataBase;
import com.flopbox.server.ServerCredential;
import com.flopbox.web.WebRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

/**
 * <h1>Class ServerUtils</h1>
 *
 * Cette classe permet de gerer l'access au serveurs
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 *
 */
public class ServerUtils {

	/**
	 * Ajoute un serveur à l'utilisateur
	 *
	 * @param request
	 * @throws IOException
	 * @throws ControllerException
	 * @throws CsvException
	 */
	public static void addServer(WebRequest request) throws IOException, ControllerException, CsvException {
		int line_number = check_serverExist(request.get("host"));
		update_user_server(request, String.valueOf(line_number));
	}

	/**
	 * Verifie si le serveur existe dans la base de donnees
	 *
	 * @param line l'adresse du serveur
	 * @throws IOException
	 * @throws ControllerException
	 * @throws CsvValidationException
	 */
	private static int check_serverExist(String line) throws IOException, ControllerException, CsvValidationException {
		CSVDataBase csvDataBase = new CSVDataBase(CSVDataBase.SERVER_FILE, CSVDataBase.SERVER_DB);
		List<String[]> data = csvDataBase.getRecordsWhere("host", line);
		if (data.size() == 0) {
			int id = csvDataBase.getRecords().size() - 1;
			csvDataBase.insertRecord(new String[] { String.valueOf(id), line, "21" });
			return id;
		}
		return Integer.parseInt(data.get(0)[0]);
	}

	/**
	 * Supprime un serveur de l'utilisateur
	 *
	 * @param request la requete HTTP, pour recuperer l'id du serveur
	 * @throws ControllerException
	 * @throws IOException
	 * @throws CsvException
	 */
	public static void deleteServer(WebRequest request) throws ControllerException, IOException, CsvException {
		ServerCredential.unBindServer(request);
	}

	/**
	 * Retourne les informations de connection au serveur
	 *
	 * @param request la requete HTTP, pour recuperer l'id du serveur
	 * @throws FTPException
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	public static String getConfig(WebRequest request) throws FTPException, IOException, CsvValidationException {
		CSVDataBase csvDataBase = new CSVDataBase(CSVDataBase.SERVER_FILE, CSVDataBase.SERVER_DB);
		String id = request.get("sid");
		if (id == null)
			throw new FTPException("id is null");
		List<String[]> data = csvDataBase.getRecordsWhere("id", id);
		if (data.size() == 0)
			throw new FTPException("id not found");
		return data.get(0)[1] + ":" + data.get(0)[2];
	}

	/**
	 * Retourne la liste des serveurs de l'utilisateur
	 *
	 * @param request la requete HTTP, pour recuperer l'id de l'utilisateur
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	public static String listServers(WebRequest request) throws IOException, CsvValidationException {
		JsonArray serversArray = new JsonArray();
		load_servers(request, serversArray);
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("servers", serversArray);
		return jsonObject.toString();
	}

	/**
	 * Charge les serveurs de l'utilisateur
	 *
	 * @param request      la requete HTTP, pour recuperer l'id de l'utilisateur
	 * @param serversArray le tableau JSON des serveurs
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	private static void load_servers(WebRequest request, JsonArray serversArray)
			throws IOException, CsvValidationException {
		JsonObject serverJson;
		List<String> servers = ServerCredential.get_user_servers(request);
		CSVDataBase csvDataBase = new CSVDataBase(CSVDataBase.SERVER_FILE, CSVDataBase.SERVER_DB);
		for (String server : servers) {
			List<String[]> recordList = csvDataBase.getRecordsWhere("id", server);
			if (recordList.size() == 0)
				continue;
			String[] record = recordList.get(0);
			serverJson = new JsonObject();
			serverJson.addProperty("id", record[0]);
			serverJson.addProperty("host", record[1]);
			serverJson.addProperty("port", record[2]);
			serversArray.add(serverJson);
		}
	}
}