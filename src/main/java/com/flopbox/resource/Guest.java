package com.flopbox.resource;

import com.flopbox.controller.core.ControllerFactory;
import com.flopbox.web.WebRequest;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 *
 * <h1>Class Guest</h1>
 *
 * Cette ressource permet de gérer les utilisateurs
 *
 * @author Leo Le Van Canhd dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
@Path("auth")
public class Guest extends AbstractResources {

	/**
	 * Constructeur de la classe
	 */
	public Guest() {
		super(ControllerFactory.GUEST);
	}

	/**
	 * Connecte un utilisateur
	 *
	 * @param request  la requête HTTP
	 * @param username Le paramètre POST username
	 * @param password Le paramètre POST password
	 * @throws Exception
	 */
	@POST
	@Path("login")
	public Response loginUser(@BeanParam WebRequest request, @FormParam("username") String username,
			@FormParam("password") String password) throws Exception {
		request.read();
		request.set("username", username);
		request.set("password", password);
		return controller.execute("loginUser", request, false);

	}

	/**
	 * Enregistre un utilisateur
	 *
	 * @param request  la requête HTTP
	 * @param email    Le paramètre POST email
	 * @param username Le paramètre POST username
	 * @param password Le paramètre POST password
	 * @throws Exception
	 */
	@POST
	@Path("register")
	public Response registerUser(@BeanParam WebRequest request, @FormParam("email") String email,
			@FormParam("username") String username, @FormParam("password") String password) throws Exception {
		request.read();
		request.set("email", email);
		request.set("username", username);
		request.set("password", password);
		return controller.execute("registerUser", request, false);

	}

}
