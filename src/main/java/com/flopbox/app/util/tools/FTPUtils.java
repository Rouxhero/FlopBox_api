package com.flopbox.app.util.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.flopbox.app.util.exception.FTPException;
import com.flopbox.app.util.web.WebRequest;
import com.flopbox.app.util.web.WebResponse;

import jakarta.ws.rs.core.MediaType;

/**
 * <h1>Class FTPUtils</h1>
 *
 * Cette classe permet de simplifier le code des ressources en regroupant les
 * fonctions de manipulation des fichiers FTP
 *
 * @author Leo Le Van Canhd dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
public class FTPUtils {

	/**
	 * Cette fonction permet de changer de dossier FTP
	 *
	 * @param request requête HTTP
	 * @param client  client FTP
	 * @throws IOException
	 */
	public static void changeDirectory(WebRequest request, FTPClient client) throws IOException {
		String path = decode_path(request.get("fid"));
		client.changeWorkingDirectory(path);
	}

	/**
	 * Cette fonction permet de creer un dossier FTP
	 *
	 * @param request requête HTTP
	 * @param client  client FTP
	 * @throws IOException
	 * @throws FTPException
	 */
	public static void createFolder(WebRequest request, FTPClient client) throws IOException, FTPException {
		if (!client.makeDirectory(request.get("name")))
			throw new FTPException("Failed to create folder " + request.get("name") + " => " + client.getReplyString());
	}

	/**
	 * Cette fonction permet de decoder l'id unique d'une ressource FTP
	 *
	 * @param id id unique
	 * @return chemin du fichier FTP
	 */
	public static String decode_path(String id) {
		String path = id;
		if (path == null)
			return "/";
		byte[] decodedBytes = Base64.getDecoder().decode(path);
		path = new String(decodedBytes);
		return path;
	}

	/**
	 * Cette fonction permet de supprimer un fichier FTP
	 *
	 * @param request requête HTTP
	 * @param client  client FTP
	 * @throws IOException
	 * @throws FTPException
	 */
	public static void deleteFile(WebRequest request, FTPClient client) throws IOException, FTPException {
		String path = decode_path(request.get("fid"));
		if (!client.deleteFile(path))
			throw new FTPException("Failed to delete file " + path + " => " + client.getReplyString());
	}

	/**
	 * Cette fonction permet de supprimer un dossier FTP
	 *
	 * @param request
	 * @param client
	 * @throws IOException
	 * @throws FTPException
	 */
	public static void deleteFolder(WebRequest request, FTPClient client) throws IOException, FTPException {
		String path = decode_path(request.get("fid"));
		if (!client.removeDirectory(path))
			throw new FTPException("Failed to delete folder " + path + " => " + client.getReplyString());
	}

	/**
	 * Cette fonction permet de generer l'id unique d'une ressource FTP
	 *
	 * @param root racine du serveur FTP
	 * @param file fichier FTP
	 */
	public static String generateUniqueID(String root, FTPFile file) {
		String path = root + "/" + file.getName();
		path = path.replace("//", "/");
		return Base64.getEncoder().encodeToString(path.getBytes());
	}

	/**
	 * Cette fonction permet d'initialiser un client FTP
	 *
	 * @param request requête HTTP
	 * @return client FTP
	 * @throws FTPException
	 */
	public static FTPClient init_auth(WebRequest request) throws FTPException {
		try {
			String line = ServerUtils.getConfig(request);
			String[] config = line.split(":");
			FTPClient client = new FTPClient();
			client.connect(config[0], Integer.parseInt(config[1]));
			if (client.login(request.get("user"), request.get("pass"))) {
				client.changeWorkingDirectory("/");
				return client;
			} else {
				throw new FTPException("Login failed");
			}
		} catch (Exception e) {
			throw new FTPException(e.getMessage());
		}
	}

	/**
	 * Cette fonction permet de lister le contenu d'un dossier FTP
	 *
	 * @param request  requête HTTP
	 * @param response réponse HTTP
	 * @param client   client FTP
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static void listContent(WebRequest request, WebResponse response, FTPClient client)
			throws IOException, ParserConfigurationException {
		client.enterLocalPassiveMode();
		FTPFile[] files = client.listFiles();
		if ((request.isAcceptedType(MediaType.APPLICATION_XML) || request.isAcceptedType(MediaType.TEXT_XML))
				&& !request.isAcceptedType(MediaType.APPLICATION_JSON)) {
			response.setType(MediaType.APPLICATION_XML);
			response.setContent(XMLUtils.listContentXML(files, client.printWorkingDirectory()));
		} else {
			response.setType(MediaType.APPLICATION_JSON);
			response.setContent(JSONUtils.listContentJson(files, client));
		}
	}

	/**
	 * Cette fonction permet de lire un fichier FTP
	 *
	 * @param request  requête HTTP
	 * @param response réponse HTTP
	 * @param client   client FTP
	 * @throws IOException
	 * @throws FTPException
	 */
	public static void readFile(WebRequest request, WebResponse response, FTPClient client)
			throws IOException, FTPException {
		String path = decode_path(request.get("fid"));
		FTPFile file = client.mlistFile(path);
		if (file == null)
			throw new FTPException("File not found");
		InputStream inputStream = client.retrieveFileStream(path);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}
		inputStream.close();
		client.logout();
		client.disconnect();
		response.setType(TypeUtils.set_type(path));
		response.setContent(outputStream.toByteArray());
		response.setAttachmentName(file.getName());
	}

	/**
	 * Cette fonction permet de renommer un fichier FTP
	 *
	 * @param request requête HTTP
	 * @param client  client FTP
	 * @throws IOException
	 * @throws FTPException
	 */
	public static void renameFile(WebRequest request, FTPClient client) throws IOException, FTPException {
		String path = decode_path(request.get("fid"));
		String name = request.get("name");
		String[] split = path.split("/");
		split[split.length - 1] = name;
		name = String.join("/", split);
		System.out.println(name);
		System.out.println(path);
		if (!client.rename(path, name))
			throw new FTPException("Failed to rename file " + path + " => " + client.getReplyString());
	}

	/**
	 * Cette fonction permet de renommer un dossier FTP
	 *
	 * @param request requête HTTP
	 * @param client  client FTP
	 * @throws IOException
	 * @throws FTPException
	 */
	public static void renameFolder(WebRequest request, FTPClient client) throws IOException, FTPException {
		String path = decode_path(request.get("fid"));
		String name = request.get("name");
		String[] split = path.split("/");
		split[split.length - 1] = name;
		name = String.join("/", split);
		System.out.println(name);
		System.out.println(path);
		if (!client.rename(path, name))
			throw new FTPException("Failed to rename file " + path + " => " + client.getReplyString());
	}

	/**
	 * Cette fonction permet de telecharger un fichier FTP
	 *
	 * @param client      client FTP
	 * @param fileContent Stream avec le contenu du fichier
	 * @param fileDetails détails du fichier
	 * @return
	 * @throws IOException
	 * @throws FTPException
	 */
	public static String uploadFile(FTPClient client, InputStream fileContent, FormDataContentDisposition fileDetails)
			throws IOException, FTPException {
		if (!client.storeFile(fileDetails.getFileName(), fileContent))
			throw new FTPException(
					"Failed to upload file " + fileDetails.getFileName() + " => " + client.getReplyString());
		return client.getModificationTime(fileDetails.getFileName());
	}

	public static String overrideFile(FTPClient client, InputStream fileContent, FormDataContentDisposition fileDetails, String path) throws FTPException, IOException {
		if (!client.storeFile(path, fileContent))
			throw new FTPException(
					"Failed to upload file " + path + " => " + client.getReplyString());
		return client.getModificationTime(fileDetails.getFileName());
	}
}