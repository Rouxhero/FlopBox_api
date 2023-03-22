package com.flopbox.app.util.web;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

/**
 * <h1>Class WebRequest</h1>
 *
 * Cette classe permet de récupérer les paramètres de la requête HTTP Elle
 * permet de simplifier le code des ressources en regroupant les paramètres de
 * la requête HTTP, La methode read() permet de récupérer les paramètres de la
 * requête HTTP et de les stocker dans un HashMap <i>content</i>
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 *
 * @see jakarta.ws.rs.BeanParam
 */
public class WebRequest {

	/**
	 * <i>HeaderParam</i> key, correspond à la clé de l'utilisateur
	 */
	@HeaderParam("key")
	String key;
	/**
	 * <i>HeaderParam</i> type, correspond au type accepté pour la réponse
	 */
	@HeaderParam("accept")
	String type;
	/**
	 * <i>HeaderParam</i> user, correspond au nom d'utilisateur FTP
	 */
	@HeaderParam("user")
	String user;
	/**
	 * <i>HeaderParam</i> pass, correspond au mot de passe FTP
	 */
	@HeaderParam("pass")
	String pass;
	/**
	 * <i>PathParam</i> sid, correspond à l'id du serveur FTP
	 */
	@PathParam("sid")
	String sid;
	/**
	 * <i>PathParam</i> fid, correspond à l'id du dossier ou fichier FTP
	 */
	@PathParam("fid")
	String fid;
	/**
	 * <i>QueryParam</i> length, correspond à la profondeur de recursivité
	 */
	@QueryParam("l")
	String length;
	/**
	 * <i>QueryParam</i> search, correspond à la chaine de caractère à rechercher
	 */
	@QueryParam("s")
	String search;
	/**
	 * Correspond à la liste des paramètres de la requête HTTP
	 */
	@Context
	UriInfo uriInfo;
	private Map<String, String> content;
	/**
	 * Correspond au flux de données du fichier à uploader
	 */
	private InputStream file;
	/**
	 * Correspond aux détails du fichier à uploader
	 */
	private FormDataContentDisposition fileDetails;

	/**
	 * Cette methode permet de sauvegarder le fichier à uploader
	 *
	 * @param uploadedInputStream le flux de données du fichier à uploader
	 * @param fileDetails         les détails du fichier à uploader
	 */
	public void addFile(InputStream uploadedInputStream, FormDataContentDisposition fileDetails) {
		this.file = uploadedInputStream;
		this.fileDetails = fileDetails;
	}

	/**
	 * Cette methode permet de vérifier si la requête HTTP contient tous les
	 * paramètres contenu dans la liste keys
	 *
	 * @param keys correspond à la liste des clés des paramètres
	 * @return true si la requête HTTP contient tous les paramètres, false sinon
	 */
	public boolean check(List<String> keys) {
		for (String key : keys) {
			if (!content.containsKey(key) || content.get(key) == null)
				return false;
		}
		return true;
	}

	/**
	 * Cette methode permet de récupérer la valeur d'un paramètre de la requête HTTP
	 *
	 * @param key correspond à la clé du paramètre
	 * @return la valeur du paramètre
	 */
	public String get(String key) {
		return content.get(key);
	}

	/**
	 * Cette methode permet de récupérer le flux de données du fichier à uploader
	 *
	 * @return le flux de données du fichier à uploader
	 */
	public InputStream getFile() {
		return file;
	}

	/**
	 * Cette methode permet de récupérer les détails du fichier à uploader
	 *
	 * @return les détails du fichier à uploader
	 */
	public FormDataContentDisposition getFileDetails() {
		return fileDetails;
	}

	/**
	 * Cette methode permet de vérifier si le type est accepté pour la reponse
	 *
	 * @param type correspond au type à vérifier
	 * @return true si le type est accepté, false sinon
	 */
	public boolean isAcceptedType(String type) {
		if (this.type == null || this.type.equals("*/*"))
			return true;
		String[] types = this.type.split(",");
		for (String t : types) {
			if (t.equals(type))
				return true;
		}
		return false;
	}

	/**
	 * Cette methode permet de récupérer les paramètres de la requête HTTP et de les
	 * stocker dans un HashMap <i>content</i>
	 */
	public void read() {
		content = new HashMap<>();
		set_content();
	}

	/**
	 * Cette methode permet de mettre à jour le contenu de la requête HTTP
	 *
	 * @param key   correspond à la clé du paramètre
	 * @param value correspond à la valeur du paramètre
	 */
	public void set(String key, String value) {
		content.put(key, value);
	}

	/**
	 * Cette methode permet de mettre à jour le contenu de la requête HTTP
	 */
	private void set_content() {
		content.put("key", key);
		content.put("user", user);
		content.put("pass", pass);
		content.put("sid", sid);
		content.put("fid", fid);
		content.put("type", type);
		content.put("length", length);
		content.put("search", search);
		if (uriInfo != null)
			content.put("uri", uriInfo.getRequestUri().toString());
		else
			content.put("uri", "");
	}

	public String getUri() {
		return content.get("uri");
	}
}
