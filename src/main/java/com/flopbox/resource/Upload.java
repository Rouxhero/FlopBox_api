package com.flopbox.resource;

import java.io.InputStream;

import jakarta.ws.rs.*;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.flopbox.app.controller.core.ControllerFactory;
import com.flopbox.app.util.web.WebRequest;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * <h1>Class Upload</h1>
 *
 * Cette ressource permet de gérer les uploads
 *
 * @author Leo Le Van Canhd dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
@Path("server/{sid}/upload")
public class Upload extends AbstractResources {
	/**
	 * Constructeur de la classe
	 */
	public Upload() {
		super(ControllerFactory.Upload);
	}

	/**
	 * Upload un fichier sur la racine
	 *
	 * @param request             la requête HTTP
	 * @param uploadedInputStream Le fichier => Si upload de fichier
	 * @param fileDetails         Les détails du fichier => Si upload de fichier
	 * @param name                Le nom du dossier => Si création de dossier
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response create(@BeanParam WebRequest request, @FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetails)
			throws Exception {
		request.read();
		request.addFile(uploadedInputStream, fileDetails);
		return controller.execute("uploadFile", request, true);
	}

	/**
	 * Upload un fichier dans fid
	 *
	 * @param request             la requête HTTP
	 * @param uploadedInputStream Le fichier => Si upload de fichier
	 * @param fileDetails         Les détails du fichier => Si upload de fichier
	 * @param name                Le nom du dossier => Si création de dossier
	 * @throws Exception
	 */
	@POST
	@Path("{fid}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOn(@BeanParam WebRequest request, @FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetails)
			throws Exception {
		request.read();
		request.addFile(uploadedInputStream, fileDetails);
		return controller.execute("uploadFile", request, true);
	}

	/**
	 * Créer un dossier dans fid
	 *
	 * @param request la requête HTTP
	 * @param name    Le nom du dossier => Si création de dossier
	 * @throws Exception
	 */
	@POST
	@Path("/f/{fid}")
	public Response createFolder(@BeanParam WebRequest request, @FormParam("name") String name) throws Exception {
		request.read();
		request.set("name", name);
		return controller.execute("createFolder", request, true);
	}
	@POST
	@Path("/f/")
	public Response createFolderRoot(@BeanParam WebRequest request, @FormParam("name") String name) throws Exception {
		request.read();
		request.set("name", name);
		return controller.execute("createFolder", request, true);
	}
}
