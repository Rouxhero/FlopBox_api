package com.flopbox.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.flopbox.controller.core.AbstractController;
import com.flopbox.controller.core.Controller;
import com.flopbox.exception.FTPException;
import com.flopbox.util.FTPUtils;
import com.flopbox.web.WebRequest;
import com.flopbox.web.WebResponse;

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
			client.setFileType(FTP.BINARY_FILE_TYPE);
			FTPUtils.createFolder(request, client);
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
		WebResponse response = new WebResponse();
		try {
			FTPClient client = FTPUtils.init_auth(request);
			FTPUtils.changeDirectory(request, client);
			client.setFileType(FTP.BINARY_FILE_TYPE);
			FTPUtils.uploadFile(client, fileContent, fileDetails);
			response.setStatus(Response.Status.OK);
			client.disconnect();
		} catch (IOException | FTPException e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
		}
		return response;
	}
}
