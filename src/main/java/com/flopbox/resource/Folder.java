package com.flopbox.resource;

import com.flopbox.app.controller.core.ControllerFactory;
import com.flopbox.app.util.web.WebRequest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.InputStream;

/**
 *
 * <h1>Class Folder</h1>
 *
 * Cette ressource permet de gérer les dossiers sur les serveurs FTP
 *
 * @author Leo Le Van Canhd dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
@Path("server/{sid}/folder/{fid}")
public class Folder extends AbstractResources {
	/**
	 * Constructeur de la classe
	 */
	public Folder() {
		super(ControllerFactory.Content);
	}

	/**
	 * Supprime un dossier
	 *
	 * @param request la requête HTTP
	 * @throws Exception
	 */
	@DELETE
	public Response deleteFolder(@BeanParam WebRequest request) throws Exception {
		request.read();
		return controller.execute("deleteFolder", request, true);
	}

	/**
	 * Récupère le contenu d'un dossier
	 *
	 * @param request la requête HTTP
	 * @throws Exception
	 */
	@GET
	public Response listFolder(@BeanParam WebRequest request) throws Exception {
		request.read();
		return controller.execute("listFolder", request, true);
	}

	/**
	 * renomme un dossier
	 *
	 * @param request la requête HTTP
	 * @param name    le nouveau nom du dossier
	 * @throws Exception
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response renameFolder(@BeanParam WebRequest request, @FormParam("name") String name) throws Exception {
		request.read();
		request.set("name", name);
		return controller.execute("renameFolder", request, true);
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(@BeanParam WebRequest request, @FormParam("name") String name)
			throws Exception {
		request.read();
		request.set("name", name);
		return controller.execute("uploadFolder", request, true);
	}
}
