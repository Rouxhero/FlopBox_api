package com.flopbox.app.controller;

import java.io.IOException;
import java.io.InputStream;

import com.flopbox.app.util.tools.Logs;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.flopbox.app.controller.core.AbstractController;
import com.flopbox.app.controller.core.Controller;
import com.flopbox.app.util.exception.FTPException;
import com.flopbox.app.util.tools.FTPUtils;
import com.flopbox.app.util.web.WebRequest;
import com.flopbox.app.util.web.WebResponse;

import jakarta.ws.rs.core.Response;

public class UploadController extends AbstractController {

	private static Controller instance = null;

	public static Controller getInstance() {
		if (instance == null) {
			instance = new UploadController();
		}
		return instance;
	}

	public UploadController() {
		super();
	}

	public WebResponse createFolder(WebRequest request) {
		WebResponse response = new WebResponse();
		try {
			FTPClient client = FTPUtils.init_auth(request);
			FTPUtils.changeDirectory(request, client);
			response.setContent(FTPUtils.createFolder(request, client));
			response.setStatus(Response.Status.OK);
			client.disconnect();
		} catch (IOException | FTPException e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
		}
		return response;
	}

	public WebResponse uploadFile(WebRequest request) {
		InputStream fileContent = request.getFile();
		FormDataContentDisposition fileDetails = request.getFileDetails();
		Logs.display("File uploaded : " + fileDetails.getFileName());
		WebResponse response = new WebResponse();
		try {
			FTPClient client = FTPUtils.init_auth(request);
			FTPUtils.changeDirectory(request, client);
			client.setFileType(FTP.BINARY_FILE_TYPE);
			response.setContent(FTPUtils.uploadFile(client,fileContent,fileDetails));
			response.setStatus(Response.Status.OK);
			client.disconnect();
		} catch (IOException | FTPException e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
		}
		return response;
	}
}
