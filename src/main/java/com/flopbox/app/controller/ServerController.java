package com.flopbox.app.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.net.ftp.FTPClient;

import com.flopbox.app.controller.core.AbstractController;
import com.flopbox.app.controller.core.Controller;
import com.flopbox.app.util.exception.ControllerException;
import com.flopbox.app.util.exception.FTPException;
import com.flopbox.app.util.tools.FTPUtils;
import com.flopbox.app.util.tools.ServerUtils;
import com.flopbox.app.util.web.WebRequest;
import com.flopbox.app.util.web.WebResponse;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ServerController extends AbstractController {

	private static Controller instance = null;

	public static Controller getInstance() {
		if (instance == null) {
			instance = new ServerController();
		}
		return instance;
	}

	public ServerController() {
		super();
	}

	public WebResponse addServer(WebRequest request) {
		WebResponse response = new WebResponse();
		try {
			ServerUtils.addServer(request);
			response.setContent("Server added");
			response.setStatus(Response.Status.OK);
		} catch (IOException | ControllerException | CsvException e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
		}
		return response;
	}

	public WebResponse deleteServer(WebRequest request) {
		WebResponse response = new WebResponse();
		try {
			ServerUtils.deleteServer(request);
			response.setStatus(Response.Status.OK);
		} catch (ControllerException | IOException | CsvException e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
		}
		return response;
	}

	public WebResponse listRoot(WebRequest request) {
		WebResponse response = new WebResponse();
		try {
			FTPClient client = FTPUtils.init_auth(request);
			FTPUtils.listContent(request, response, client);
			response.setStatus(Response.Status.OK);
			client.disconnect();
		} catch (IOException | ParserConfigurationException | FTPException e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
		}
		return response;
	}

	public WebResponse listServer(WebRequest request) {
		WebResponse response = new WebResponse();
		try {
			response.setContent(ServerUtils.listServers(request));
			response.setType(MediaType.APPLICATION_JSON);
			response.setStatus(Response.Status.OK);
		} catch (IOException | CsvValidationException e) {
			response.setStatus(Response.Status.INTERNAL_SERVER_ERROR);
			response.setContent(e.getMessage());
		}
		return response;
	}
}
