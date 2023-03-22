package com.flopbox.app.util.web;

import com.flopbox.app.util.tools.Logs;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * <h1>Class WebResponse</h1>
 *
 * Cette classe permet de récupérer de generer une réponse pour une l'API REST.
 * Elle permet de generer une réponse avec un type, un contenu, un status et un
 * nom de fichier, en simplifiant la creation de la reponse.
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 *
 */
public class WebResponse {

	public static Response buildResponse(Response.Status status, String message) {
		return Response.status(status).entity(message).type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Correspond au type de la réponse
	 *
	 * @see jakarta.ws.rs.core.MediaType
	 */
	private String type;
	/**
	 * Correspond au contenu de la réponse
	 */
	private Object content;
	/**
	 * Correspond au status de la réponse
	 *
	 * @see jakarta.ws.rs.core.Response.Status
	 */
	private Response.Status status;

	/**
	 * Correspond au nom du fichier à télécharger(si la réponse est un fichier)
	 */
	private String attachmentName;

	/**
	 * Constructeur, qui set un reponse type JSON vide avec le status OK
	 */
	public WebResponse() {
		this.type = MediaType.TEXT_PLAIN;
		this.content = "";
		this.status = Response.Status.OK;
		this.attachmentName = null;
	}

	/**
	 * Permet de construire la réponse, en fonction de la presence ou non d'un nom
	 * de fichier à télécharger
	 *
	 * @see jakarta.ws.rs.core.Response
	 */
	public Response build() {
		Logs.display(Logs.DEBUG, "Return response: " + getStatus() + " <" + getType() + ">");
		if (attachmentName != null)
			return Response.status(getStatus()).entity(getContent()).type(getType())
					.header("Content-Disposition", "attachment; filename=\"" + getAttachmentName() + "\"").build();
		else
			return Response.status(getStatus()).entity(getContent()).type(getType()).build();
	}

	/**
	 * Permet de récupérer le nom du fichier à télécharger
	 *
	 * @return le nom du fichier à télécharger
	 */
	public String getAttachmentName() {
		return attachmentName;
	}

	/**
	 * Permet de récupérer le contenu de la réponse
	 *
	 * @return le contenu de la réponse
	 */
	public Object getContent() {
		return content;
	}

	/**
	 * Permet de récupérer le status de la réponse
	 *
	 * @return le status de la réponse
	 */
	public Response.Status getStatus() {
		return status;
	}

	/**
	 * Permet de récupérer le type de la réponse
	 *
	 * @return le type de la réponse
	 */
	public String getType() {
		return type;
	}

	/**
	 * Permet de set le nom du fichier à télécharger
	 *
	 * @param name le nom du fichier à télécharger
	 */
	public void setAttachmentName(String name) {
		this.attachmentName = name;
	}

	/**
	 * Permet de set le contenu de la réponse
	 *
	 * @param content le contenu de la réponse
	 */
	public void setContent(Object content) {
		this.content = content;
	}

	/**
	 * Permet de set le status de la réponse
	 *
	 * @param status le status de la réponse
	 */
	public void setStatus(Response.Status status) {
		this.status = status;
	}

	/**
	 * Getter / Setter
	 */
	/**
	 * Permet de set le type de la réponse
	 *
	 * @param type le type de la réponse
	 */
	public void setType(String type) {
		this.type = type;
	}

}
