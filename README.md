# ChatBot pour HSBC

### Stack

 * Java 8
 * [Spark MLLib](https://spark.apache.org/docs/latest/mllib-guide.html)
 * [Smack library](https://www.igniterealtime.org/projects/smack/)
 * Amzon EC2

### Data
  Pour apprendre un model l'algorithme a besoin de données. Il se connecte à une API qui gère les
  intents et leurs utterances. Elle permet également d'enregistrer les feedbacks des utilisateurs.
  L'API doit être disponible et connecté à la base de données.
  Suivre le README du [repository](https://github.com/LouisPouillot/HSBC_WEBSITE)

  Pour ajouter des intents vous pouvez utiliser le fichier data.json en envoyant son contenu comme body d'une reqête POST à l'URL https://{votre-nom-d-app-heroku}.herokuapp.com/intents.
  Vous pouvez utilisé le logiciel Postman ou curl pour se faire.

  ATTENTION:
  Pour pouvoir utiliser les feedbacks vous devez ajouter deux intents manuellement via l'interface web en cochant 'FEEDBACK YES' pour l'intent OUI et 'FEEDBACK NO' pour l'intent NON.

### Deployment

Pour déployer sur une instance d'EC2 il vous suffit:
* De vous connectez en ssh à cette instance
* [D'installez Docker](https://docs.docker.com/engine/installation/)
* D'installer git et de clone ce repository
* Puis de run deux commandes dans la racine du dossier cloné
    * ``` docker build -t hsbc-bot .```
    * ``` docker run -t hsbc-bot ```
