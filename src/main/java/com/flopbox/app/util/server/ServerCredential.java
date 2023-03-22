package com.flopbox.app.util.server;

import static com.flopbox.app.util.server.CSVDataBase.USER_DB;
import static com.flopbox.app.util.server.CSVDataBase.USER_FILE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import com.flopbox.app.util.exception.ControllerException;
import com.flopbox.app.util.web.WebRequest;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

/**
 * <h1>Class ServerCredential</h1>
 *
 * Cette classe permet de gérer les opérations liées aux access utilisateurs.
 * Elle permet de gérer l'authentification et l'inscription des utilisateurs.
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 *
 */
public class ServerCredential {

	/**
	 * Message d'erreur lors de l'authentification.
	 */
	public final static String AUTH_FAIL = "Authentication failed";
	// Operation

	/**
	 * Cette méthode permet de vérifier si un utilisateur est authentifié (Avec la
	 * clef d'API).
	 *
	 * @param request La requête contenant les informations de l'utilisateur.
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	public static boolean check_auth(WebRequest request) throws IOException, CsvValidationException {
		CSVDataBase csvDataBase = new CSVDataBase(USER_FILE, USER_DB);
		String key = request.get("key");
		String sid = request.get("sid");
		List<String[]> record = csvDataBase.getRecordsWhere("key", key);
		if (record.size() == 0)
			return false;
		String[] recordData = record.get(0);
		List<String> server = Arrays.asList(recordData[4].split("\\|"));
		return server.contains(sid) || sid == null;
	}

	/**
	 * Cette méthode permet de récupérer les serveurs d'un utilisateur.
	 *
	 * @param request La requête contenant les informations de l'utilisateur.
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	public static List<String> get_user_servers(WebRequest request) throws IOException, CsvValidationException {
		CSVDataBase csvDataBase = new CSVDataBase(USER_FILE, USER_DB);
		String key = request.get("key");
		String[] record = csvDataBase.getRecordsWhere("key", key).get(0);
		List<String> server = Arrays.asList(record[4].split("\\|"));
		List<String> server2 = new ArrayList<>();
		for (int i = 0; i < server.size(); i++) {
			if (!server.get(i).equals(""))
				server2.add(server.get(i));
		}
		return server2;
	}

	/**
	 * Cette méthode permet de vérifier les informations d'authentification d'un
	 * utilisateur.
	 *
	 * @param request La requête contenant les informations de l'utilisateur.
	 * @throws ControllerException
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	public static String login(WebRequest request) throws ControllerException, IOException, CsvValidationException {
		CSVDataBase csvDataBase = new CSVDataBase(USER_FILE, USER_DB);
		String username = request.get("username");
		String password = request.get("password");
		String[] record = csvDataBase.getRecordsWhere("username", username).get(0);
		return DigestUtils.sha256Hex(password).equals(record[1]) ? record[3] : null;
	}

	/**
	 * Cette méthode permet d'enregistrer un utilisateur.
	 *
	 * @param request La requête contenant les informations de l'utilisateur.
	 * @throws ControllerException
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	public static boolean registerUser(WebRequest request)
			throws ControllerException, IOException, CsvValidationException {
		String username = request.get("username");
		String password = request.get("password");
		String email = request.get("email");
		CSVDataBase csvDataBase = new CSVDataBase(USER_FILE, USER_DB);
		if (csvDataBase.getRecordsWhere("username", username).size() > 0)
			throw new ControllerException("Username already exists");
		String hashPass = DigestUtils.sha256Hex(password);
		String hash = DigestUtils.sha256Hex(username + password + email);
		csvDataBase.insertRecord(new String[] { username, hashPass, email, hash, "" });
		return true;
	}

	/**
	 * Cette méthode permet de supprimer un serveur d'un utilisateur.
	 *
	 * @param request La requête contenant les informations de l'utilisateur.
	 * @throws ControllerException
	 * @throws IOException
	 * @throws CsvException
	 */
	public static void unBindServer(WebRequest request) throws ControllerException, IOException, CsvException {
		CSVDataBase csvDataBase = new CSVDataBase(USER_FILE, USER_DB);
		String key = request.get("key");
		String sid = request.get("sid");
		String[] record = csvDataBase.getRecordsWhere("key", key).get(0);
		String[] new_record = record.clone();
		List<String> server = new ArrayList<>(Arrays.asList(record[4].split("\\|")));
		if (server.contains(sid)) {
			server.remove(sid);
			new_record[4] = String.join("|", server);
			csvDataBase.updateRecord(record, new_record, 3);
		}

	}

	/**
	 * Cette méthode permet de mettre à jour les informations d'un utilisateur.
	 *
	 * @param request   La requête contenant les informations de l'utilisateur.
	 * @param server_id L'id du serveur.
	 * @throws ControllerException
	 * @throws IOException
	 * @throws CsvException
	 */
	public static void update_user_server(WebRequest request, String server_id)
			throws ControllerException, IOException, CsvException {
		CSVDataBase csvDataBase = new CSVDataBase(USER_FILE, USER_DB);
		String key = request.get("key");
		String[] record = csvDataBase.getRecordsWhere("key", key).get(0);
		String[] new_record = record.clone();
		List<String> server = new ArrayList<>(Arrays.asList(record[4].split("\\|")));
		if (!server.contains(server_id)) {
			server.add(server_id);
			new_record[4] = String.join("|", server);
			csvDataBase.updateRecord(record, new_record, 3);
		}
	}
}
