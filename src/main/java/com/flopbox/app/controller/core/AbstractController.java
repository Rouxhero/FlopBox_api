package com.flopbox.app.controller.core;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.flopbox.app.util.exception.ControllerException;
import com.flopbox.app.util.server.ServerCredential;
import com.flopbox.app.util.tools.Logs;
import com.flopbox.app.util.web.WebRequest;
import com.flopbox.app.util.web.WebResponse;

import jakarta.ws.rs.core.Response;

/**
 * <h1>class AbstractController</h1>
 *
 * Classe abstraite permettant de factoriser le code commun aux différents
 * contrôleurs.
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 */
public abstract class AbstractController implements Controller {

	/**
	 * Map contenant les méthodes publiques de la classe.
	 */
	protected HashMap<String, Method> methods;

	/**
	 * Constructeur de la classe.
	 */
	protected AbstractController() {
		methods = new HashMap<>();
		indexMethods();
	}

	/**
	 * Cette méthode permet d'executer une commande.
	 *
	 * @param command   La commande à executer.
	 * @param request   La requête.
	 * @param need_auth Si la requête nécessite une authentification.
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response execute(String command, Object request, boolean need_auth) throws Exception {
		Method method = methods.get(command);
		Logs.display(Logs.DEBUG, "Receive request : " + ((WebRequest) request).get("uri"));
		if (need_auth) {
			if (!ServerCredential.check_auth((WebRequest) request)) {
				Logs.display(Logs.ERROR, ServerCredential.AUTH_FAIL + " for key " + ((WebRequest) request).get("key"));
				return WebResponse.buildResponse(Response.Status.UNAUTHORIZED, ServerCredential.AUTH_FAIL);
			}
		}
		Logs.display(Logs.DEBUG, "Execute command : " + command);
		try {
				if (method != null) {
				WebResponse response = (WebResponse) method.invoke(this, request);
				return response.build();
			} else {
				throw new ControllerException("La commande " + command + " n'a pas été trouvée.");
			}
		} catch (Exception e) {
			Logs.display(Logs.ERROR, e.getMessage());
			return WebResponse.buildResponse(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	/**
	 * Cette méthode permet d'indexer les méthodes publiques de la classe.
	 */
	private void indexMethods() {
		Method[] allMethods = this.getClass().getMethods();
		for (Method method : allMethods) {
			if (method.getDeclaringClass() != AbstractController.class
					&& method.getModifiers() == java.lang.reflect.Modifier.PUBLIC) {
				methods.put(method.getName(), method);
			}
		}
	}
}
