package com.flopbox.resource;

import com.flopbox.app.controller.core.Controller;
import com.flopbox.app.controller.core.ControllerFactory;

/**
 * <h1>Class AbstractResources</h1>
 *
 * Cette classe permet de gérer le controller des ressources
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
public class AbstractResources {

	/**
	 * Le controller associé à la ressource
	 */
	protected Controller controller;

	/**
	 * Constructeur de la classe
	 *
	 * @param name le nom du controller
	 */
	public AbstractResources(String name) {
		controller = ControllerFactory.getInstance(name);
	}

}
