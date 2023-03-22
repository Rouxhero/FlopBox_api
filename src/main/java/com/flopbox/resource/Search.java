package com.flopbox.resource;

import com.flopbox.app.controller.core.ControllerFactory;
import com.flopbox.app.util.web.WebRequest;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * <h1>Class Search</h1>
 *
 * Cette ressource permet de gérer les recherches
 *
 *
 * @author Leo Le Van Canhd dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
@Path("server/search")
public class Search extends AbstractResources {

	/**
	 * Constructeur de la classe
	 */
	public Search() {
		super(ControllerFactory.Search);
	}

	/**
	 * Recherche un fichier
	 *
	 * @param request la requête HTTP
	 * @throws Exception
	 */
	@GET
	public Response search(@BeanParam WebRequest request) throws Exception {
		request.read();
		return controller.execute("search", request, true);
	}
}
