package com.flopbox.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;

import com.flopbox.util.TypeUtils;
import com.flopbox.web.WebRequest;
import com.flopbox.web.WebResponse;

/**
 * <h1>Class ServerCache</h1>
 *
 * Cette classe permet de gérer le cache du serveur. Elle permet de vérifier si
 * un fichier est déjà en cache et de le récupérer. Cela permet de ne pas
 * télécharger plusieurs fois le même fichier.
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 *
 */
public class ServerCache {

	/**
	 * Le chemin du cache.
	 */
	private static String path_cache = "cache/";

	/**
	 * Cette méthode permet d'ajouter un fichier au cache.
	 *
	 * @param request  La requête contenant le fichier à ajouter.
	 * @param response Le flux contenant le fichier à ajouter.
	 * @throws IOException
	 */
	public static void addToCache(WebRequest request, ByteArrayOutputStream response) throws IOException {
		String path = request.get("fid");
		byte[] decodedBytes = Base64.getDecoder().decode(path);
		String code = new String(decodedBytes);
		File file = new File(path_cache + DigestUtils.sha256Hex(request.get("sid") + "/" + code));
		// Write to disk with FileOutputStream
		FileOutputStream f_out = new FileOutputStream(file);
		f_out.write(response.toByteArray());

	}

	/**
	 * Cette méthode permet de récupérer un fichier du cache.
	 *
	 * @param request  La requête contenant le fichier à récupérer.
	 * @param response La réponse pour envoyer le fichier.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void getFromCache(WebRequest request, WebResponse response)
			throws IOException, ClassNotFoundException {
		String path = request.get("fid");
		byte[] decodedBytes = Base64.getDecoder().decode(path);
		String code = new String(decodedBytes);
		File file = new File(path_cache + DigestUtils.sha256Hex(request.get("sid") + "/" + code));
		FileInputStream fileInputStream = new FileInputStream(
				path_cache + DigestUtils.sha256Hex(request.get("sid") + "/" + code));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = fileInputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, length);
		}
		byte[] fileContent = outputStream.toByteArray();
		fileInputStream.close();
		response.setType(TypeUtils.set_type(code));
		response.setContent(fileContent);
		response.setAttachmentName(code);

	}

	/**
	 * Cette méthode permet de vérifier si un fichier est déjà en cache.
	 *
	 * @param request La requête contenant le fichier à vérifier.
	 */
	public static boolean isCached(WebRequest request) {
		String path = request.get("fid");
		byte[] decodedBytes = Base64.getDecoder().decode(path);
		String code = new String(decodedBytes);
		File file = new File(path_cache + DigestUtils.sha256Hex(request.get("sid") + "/" + code));
		return file.exists();
	}

}
