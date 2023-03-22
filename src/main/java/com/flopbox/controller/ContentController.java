package com.flopbox.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.net.ftp.FTPClient;

import com.flopbox.controller.core.AbstractController;
import com.flopbox.controller.core.Controller;
import com.flopbox.exception.FTPException;
import com.flopbox.server.ServerCache;
import com.flopbox.util.FTPUtils;
import com.flopbox.web.WebRequest;
import com.flopbox.web.WebResponse;

import jakarta.ws.rs.core.Response;

/**
 * <h1>class GuestController</h1>
 *
 * Permet de repondre au besoin des ressources {@link com.flopbox.resource.File}
 * et {@link com.flopbox.resource.Folder}
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
public class ContentController extends AbstractController {
	/**
	 * Instance du controller
	 */
	private static Controller instance = null;

	/**
	 * Retourne l'instance du controller
	 *
	 * @return
	 */
	public static Controller getInstance() {
		if (instance == null) {
			instance = new ContentController();
		}
		return instance;
	}

	/**
	 * Constructeur de la classe
	 */
	public ContentController() {
		super();
	}

	/**
	 * Permet de supprimer un fichier
	 *
	 * @param request la requete HTTP
	 * @return
	 */
	public WebResponse deleteFile(WebRequest request) {
		WebResponse response = new WebResponse();
		try {
			FTPClient client = FTPUtils.init_auth(request);
			FTPUtils.changeDirectory(request, client);
			FTPUtils.deleteFile(request, client);
			response.setStatus(Response.Status.OK);
			client.disconnect();
		} catch (IOException | FTPException e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
		}
		return response;
	}

	/**
	 * Permet de supprimer un dossier
	 *
	 * @param request
	 * @return
	 */
	public WebResponse deleteFolder(WebRequest request) {
		WebResponse response = new WebResponse();
		try {
			FTPClient client = FTPUtils.init_auth(request);
			FTPUtils.changeDirectory(request, client);
			FTPUtils.deleteFolder(request, client);
			response.setStatus(Response.Status.OK);
			client.disconnect();
		} catch (IOException | FTPException e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
		}
		return response;
	}

	/**
	 * Permet de lister le contenu d'un fichier
	 *
	 * @param request la requete HTTP
	 * @return
	 */
	public WebResponse listContent(WebRequest request) {
		WebResponse response = new WebResponse();
		try {
			if (!ServerCache.isCached(request)) {
				FTPClient client = FTPUtils.init_auth(request);
				FTPUtils.changeDirectory(request, client);
				FTPUtils.readFile(request, response, client);
				response.setStatus(Response.Status.OK);
				client.disconnect();

			} else {
				response.setStatus(Response.Status.OK);
				ServerCache.getFromCache(request, response);
			}
		} catch (IOException | FTPException | ClassNotFoundException e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
			response.setContent(e.getMessage());
		}
		return response;
	}

	/**
	 * Permet de lister le contenu d'un dossier
	 *
	 * @param request la requete HTTP
	 * @return
	 */
	public WebResponse listFolder(WebRequest request) {
		WebResponse response = new WebResponse();
		try {
			FTPClient client = FTPUtils.init_auth(request);
			FTPUtils.changeDirectory(request, client);
			FTPUtils.listContent(request, response, client);
			response.setStatus(Response.Status.OK);
			client.disconnect();
		} catch (IOException | ParserConfigurationException | FTPException e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
		}
		return response;
	}

	/**
	 * Permet de renommer un fichier
	 *
	 * @param request la requete HTTP
	 * @return
	 */
	public WebResponse renameFile(WebRequest request) {
		WebResponse response = new WebResponse();
		try {
			FTPClient client = FTPUtils.init_auth(request);
			FTPUtils.changeDirectory(request, client);
			FTPUtils.renameFile(request, client);
			response.setStatus(Response.Status.OK);
			client.disconnect();
		} catch (IOException | FTPException e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
		}
		return response;
	}

	/**
	 * Permet de renommer un dossier
	 *
	 * @param request
	 * @return
	 */
	public WebResponse renameFolder(WebRequest request) {
		WebResponse response = new WebResponse();
		try {
			FTPClient client = FTPUtils.init_auth(request);
			FTPUtils.changeDirectory(request, client);
			FTPUtils.renameFolder(request, client);
			response.setStatus(Response.Status.OK);
			client.disconnect();
		} catch (IOException | FTPException e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
		}
		return response;
	}

}
