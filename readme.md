Ce programme analyse un projet Java et retourne des informations sur les commentaires présents dans les classes et les méthodes. Ces informations sont présentées dans deux fichiers .csv : un pour les classes et un pour les méthodes.

L'utilisation du programme est très simple : on le lance comme un programme Java normal et il nous sera demandé d'indiquer le "path" de notre projet dans la console. Il suffit alors de donner le bon chemin relatif et les deux fichiers .csv seront créés à l'endroit où notre programme est exécuté.

Pour lancer les tests unitaires, il suffit de retirer des commentaires le bloc indiqué dans le code à cet effet et d'exécuter le programme avec l'option -ea pour permettre les "assert". En l'absence d'erreur, le seul résultat sera les indications imprimées à la console selon lesquelles les tests ont passé.