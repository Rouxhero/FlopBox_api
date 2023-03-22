package com.flopbox.app.controller.core;

import jakarta.ws.rs.core.Response;

/**
 * <h1>class Controller</h1>
 *
 * Interface pour centraliser les méthodes des controllers
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
public interface Controller {

	/**
	 * Cette méthode permet d'executer une commande.
	 *
	 * @param command   La commande à executer.
	 * @param request   La requête.
	 * @param need_auth Si la requête nécessite une authentification.
	 * @throws Exception
	 */
	public Response execute(String command, Object request, boolean need_auth) throws Exception;
}
