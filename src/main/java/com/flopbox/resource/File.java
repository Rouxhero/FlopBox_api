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
 * <h1>Class File</h1>
 *
 * Cette ressource permet de gérer les fichiers sur les serveurs FTP
 *
 * @author Leo Le Van Canhd dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
@Path("server/{sid}/file/{fid}")
public class File extends AbstractResources {
	/**
	 * Constructeur de la classe
	 */
	public File() {
		super(ControllerFactory.Content);
	}

	/**
	 * Supprime un fichier
	 *
	 * @param request la requête HTTP
	 * @throws Exception
	 */
	@DELETE
	public Response deleteFile(@BeanParam WebRequest request) throws Exception {
		request.read();
		return controller.execute("deleteFile", request, true);
	}

	/**
	 * Récupère le contenu d'un fichier
	 *
	 * @param request la requête HTTP
	 * @throws Exception
	 */
	@GET
	public Response listFolder(@BeanParam WebRequest request) throws Exception {
		request.read();
		return controller.execute("listContent", request, true);
	}

	/**
	 * renomme un fichier
	 *
	 * @param request la requête HTTP
	 * @param name    le nouveau nom du fichier
	 * @throws Exception
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response renameFile(@BeanParam WebRequest request, @FormParam("name") String name) throws Exception {
		request.read();
		request.set("name", name);
		return controller.execute("renameFile", request, true);
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(@BeanParam WebRequest request, @FormDataParam("file") InputStream uploadedInputStream,
						   @FormDataParam("file") FormDataContentDisposition fileDetails)
			throws Exception {
		request.read();
		request.addFile(uploadedInputStream, fileDetails);
			return controller.execute("uploadFile", request, true);
	}
}
