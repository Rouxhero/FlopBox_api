package com.flopbox.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.flopbox.controller.core.AbstractController;
import com.flopbox.controller.core.Controller;
import com.flopbox.exception.FTPException;
import com.flopbox.server.ServerCredential;
import com.flopbox.util.FTPUtils;
import com.flopbox.util.XMLUtils;
import com.flopbox.web.WebRequest;
import com.flopbox.web.WebResponse;
import com.opencsv.exceptions.CsvValidationException;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class SearchController extends AbstractController {

	private static Controller instance = null;

	public static Controller getInstance() {
		if (instance == null) {
			instance = new SearchController();
		}
		return instance;
	}

	private static void searchFiles(FTPClient client, String path, String keyword, List<String> resultFiles) {
		client.enterLocalPassiveMode();
		try {
			FTPFile[] files = client.listFiles(path);

			for (FTPFile file : files) {
				String currentPath = path + "/" + file.getName();
				if (file.isDirectory()) {
					if (file.getName().contains(keyword))
						resultFiles.add(currentPath);
					searchFiles(client, currentPath, keyword, resultFiles);
				} else {
					if (file.getName().contains(keyword))
						resultFiles.add(currentPath);
				}
			}
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			/// => On ignore les directory a probleme
		}
	}

	public SearchController() {
		super();
	}

	private void convertData(WebRequest request, WebResponse response, Map<String, List<String>> res)
			throws ParserConfigurationException {
		if ((request.isAcceptedType(MediaType.TEXT_XML) || request.isAcceptedType(MediaType.APPLICATION_XML)
				&& !request.isAcceptedType(MediaType.APPLICATION_JSON))) {
			response.setContent(XMLUtils.mapToXML(res));
			response.setType(MediaType.APPLICATION_XML);
		} else {
//            response.setContent(JSONUtils.mapToJson(res));
			response.setType(MediaType.APPLICATION_JSON);
		}
	}

	public WebResponse search(WebRequest request)
			throws CsvValidationException, IOException, FTPException, ParserConfigurationException {
		WebResponse response = new WebResponse();
		List<String> list = ServerCredential.get_user_servers(request);
		Map<String, List<String>> res = new HashMap<>();
		for (String server : list) {
			request.set("sid", server);
			res.put(server, new ArrayList<>());
			searchInServer(request, res.get(server));
		}
		response.setStatus(Response.Status.OK);
		convertData(request, response, res);
		return response;
	}

	private void searchInServer(WebRequest request, List<String> response) throws FTPException, IOException {
		String keyword = request.get("search");
		FTPClient client = FTPUtils.init_auth(request);
		searchFiles(client, "/", keyword, response);
	}
}
