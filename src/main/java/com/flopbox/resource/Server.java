package com.flopbox.resource;

import com.flopbox.controller.core.ControllerFactory;
import com.flopbox.web.WebRequest;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * <h1>Class Server</h1>
 *
 * Cette ressource permet de gérer les serveurs
 *
 * @author Leo Le Van Canhd dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
@Path("server")
public class Server extends AbstractResources {
	/**
	 * Constructeur de la classe
	 */
	public Server() {
		super(ControllerFactory.SERVER);
	}

	@POST
	public Response addServer(@BeanParam WebRequest request, @FormParam("host") String host,
			@FormParam("port") String port) throws Exception {
		request.read();
		request.set("host", host);
		request.set("port", port);
		return controller.execute("addServer", request, true);
	}

	/**
	 * Supprime un serveur
	 *
	 * @param request
	 * @throws Exception
	 */
	@DELETE
	@Path("{sid}")
	public Response deleteServer(@BeanParam WebRequest request) throws Exception {
		request.read();
		return controller.execute("deleteServer", request, true);
	}

	@GET
	@Path("{sid}")
	public Response listRoot(@BeanParam WebRequest request) throws Exception {
		request.read();
		return controller.execute("listRoot", request, true);
	}

	@GET
	public Response listServer(@BeanParam WebRequest request) throws Exception {
		request.read();
		return controller.execute("listServer", request, true);
	}

}
