package com.flopbox.controller.core;

import com.flopbox.controller.ContentController;
import com.flopbox.controller.GuestController;
import com.flopbox.controller.SearchController;
import com.flopbox.controller.ServerController;
import com.flopbox.controller.UploadController;

/**
 * <h1>class ControllerFactory</h1>
 *
 * Factory pour recuperer les controllers
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
public class ControllerFactory {

	/**
	 * Nom pour le controller guest
	 */
	public static final String GUEST = "guest";
	/**
	 * Nom pour le controller server
	 */
	public static final String SERVER = "server";
	/**
	 * Nom pour le controller upload
	 */
	public static final String Upload = "upload";
	/**
	 * Nom pour le controller content
	 */
	public static final String Content = "content";
	/**
	 * Nom pour le controller search
	 */
	public static final String Search = "search";

	/**
	 * Retourne le controller correspondant au nom
	 *
	 * @param name le nom du controller
	 * @return
	 */
	public static Controller getInstance(String name) {
		switch (name) {
		case GUEST:
			return GuestController.getInstance();
		case SERVER:
			return ServerController.getInstance();
		case Upload:
			return UploadController.getInstance();
		case Content:
			return ContentController.getInstance();
		case Search:
			return SearchController.getInstance();
		default:
			return null;
		}
	}
}
