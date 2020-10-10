Ce programme mesure la sous-documentation d'un projet Java et retourne des informations sur son niveau de documentation. Ces informations sont présentées dans deux fichiers .csv : un pour les classes et un pour les méthodes.

Notez bien que puisqu'il ne s'agit pas d'un exercice de parsing, nous sommes conscients que notre programme ne traite pas tous les cas limites. Notamment, les méthodes définies sur plusieurs lignes ne sont pas bien interprétés. Nous avons choisi de concentrer nos efforts sur la réflexion et l'analyse des métriques plutôt que d'améliorer l'algorithme de parsing.

L'utilisation du programme est très simple : on le lance à l'aide de la commande **java -jar TP1.jar** et il sera demandé d'indiquer le "path" relatif de notre projet dans la console. Il suffit alors de donner le bon chemin relatif et les deux fichiers .csv seront créés à l'endroit où notre programme est exécuté.

Pour lancer les tests unitaires, il suffit d'exécuter le programme avec l'option "-ea", donc la commande **java -jar TP1.jar -ea** pour permettre les "assert". En l'absence d'erreur, le seul résultat sera les indications imprimées à la console selon lesquelles les tests ont passé.

Bonne correction! ^^
