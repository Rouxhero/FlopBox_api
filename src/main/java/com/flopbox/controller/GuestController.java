package com.flopbox.controller;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.codec.digest.DigestUtils;

import com.flopbox.controller.core.AbstractController;
import com.flopbox.controller.core.Controller;
import com.flopbox.server.ServerCredential;
import com.flopbox.web.WebRequest;
import com.flopbox.web.WebResponse;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * <h1>class GuestController</h1>
 *
 * Permet de repondre au besoin de la ressource
 * {@link com.flopbox.resource.Guest}
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
public class GuestController extends AbstractController {

	/**
	 * Instance du controller
	 */
	private static Controller instance = null;

	/**
	 * Retourne l'instance du controller
	 *
	 * @return l'instance du controller
	 */
	public static Controller getInstance() {
		if (instance == null) {
			instance = new GuestController();
		}
		return instance;
	}

	/**
	 * Constructeur de la classe
	 */
	public GuestController() {
		super();
	}

	private boolean hash_key(WebRequest request) {
		return request.check(Collections.singletonList("key"));
	}

	/**
	 * Permet de login un utilisateur
	 *
	 * @param request la requete HTTP
	 * @return
	 */
	public WebResponse loginUser(WebRequest request) {
		WebResponse response = new WebResponse();
		if (!request.check(Arrays.asList("username", "password"))) {
			response.setStatus(Response.Status.BAD_REQUEST);
			response.setContent("Missing parameters");
			return response;
		}
		try {
			String message = ServerCredential.login(request);
			if (message != null) {
				response.setStatus(Response.Status.OK);
				response.setContent(message);
			} else {
				response.setStatus(Response.Status.BAD_REQUEST);
				response.setContent("Invalid credentials");
			}
		} catch (Exception e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
		}
		return response;
	}

	/**
	 * Permet de register un utilisateur
	 *
	 * @param request la requete HTTP
	 * @return
	 */
	public WebResponse registerUser(WebRequest request) {
		WebResponse response = new WebResponse();
		response.setType(MediaType.TEXT_PLAIN);
		if (hash_key(request)) {
			response.setStatus(Response.Status.BAD_REQUEST);
			response.setContent("Access denied");
			return response;
		}
		if (!request.check(Arrays.asList("username", "password", "email"))) {
			response.setStatus(Response.Status.BAD_REQUEST);
			response.setContent("Missing parameters");
			return response;
		}
		try {
			ServerCredential.registerUser(request);
			response.setStatus(Response.Status.OK);
			response.setContent(
					DigestUtils.sha256Hex(request.get("username") + request.get("password") + request.get("email")));
			return response;
		} catch (Exception e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
			return response;
		}
	}
}
