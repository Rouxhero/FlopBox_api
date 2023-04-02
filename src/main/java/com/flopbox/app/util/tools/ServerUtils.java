package com.flopbox.app.util.tools;

import static com.flopbox.app.util.server.ServerCredential.update_user_server;

import java.io.IOException;
import java.util.List;

import com.flopbox.app.util.exception.ControllerException;
import com.flopbox.app.util.exception.FTPException;
import com.flopbox.app.util.server.CSVDataBase;
import com.flopbox.app.util.server.ServerCredential;
import com.flopbox.app.util.web.WebRequest;
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
	 * @return
	 * @throws IOException
	 * @throws ControllerException
	 * @throws CsvException
	 */
	public static String addServer(WebRequest request) throws IOException, ControllerException, CsvException {
		int line_number = check_serverExist(request);
		update_user_server(request, String.valueOf(line_number));
		return String.valueOf(line_number);
	}

	/**
	 * Verifie si le serveur existe dans la base de donnees
	 *
	 * @param line l'adresse du serveur
	 * @throws IOException
	 * @throws ControllerException
	 * @throws CsvValidationException
	 */
	private static int check_serverExist(WebRequest request) throws IOException, ControllerException, CsvValidationException {
		CSVDataBase csvDataBase = new CSVDataBase(CSVDataBase.SERVER_FILE, CSVDataBase.SERVER_DB);
		List<String[]> data = csvDataBase.getRecordsWhere("alias", request.get("alias"));
		if (data.size() == 0) {
			int id = csvDataBase.getRecords().size() - 1;
			csvDataBase.insertRecord(new String[] { String.valueOf(id), request.get("alias"),request.get("host"), request.get("port") });
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
		return data.get(0)[2] + ":" + data.get(0)[3];
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
			serverJson.addProperty("alias", record[1]);
			serverJson.addProperty("host", record[2]);
			serverJson.addProperty("port", record[3]);
			serversArray.add(serverJson);
		}
	}
}
