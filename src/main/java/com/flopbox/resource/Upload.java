package com.flopbox.resource;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.flopbox.controller.core.ControllerFactory;
import com.flopbox.web.WebRequest;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
			@FormDataParam("file") FormDataContentDisposition fileDetails, @QueryParam("name") String name)
			throws Exception {
		request.read();
		request.addFile(uploadedInputStream, fileDetails);
		request.set("name", name);
		if (uploadedInputStream != null)
			return controller.execute("uploadFile", request, true);
		else
			return controller.execute("createFolder", request, true);
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
			@FormDataParam("file") FormDataContentDisposition fileDetails, @QueryParam("name") String name)
			throws Exception {
		request.read();
		request.addFile(uploadedInputStream, fileDetails);
		request.set("name", name);
		if (uploadedInputStream != null)
			return controller.execute("uploadFile", request, true);
		else
			return controller.execute("createFolder", request, true);
	}
}
