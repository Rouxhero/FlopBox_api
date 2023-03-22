package com.flopbox.controller;

import java.io.IOException;

import com.flopbox.app.controller.GuestController;
import org.junit.Before;
import org.junit.Test;

import com.flopbox.app.controller.core.Controller;
import com.flopbox.app.util.server.CSVDataBase;
import com.flopbox.app.util.web.WebRequest;

import jakarta.ws.rs.core.Response;

public class GuestControllerTest {

	private WebRequest request;

	@Test
	public void loginValidTest() throws Exception {
		Controller controller = GuestController.getInstance();
		request.set("username", "test");
		request.set("password", "test");
		request.set("email", "test@test.test");
		Response response = controller.execute("registerUser", request, false);
		response = controller.execute("loginUser", request, false);
		assert (response.getStatus() == Response.Status.OK.getStatusCode());
	}

	@Test
	public void loginWithKey() throws Exception {
		Controller controller = GuestController.getInstance();
		assert (controller instanceof GuestController);
		request.set("key", "test");
		Response response = controller.execute("loginUser", request, false);
		assert (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
	}

	@Test
	public void loginWithMissingParametersTest() throws Exception {
		Controller controller = GuestController.getInstance();
		request.set("username", "test");
		Response response = controller.execute("loginUser", request, false);
		assert (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
	}

	@Test
	public void registerAlreadyRegisteredTest() throws Exception {
		Controller controller = GuestController.getInstance();
		request.set("username", "test");
		request.set("password", "test");
		request.set("email", "test@test.test");
		Response response = controller.execute("registerUser", request, false);
		assert (response.getStatus() == Response.Status.OK.getStatusCode());
		response = controller.execute("registerUser", request, false);
		assert (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}

	@Test
	public void registerValidTest() throws Exception {
		Controller controller = GuestController.getInstance();
		request.set("username", "test");
		request.set("password", "test");
		request.set("email", "test@test.test");
		Response response = controller.execute("registerUser", request, false);
		assert (response.getStatus() == Response.Status.OK.getStatusCode());
	}

	@Test
	public void registerWithKeyTest() throws Exception {
		Controller controller = GuestController.getInstance();
		assert (controller instanceof GuestController);
		request.set("key", "test");
		Response response = controller.execute("registerUser", request, false);
		assert (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
	}

	@Test
	public void registerWithMissingParametersTest() throws Exception {
		Controller controller = GuestController.getInstance();
		request.set("username", "test");
		request.set("password", "test");
		Response response = controller.execute("registerUser", request, false);
		assert (response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
	}

	@Before
	public void setUp() throws IOException {
		request = new WebRequest();
		request.read();
		CSVDataBase.initializeDatabase(CSVDataBase.USER_FILE, CSVDataBase.USER_DB);
	}

	@Test
	public void testCreateInstance() {
		Controller controller = GuestController.getInstance();
		assert (controller instanceof GuestController);
	}

	@Test
	public void testCreateInstanceAlreadyCreated() {
		Controller controller = GuestController.getInstance();
		assert (controller instanceof GuestController);
		Controller controller2 = GuestController.getInstance();
		assert (controller2 == controller);
	}
}
