package com.flopbox.controller.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.flopbox.controller.GuestController;
import com.flopbox.controller.ServerController;
import com.flopbox.controller.UploadController;

public class ControllerFactoryTest {

	@Test
	public void testCreateInstance() {
		assert (GuestController.getInstance() instanceof GuestController);
		assert (ServerController.getInstance() instanceof ServerController);
		assert (UploadController.getInstance() instanceof UploadController);
	}

	@Test
	public void testCreateInstanceAlreadyCreated() {
		Controller controller = GuestController.getInstance();
		assert (controller instanceof GuestController);
		Controller controller2 = GuestController.getInstance();
		assertEquals(controller2, controller);
	}

}
