package com.flopbox.server;

import static com.flopbox.app.util.server.CSVDataBase.USER_DB;
import static com.flopbox.app.util.server.CSVDataBase.USER_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import com.flopbox.app.util.server.CSVDataBase;
import com.flopbox.app.util.server.ServerCredential;
import org.junit.Before;
import org.junit.Test;

import com.flopbox.app.util.exception.ControllerException;
import com.flopbox.app.util.web.WebRequest;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

public class ServerCredentialTest {

	private WebRequest request;
	private final String USERNAME = "Tim";
	private final String PASSWORD = "Oleon";
	private final String EMAIL = "tim.oleon@test.fr";
	private final String SID = "1";

	@Test
	public void addServerTest() throws ControllerException, CsvException, IOException {
		ServerCredential.registerUser(request);
		String key = ServerCredential.login(request);
		request.set("key", key);
		request.set("sid", SID);
		ServerCredential.update_user_server(request, SID);
		List<String> server = ServerCredential.get_user_servers(request);
		assertEquals(server.size(), 1);
	}

	@Test
	public void check_authTest() throws CsvValidationException, IOException, ControllerException {
		assertFalse(ServerCredential.check_auth(request));
		ServerCredential.registerUser(request);
		String key = ServerCredential.login(request);
		request.set("key", key);
		assertTrue(ServerCredential.check_auth(request));
	}

	@Test
	public void getServerTest() throws ControllerException, CsvValidationException, IOException {
		ServerCredential.registerUser(request);
		String key = ServerCredential.login(request);
		request.set("key", key);
		List<String> server = ServerCredential.get_user_servers(request);
		assertEquals(server.size(), 0);
	}

	@Test
	public void registerUserTest() throws ControllerException, CsvValidationException, IOException {
		ServerCredential.registerUser(request);
		assertNotNull(ServerCredential.login(request));
	}

	@Test
	public void removeServerTest() throws ControllerException, CsvException, IOException {
		ServerCredential.registerUser(request);
		String key = ServerCredential.login(request);
		request.set("key", key);
		request.set("sid", SID);
		ServerCredential.update_user_server(request, SID);
		ServerCredential.unBindServer(request);
		List<String> server = ServerCredential.get_user_servers(request);
		assertEquals(server.size(), 0);
	}

	@Before
	public void setUp() throws Exception {
		request = new WebRequest();
		request.read();
		request.set("username", USERNAME);
		request.set("password", PASSWORD);
		request.set("email", EMAIL);
		CSVDataBase.initializeDatabase(USER_FILE, USER_DB);
	}

}
