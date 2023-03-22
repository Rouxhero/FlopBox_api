/**
 * <h1>Package controller</h1>
 *
 * Ce package contient les classes qui permettent de gérer les requêtes HTTP. Le
 * sous package core contient les classes source des controllers.
 * L'implémentation de ces classes permet de repondre au requete reçu par les
 * ressources. Les controlleur sont des singleton. Ils sont instanciés par la
 * classe {@link com.flopbox.app.controller.core.ControllerFactory}. Les
 * controlleurs sont instanciés au premier appel de ce dernier. La classes
 * AbstractController permet d'indexer les methodes des controlleurs. Ce qui
 * permet de les appeler par la ressource.
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 *
 * @see com.flopbox.app.controller.core.ControllerFactory
 */

package com.flopbox.app.controller;