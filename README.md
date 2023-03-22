Projet nº1 - Application «FlopBox»

Le Van Canh dit Ban Léo

21/02/2023


# Sommaire
- [Sommaire](#sommaire)
- [Introduction](#introduction)
- [UML](#uml)
- [Code sample](#code-sample)
- [Liste des paths](#liste-des-paths)
  - [Resumer des paths](#resumer-des-paths)


# Introduction

Le but de ce projet est de créer une API REST en Java.

Cette API a pour fonction de regrouper des serveurs FTP et d'offrir un accès à ces serveurs via des liens.
 
Elle permet également  d'uploader et de télécharger des fichiers sur les différents serveurs.

# Demo

La demo est dans le dossier `doc\`


# UML

Cliquez ici pour voir l' [UML](doc/UML.png)

# Code sample

<details>
<summary>Code sample 1</summary>
<div>
<h3>Les controlleurs</h3>

Pour rendre le projet facilement extensible, le système de contrôleurs utilise le pattern `Factory` pour créer les contrôleurs.

De plus, l'abstract controller charge les méthodes publiques du contrôleur qui l'étend. Ainsi, pour exécuter les différentes méthodes dans les ressources, il suffit d'appeler la méthode "execute" de l'abstract controller. Les contrôleurs utilisent également le design pattern `Singleton` pour éviter d'instancier les contrôleurs à chaque fois qu'on en a besoin. 

On peut donc créer un nouveau contrôleur et exécuter la méthode simplement en utilisant la méthode "execute" comme suit :


```java
//############# new Controller ############
class NewController extends AbstractController {
    public static Controller instance = null;

    public static Controller getInstance() {
        if (instance == null) {
            instance = new NewController();
        }
        return instance;
    }

    public NewController() {
        super();
    }

    public void newMethod() {
        // Do something
    }
    // Autres methode..
}

// ############ controllerFactory.java ############
//...
    public static final String NEW_CONTROLLER = "newController";
//...
case NEW_CONTROLLER:
        return NewController.getInstance();
//...
//################# New Ressources ################

@Path("/new")
public class NewRessource extends AbstractRessource {

    public NewRessource() {
        super(ControllerFactory.NEW_CONTROLLER);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response newMethod(@BeanParam WebResponse request) {
        return controller.execute("newMethod", request, true);
    }
    // autre route ... 
}
```
Pour les ressources , regarder le codeSample 2
</div>
</details>

<details>
<summary>Code sample 2</summary>
<div>
<h3>Les ressources</h3>
Les ressources étendent l'abstract resource, ce qui permet de récupérer le bon contrôleur en fonction de la ressource. Ainsi, il n'est plus nécessaire de gérer l'appel des contrôleurs dans les ressources.
</div>
</details>

<details>
<summary>Code sample 3</summary>
<div>
<h3>WebRequest/WebResponce</h3>
Afin de simplifier la récupération des paramètres des requêtes, l'utilisation d'un BeanParam regroupe les paramètres de la requête dans un seul objet. 

Cela permet de simplifier l'écriture des méthodes dans les contrôleurs. De plus, dans un souci d'unifier les retours des méthodes, les méthodes des contrôleurs renvoient un objet WebResponse. 

Cette classe permet de construire un objet Response avec les paramètres de la requête.
</div>
</details>


# Liste des paths

- Authentification => Pour les `Guest`
    -[x] [POST] /auth/login : Permet de se connecter 
    -[x] [POST] /auth/register : Permet de s'enregistrer

- Serveur => Clef d'API obligatoire 
    - /server
      -[x] [GET]  : Permet de recuperer la liste des serveurs FTP
      -[x] [POST] : Permet d'ajouter un nouveau serveur FTP
    - /server/:sid
      -[x] [GET] : Permet de recuperer le contenu du dossier racine du serveur FTP ``:sid``
      -[x] [DELETE] : Permet de supprimer le serveur FTP ``:sid``

- Dossier => Clef d'API obligatoire
    - /server/:sid/folder/:fid
      -[x] [GET] : Permet de recuperer le contenu du dossier ``:fid`` du serveur FTP ``:sid``
      -[x] [DELETE]  : Permet de supprimer le dossier ``:fid`` du serveur FTP ``:sid``
      -[x] [PUT]  : Permet de renomer le dossier ``:fid`` du serveur FTP ``:sid``

- Fichier => Clef d'API obligatoire
    - /server/:sid/file/:fid
      -[x] [GET] : Permet de recuperer le fichier ``:fid`` du serveur FTP ``:sid``
      -[x] [DELETE] : Permet de supprimer le fichier ``:fid`` du serveur FTP ``:sid``
      -[x] [PUT]  : Permet de renomer le fichier ``:fid`` du serveur FTP ``:sid``
      
- Upload => Clef d'API obligatoire
    - /server/:sid/upload/:fid
      -[X] [POST] : Permet d'uploader un fichier ou créer un dossier dans le chemin ``fid`` sur le serveur FTP ``:sid``

- Search => Clef d'API obligatoire
    - /server/search?s=
      -[x] [GET] : Permet de rechercher un fichier ou un dossier sur le serveur FTP ``:sid``
## Resumer des paths

| Path                     | Access | Header                                                   | Query | path         | Body Form                            | Response  Type                                                                     | Response Body   |
|--------------------------|--------|----------------------------------------------------------|-------|--------------|--------------------------------------|------------------------------------------------------------------------------------|-----------------|
| /auth/register           | POST   | None                                                     | None  | None         | *username*<br>*passoword*<br>*email* | text/plain                                                                         | **API Key**     |
| /auth/login              | POST   | None                                                     | None  | None         | *username*<br>*passoword*            | text/plain                                                                         | **API Key**     |
| /server                  | GET    | API Key                                                  | None  | None         | None                                 | application/json                                                                   | **Server List** |
| /server                  | POST   | API Key                                                  | None  | None         | *host*<br>*port*                     | text/plain                                                                         | **Message**     |
| /server/:sid             | GET    | API Key <br> user <br> pass <br> Accept                  | None  | sid          | None                                 | application/json<br>application/xml                                                | **Folder List** |
| /server/:sid             | DELETE | API Key                                                  | None  | sid          | None                                 | text/plain                                                                         | **Message**     |
 | /server/:sid/folder/:fid | GET    | API Key <br> user <br> pass <br> Accept                  | None  | sid <br> fid | None                                 | application/json<br>application/xml                                                | **Folder List** |
| /server/:sid/folder/:fid | DELETE | API Key <br> user <br> pass <br> Accept                  | None  | sid <br> fid | None                                 | text/plain                                                                         | **Message**     |
| /server/:sid/folder/:fid | PUT    | API Key <br> user <br> pass <br> Accept <br>Content-Type | None  | sid <br> fid | *name*                               | text/plain                                                                         | **Message**     |
 | /server/:sid/file/:fid   | GET    | API Key <br> user <br> pass <br> Accept                  | None  | sid <br> fid | None                                 | application/octet-stream <br> text/plain <br>application/json <br> application/xml | **File**        |
 | /server/:sid/file/:fid   | DELETE | API Key <br> user <br> pass <br> Accept                  | None  | sid <br> fid | None                                 | text/plain                                                                         | **Message**     |
 | /server/:sid/file/:fid   | PUT    | API Key <br> user <br> pass <br> Accept <br>Content-Type | None  | sid <br> fid | *name*                               | text/plain                                                                         | **Message**     |
 | /server/:sid/upload/:fid | POST   | API Key <br> user <br> pass                              | None  | sid <br> fid | *file*<br>*name*                     | text/plain                                                                         | **Message**     |
 | /server/:sid/upload      | POST   | API Key <br> user <br> pass                              | None  | sid          | *file*<br>*name*                     | text/plain                                                                         | **Message**     |
 | /server/search           | GET    | API Key <br> user <br> pass <br> Accept                  | s     | None         | None                                 | application/json<br>application/xml                                                | **File List**   |

