package com.flopbox.controller.core;

import com.flopbox.app.controller.core.AbstractController;
import com.flopbox.app.controller.core.Controller;
import org.junit.Test;

import com.flopbox.app.util.web.WebRequest;
import com.flopbox.app.util.web.WebResponse;

import jakarta.ws.rs.core.Response;

public class AbstractControllerTest {

	public static final String CONTENT = "test";

	@Test
	public void testCreateInstance() {
		Controller controller = MockController.createInstance();
		assert (controller instanceof MockController);
	}

	@Test
	public void testExecute() throws Exception {
		MockController controller = new MockController();
		WebRequest request = new WebRequest();
		request.read();
		Response response = controller.execute("test", request, false);
		assert (response.getStatus() == Response.Status.OK.getStatusCode());
		assert (response.getEntity().equals(CONTENT));
	}

	@Test
	public void testExecuteAuth() throws Exception {
		MockController controller = new MockController();
		WebRequest request = new WebRequest();
		request.read();
		Response response = controller.execute("test", request, true);
		assert (response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode());
	}

	@Test
	public void testExecuteException() throws Exception {
		MockController controller = new MockController();
		WebRequest request = new WebRequest();
		request.read();
		Response rep = controller.execute("ma_command", request, false);
		assert (rep.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}

	@Test
	public void testIndexMethods() {
		MockController controller = new MockController();
		assert (controller.getMethodsSize() == 4);
		MockController controller2 = new MockController() {
			public WebResponse test2(WebRequest request) {
				return new WebResponse();
			}
		};
		assert (controller2.getMethodsSize() == 5);
	}

}

class MockController extends AbstractController {

	protected static Controller createInstance() {
		return new MockController();
	}

	public MockController() {
		super();
	}

	public int getMethodsSize() {
		return methods.size();
	}

	public WebResponse test(WebRequest request) {
		WebResponse response = new WebResponse();
		response.setStatus(Response.Status.OK);
		response.setContent(AbstractControllerTest.CONTENT);
		return response;
	}
}