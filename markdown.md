1

IUT de Paris - Rives de Seine

Université Paris Cité

# Programmation avancée (R5.A.05) :
Développement d'applications d'entreprises avec JavaEE (Java Enterprise Edition)

BUT Informatique, 3ème année

Mourad Ouziri
mourad.ouziri@u-paris.fr

# Informations pratiques

- Plan du cours
- Architecture des Applications JEE : agilité et maintenabilité
- Développement avec le framework Spring : productivité

- Vous apprendrez à :
- Développer des applications de qualité en JEE
- Développer à l'aide d'un framework : Spring (Core, MVC, Data, Security)
- Remanier une application d'entreprise : audit de son architecture

- Evaluation : (QCM + TP noté + Projet) / 3

- Bibliographie :
- Software Architecture in Practice (2nd edition) – Addison-Wesley 2003
- Agile Software Development: Principles, Patterns, and Practices – Prentice Hall 2003
- JEE : https://javaee.github.io/tutorial/toc.html
- https://jakarta.ee

2

# Architecture des applications d'entreprise :

Mise en oeuvre en JavaEE

# Développement logiciel et qualité

## Rappel

- Application d'entreprise : deux types de services + qualité

![img-0.jpeg](img-0.jpeg)

# Qualité des applications

https://www.iso.org/fr/standard/35733.html

- Objectif : développer des applications de qualité
- Plusieurs indicateurs de qualité (ISO 9126, FURPS, etc.)
- Exactitude fonctionnelle : prise en compte des besoins fonctionnels des futurs usagers
- Fiabilité : niveau de service (tolérance aux pannes et reprise après panne)
- Utilisabilité (maniabilité) : effort à l’utilisation
- Rendement et efficacité : rapport entre le service rendu et l’effort de nécessaire fonctionner (rentabilité)
- Maintenabilité : effort nécessaire à ses modifications (évolution, correction, etc.)
- Portabilité : transférabilité sur d’autres environnements d’exécution

5

# Qualité des applications

## Maintenabilité

- Maintenabilité, un facteur de qualité important !
- Evolution constante des attentes des utilisateurs et des technologies
- Applications d'entreprise complexes
- Coût moyen des applications informatiques très élevé
- Objectif : amortissement des coûts de développement/acquisition

![img-1.jpeg](img-1.jpeg)
Software Life-Cycle Costs

![img-2.jpeg](img-2.jpeg)

7

# Qualité des applications

## Maintenabilité

- **Objectif :**
- Maintenir l'application en état de fonctionnement le plus longtemps possible à moindre coût

- **Maintenance logicielle**
- Les changements qui doivent être apportés à un logiciel après sa livraison à l'utilisateur (Martin et McClure 1983)
- La totalité des activités qui sont requises afin de procurer un support, au meilleur coût possible, d'un logiciel. Certaines activités débutent avant la livraison du logiciel, donc pendant sa conception initiale (SWEBO 2005)

- **Levier de la maintenabilité :** architecture de code

# Architecture logicielle

## Définition
- Architecture d'une application = Structure de l'application
- Schéma des différents composants, leurs rôles et leurs interactions

## Eléments d'architecture
- Composants, rôles, dépendances/communication inter-composants

## Objectif
- Concevoir des applications maintenables à moindre coût
- Concevoir des applications robustes vis-à-vis des évolutions futures

8

# Problématiques de la maintenabilité

Modules trop volumineux, plusieurs rôles et types de code

```c
public class Main {
// toute l'application codée dans une seule classe/méthode
private int numero;
private String nom;
private String adresse;
String urljdbc = "jdbc:oracle:thin:@myhost:1521:orcl";
Connection conn;
Statement st;
private int numero; private int numero; private int numero; private int numero;
private String nom;
private String adresse;
String urljdbc = "jdbc:oracle:thin:@myhost:1521:orcl";
Connection conn;
Statement st; private int numero;
private String nom; private int numero; private int numero;
SQLResult set;
<h1> Inscription </h1>
String sql=insert into Personne ...
private String adresse;
String urldbc = "jdbc:oracle:thin:@myhost:1521:orcl"; private int numero; private int numero;
Connection conn;
<spam> dggfdd dkjhdiud </spam>
Statement st;
private int numero;
private String nom; private int numero; private int numero; private int numero; private int numero;
private String adresse;
String urldbc = "jdbc:oracle:thin:@myhost:1521:orcl";
<spam> dggfdd dkjhdiud </spam>
Statement st;
private int numero; private int numero; private int numero; private int numero; private int numero;
private String nom;
private String adresse;
String urldbc = "jdbc:oracle:thin:@myhost:1521:orcl";
}
}
```

![img-3.jpeg](img-3.jpeg)

![img-4.jpeg](img-4.jpeg)

![img-5.jpeg](img-5.jpeg)

![img-6.jpeg](img-6.jpeg)

![img-7.jpeg](img-7.jpeg)

![img-8.jpeg](img-8.jpeg)

![img-9.jpeg](img-9.jpeg)

9

# Problématiques de la maintenabilité

Modules trop volumineux, trop de rôles et de types de code

- Quel est le coût des maintenances liées au seul changement de règle de gestion métier (des interfaces, base de données ou sécurité) ?
- Localiser le code concerné : lire et comprendre dans une grande quantité de code
- Interférence de technologies : chevauchement de compétences transversales
- Modifier du code existant : présente le risque de régression fonctionnelle

- Structurer le code pour réduire les coûts de maintenance : le diviser en plusieurs « petites » classes (paquetages, modules, etc.)
- Limiter la taille des classes
- Limiter les responsabilités de chaque classe

- Quelle taille pour une classe (paquetage, méthode ou module) ?

10

# Architecture logicielle

## Principes de conception

### Principe général

- Diviser le code en modules (entités logicielles, fichiers de code) indépendants atomiques (à responsabilité unique)

### Deux principes directeurs...

- Atomicité des maintenances – SRP (Single Responsibility Principle) : un module de code doit avoir une responsabilité (rôle) limitée de manière à ce que tout son doit évoluer au même rythme

- Couplage faible – DI (Dependency Inversion) : les modules de code ne doivent pas avoir de fortes dépendances entre eux de sorte à ce que le changement d'un module ne doit pas impacter le reste des modules. Elle est souvent réalisée à l'aide de l'inversion de dépendance et de l'injection de dépendance (Dependency Inversion/Injection)

# Architecture logicielle

## Principes de séparation de code (SRP)

![img-10.jpeg](img-10.jpeg)

Code métier (fonctionnel)

- Indépendance de la logique métier de la brique technique de l'informatique
- Code métier : code réalisant les spécifications fonctionnelles du client
- Code technique (informatique) : bases de données, protocoles (Web), sécurité, bibliothèques logicielles, frameworks, etc.
- Productivité des développements : automatiser ce qui est générique, $c$-à-$d$ la partie technique

# Architecture logicielle

## Principe de modules atomiques

- SRP (Single Responsibility Principle)
- Un module ne doit avoir qu'un seul axe (raison) de changement
- Séparer les codes qui n'évolueront pas au même rythme
- Exemple de codes évoluant à des rythmes différents : métier/technique, calcul/affichage/base de données, etc.

![img-11.jpeg](img-11.jpeg)
2 axes de changement

![img-12.jpeg](img-12.jpeg)
1 axe de changement chacun

14

# Architecture logicielle

## Principe de modules atomiques

- SRP (exemple)
- Un module (classe) ne doit avoir qu'une seule responsabilité
- Afin qu'elle n'ait qu'une **seule raison de changer**

```cs
module Metier {
private int numero;
private String non;
private String address;
private String name;
private int numero;
private String name;
private int numero;
}
```

```cs
module IHM {
private int numero;
private String non;
private String address;
private String name;
private int numero;
private int numero;
}

```cs
module PersistanceOracle {
private int numero;
private String non;
private String address;
private String name;
private int numero;
private int numero;
private String address
}
```

# Architecture logicielle

## Principe de modules atomiques

- SRP (Single Responsibility Principle)
- Si deux fragments de code (y compris s'ils sont du même type) n'évolueraient pas au même rythme, les séparer dans deux fichiers de code différents !

```
Services fonct
<code f1="" (métier)="" i="">
<code f2="" (métier)="" i="">
```

2 axes de changement : F1 et F2 évolueront à des rythmes différents

```
Service fonct 1
<code f1="" (métier)="" i="">
```

```
Service fonct 2
<code f2="" (métier)="" i="">
```

Un service = un seul axe de changement

15</code></code></code>

# Architecture logicielle

## Principe de modules atomiques

- Séparer deux bouts de code, même s'ils sont du même type et dans une même fonction !

### Services fonct

<code f1="" (métier)=""="" de="" fragment="" code="" f1="" l="">
<code 2="" la="" l="" l'="">

2 axes de changement :
Les deux fragments de code
de F1 évolueraient à des
rythmes différents

Séparer les deux
![img-13.jpeg](img-13.jpeg)

### Service fonct 1

<code 1="" f1="" l'="" (métier)="" de="" fragment="" code="" f1="" l'="">

### Service fonct 2

<code 2="" la="" l="" l'="" (métier)="" de="" fragment="" code="" f1="" l'="">

1 axe de changement chacun</code></code></code></code>

# Architecture logicielle

## Principe de modules atomiques

- SRP (Single Responsibility Principle)
- Les langages sont indicateurs des axes de changements

![img-14.jpeg](img-14.jpeg)

![img-15.jpeg](img-15.jpeg)

14 axes de changement :
Chaque langage/technologie
Constitue un axe de changement

1 axe de changement chacun

# Architecture logicielle

## Découplage (ou indépendance des modules)

Schéma d'architecture : effet négatif des dépendances

→ Dépendance
Mi Instable

![img-16.jpeg](img-16.jpeg)
Module instable!

![img-17.jpeg](img-17.jpeg)

## Problèmes de maintainabilité

- Propagation (impact) du changement (dans les structures de données par exemple)
- Coût = Cout lié à M1 + Coût des modules impactés par propagation (M2, M3, M4, M5) [M. Ziane]

18

# Architecture logicielle

Découplage du fonctionnel par rapport à la technique

DIP (Dependency Inversion Principle) : Inversion de dépendance

- Ne pas faire dépendre de modules/services instables (changeants)
- Le modèle ne doit pas dépendre (comment sont codés) des services techniques mais de leur spécification (quoi ?)

![img-18.jpeg](img-18.jpeg)

# Architecture logicielle

Découplage du fonctionnel par rapport à la technique

- DIP (Dependency Inversion Principle) : Inversion de dépendance
- Comment injecter les classes des librairies techniques dans le modèle sans les mentionner explicitement (indépendance) ?
- Solution : Spring core permet d'injecter les objets techniques dans le modèle

![img-19.jpeg](img-19.jpeg)

# Architecture logicielle

## Le découplage : l'exemple de JDBC

- DIP : découplage des méthodes fonctionnelles des Driver de SGBD
- JDBC est une spécification d'accès aux BDD à partir de programmes Java
- Les Driver de SGBD fournissent des implémentations des interfaces de JDBC

![img-20.jpeg](img-20.jpeg)

Code des services (librairies)

# Architecture logicielle

Découplage du fonctionnel par rapport à la technique

- Injection de dépendance :
- Comment injecter l'implémentation/classe des bibliothèques techniques dans le modèle sans les mentionner explicitement (indépendance) ?
- Solution : injection de dépendance

![img-21.jpeg](img-21.jpeg)

# Architecture logicielle

Découplage du fonctionnel par rapport à la technique

Injection de dépendance

|  public class Fonctionnel { IOperation iOp; public Fonctionnel (IOperation op) { this.setOperation (op); } public void setOperation (IOperation op) { this.iOp = op; } public double calcular(a, b) { return iOp.calculer(a,b); } } | interface IOperation { double calculer (double x, double y); }  |
| --- | --- |
|   |  package operations; class OperationSomme implements IOperation { double calculer (double x, double y){ return x + y; } }  |
|  class MainApplication { public static void main(String [] args) { IOperation opSomme = new OperationSomme () Fonctionnel f = new Fonctionnel (opSomme) ; // injection via le constructeur f.setOperation (opSomme) ; // injection via un setter double res = f.calculer (2, 3); System.out.println(res); } }  |   |

# Architecture logicielle

## Découplage du fonctionnel par rapport à la technique

- Injection de dépendance : principe
- Externaliser le nom de la classe à instancier (dans un fichier de configuration par exemple) puis l'instancier dynamiquement avec la méta classe Java : Class
- Externaliser/déléguer le chargement de la classe et son instanciation à une classe dédiée (factory, outils d'injection de dépendances, frameworks, etc.)

- Schéma de l'injection de dépendance :

![img-22.jpeg](img-22.jpeg)

# Architecture logicielle

Découplage du fonctionnel par rapport à la technique

Injection de dépendance : exemple d'implémentation

|  class Fonctionnel { IOperation iOp; void setOperation (IOperation op) { this.iOp = op; } double calculer(a, b) { return iOp.calculer(a,b); } | dependances.properties classe-op-id=operations.OperationSomme  |
| --- | --- |
|  interface IOperation { double calculer (double x, double y) { return x + y; } } |   |
|  class InjectionDependance { void static injector (Fonctionnel f, String fichierConfig, String idClasseImpl) { String nomClasseOp = lireParamètresConfig (fichierConfig, nomClasseImpl); IOperation op = (IOperation) Class.forName (nomClasseOp); f.setOperation (op); return m; } |   |
|  Fonctionnel f = new Fonctionnel (); InjectionDependance.injecter (f, "config.properties", "classe-op-id"); f.calculer (2, 3); |   |

# Architecture MVC

Modèle-Vue-Contrôleur : indépendance du Modèle

![img-23.jpeg](img-23.jpeg)

## Rôles :

- Vue : interaction avec les utilisateurs (environnement extérieur de l'appli)
- Modèle : logique métier (ou le fonctionnel) de l'application
- Contrôleur : routage/aiguillage des requêtes vers le Modèle (pour être traitées), puis des résultats vers les Vue (pour être affichés)

Rôle essentiel : indépendance du Modèle...

## Maintenabilité

- Localisation des responsabilités (bogues et évolutions)
- Hypothèse de travail : le métier (Modèle) et la technique évoluent à des rythmes différents

# Architecture technique des applications d'entreprise
## Le dilemme du Modèle

![img-24.jpeg](img-24.jpeg)

|  Composants Spring | Spring REST Spring teamleaf | Spring MVC | Spring core (Injection de dépendance) | Spring Data Spring Security etc.  |
| --- | --- | --- | --- | --- |

*POJO : Plain Old Java Object

# Analyse de l'architecture MVC avec UML

- Exemple : gestion simplifiée d'une bibliothèque

![img-25.jpeg](img-25.jpeg)

![img-26.jpeg](img-26.jpeg)

# MVC avec Java, JSP et Servlet

- Architecture mono-contrôleur (MVC 1)
- Un seul contrôleur pour l'ensemble de l'application
- Inconvénient : complexité du contrôleur et dépendance de JEE (API Servlet)

![img-27.jpeg](img-27.jpeg)

# MVC avec Java, JSP et Servlet

- Architecture multi-contrôleurs (MVC 2)
- Un contrôleur par fonction de l'application
- Inconvénient : toutes les classes du contrôleur dépendent du protocole http

![img-28.jpeg](img-28.jpeg)

# MVC avec Java, JSP et Servlet

- Architecture à contrôleurs mixtes (la meilleure en termes de qualité !) :
- Un contrôleur frontal (de type Servlet) pour le protocole http et plusieurs contrôleurs dédiés (de type POJO) aux fonctions de l'application
- Avantages : respect du principe SRP (facilité d'ajout et suppression de fonctions) et moins de dépendance de JEE (Servlet)

![img-29.jpeg](img-29.jpeg)

# Vue en JSP/HTML

## Identité fonctionnelle auprès du contrôleur

|   | ajoutLivre.html  |
| --- | --- |
|  |   |
|  # Ajout d’un livre |   |
|  |   |
|  Titre du livre : Son auteur : |   |
|  |   |
|  |   |
|  |   |
|  |   |

|   | rechercherLivre.html  |
| --- | --- |
|  |   |
|  # Rechercher un livre |   |
|  |   |
|  Titre du livre : |   |
|  |   |
|  |   |

Toutes les vues-requêtes utilisent le même paramètre (caché) pour s’identifier auprès du contrôleur

32

# Vue en JSP/HTML

Identité fonctionnelle auprès du contrôleur

Vue (html/jsp) : Envoi d'une requête à la Servlet (Contrôleur)

1. Une requête HTTP est envoyée lorsqu'on clique sur Submit
2. Les données du formulaires sont transmises dans la requête HTTP sous forme de paramètres
3. Exemples : requêtes de type GET :

a. La vue ajoutLivre.html

```txt
http://url_du_serveur/bibliotheque/controleur?titreLivre=titre_sais&amp;auteurLivre=nom_saisi&amp;opereation=ajoutLivre
```

b. La vue rechercherLivre.html

```txt
http://url_du_serveur/bibliotheque/controleur?titreLivreRecherche=titre_saisi&amp;opereation=rechercherLivre

# Contrôleur : HttpServlet

## Contrôleur : classe HttpServlet

public abstract class javax.servlet.http.HttpServlet {
public void service (HttpServletRequest req, HttpServletResponse resp) throws ... ;
protected void doPost (HttpServletRequest req, HttpServletResponse resp)
throws ServletException, java.io.IOException ;
protected void doGet (HttpServletRequest req, HttpServletResponse resp)
throws ServletException, java.io.IOException ;

}

A chaque réception d’une requête :

1. Appel de la méthode service (req, resp) en lui transmettant les paramètres de la requête http dans le paramètre HttpServletRequest
2. La méthode service transfert la requête (HttpServletRequest) à doPost ou à goGet.

34

# J2EE avec UML

Réception des requêtes par la Servlet (Contrôleur)

- DS de réception d'une requête http par la Servlet (contrôleur)

![img-30.jpeg](img-30.jpeg)

# J2EE avec UML

## Réception des requêtes par la Servlet (Contrôleur)

![img-31.jpeg](img-31.jpeg)

DS de réception de la première requête http : instantiation de la Servlet (Contrôleur)

# Architecture mono-contrôleur

Architecture mono-contrôleur

Contrôleur : classe HttpServlet
package controleur;
import modele.*;
public class ControleurBiblio extends HttpServlet {
private IBibliotheque modeleBiblio;
public void init (ServletConfig cf) throws ServletException {
super.init (cf);
modeleBiblio = new Bibliotheque(); // dépendance ! À retirer...
}
public void service (HttpServletRequest req, HttpServletResponse rép) throws ... {
1. Identifier le service demandé et récupérer ses paramètres
2. Invoquer le service (fonctionnel) demandé du modèle
3. Transmettre les résultats à la vue
}
public void destroy () {modeleBiblio = null; }
}
37

# Architecture mono-contrôleur
## Injection de dépendance

- Indépendance du Contrôleur de la classe du Modèle
```java
package controleur;
import modele.*;
public class ControleurBiblio extends HttpServlet {
private IBibliotheque modeleBiblio;
public void init (ServletConfig cf) throws ServletException {
super.init (cf);
modeleBiblio = new Bibliotheque(); // dépendance ! À retirer...
}
public void service (HttpServletRequest req, HttpServletResponse rép) throws ... {
...
}
public void destroy () {modeleBiblio = null; }
}
```

38

# Architecture mono-contrôleur

Injection de dépendance du Modèle

- Injection de dépendance (objet de persistance)
- Récupérer le nom de la classe de persistance du fichier de configuration et créé un objet de persistance

```java
public class ControleurBiblio extends HttpServlet {
private IBibliotheque modeleBiblio;
@Override
public void init() { // l'injection de dépendance (Implémentation du Modèle) super.init (cf);
String nomClasseModele = getServletContext().getInitParameter("classe-modele");
this.modeleBiblio = (IBiliotheque) Class.forName(nomClasseModele)
.getDeclaredConstructor().newInstance();
}
...
}

# Indépendance du contrôleur du Modèle

## Injection de dépendance

### Injection de dépendance (objet Modèle)

- Paramétrer la classe de dépendance (modèle) dans le fichier de configuration Web : web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app>
...
<context-param>
<param-name>modele</param-name>
<param-value>nom-de-la-classe-d'implémentation-du-modèle</param-value>
</context-param>
...
</web-app>
```

40

# Architecture mono-contrôleur

## Traitement des requêtes

Contrôleur (HttpServlet) : identifier la demande

```java
public class ControleurBiblio extends HttpServlet {
public void service (HttpServletRequest req, HttpServletResponse resp) {
// Identifier la demande
String demande = req.getParameter("operation");
if (demande.equals("ajoutLivre")) {
1. récupérer les paramètres http de la demande d'ajout d'un livre
2. Transmettre la demande au le modèle (Java pure)
3. Transmettre le résultat à la page JSP se chargeant de son affichage
}
if (demande.esquals("rechercherLivre")) {
1. récupérer les paramètres http de la demande de recherche d'un livre
2. Faire exécuter la demande par le modèle (Java pure)
3. Transmettre le résultat à la page JSP se chargeant de son affichage
}
}
```

41

# Architecture mono-contrôleur

## Traitement des requêtes

Contrôleur : traitement de la demande d’ajout d’un livre

```java
public class ControleurBiblio extends HttpServlet {
public void service (HttpServletRequest req, HttpServletResponse response) {
// identifier la demande
String demande = req.getParameter("operation");
if (demande.equals("ajoutLivre")) {
// récupérer les paramètres http de la demande
String titre = req.getParameter("titreLivre");
String auteur = req.getParameter("auteurLivre");
// Transmettre la demande au modèle
boolean res = modeleBiblio.ajouterLivre(titre, auteur);
// Transmettre le résultat à la page JSP confirmationAjout
req.setAttribute("confAjout", res);
RequestDispatcher d = req.getRequestDispatcher("/vue/confirmationAjout.jsp"))
d.forward(req, response);
if (demande.("rechercherLivre")) { // traitement de recherche }
}
```

42

43

# Architecture mono-contrôleur

## Traitement des requêtes

Servlet (Controluer) : Transmettre une donnée à la vue (Page JSP)

1. Type simple (int, String, etc) : transmise sous forme de paramètre et récupérable avec la méthode : `setParameter (nomduparamètre, donnée)`.
2. Types complexe (objets) : Transmis sous forme d’attribut et récupérable avec la méthode : `setAttribute (nomdelattribut, donnée)`.

Où ? Dans un des trois objets

- Dans la requête http : `HttpServletRequest`
- Dans la session de l’utilisateur
- Dans un objet commun à tous les utilisateurs

# Vue JSP

## Vues réponse de l'application

- Vue réponse : présentation des données (affichage des résultats)
- Où récupérer les données ? Dans un des trois objets JSP implicites
- Dans la requête http : objet request
- Dans la session de l'utilisateur : objet session
- Dans un objet commun à tous les utilisateurs : objet application
- Deux types de données :
1. Paramètres http : récupérable avec getParameter (nomduparamètre)
2. Données de l'application : déposés dans la requête http sous forme d'attribut et récupérable avec la méthode : getAttribute (nomdelattribut)

44

# Vue JSP

## Vues réponse de l'application

Exemple : la vue (Page JSP) de confirmation d'ajout d'un livre

- Récupérer le résultat de l'ajout et le titre du livre (types simples)
- Afficher le message

```javascript
&lt;%
Boolean confirmation = (boolean) request.getAttribute("confAjout");
String titre = request.getParameter("titreLivre");
%&gt;
<html><body>
<h1> Confirmation d'ajout du livre </h1>
Le livre &lt;%=titre%&gt;
&lt;% if (confirmation) {%&gt;
a bien été ajouté !
&lt;% } else { %&gt;
n'a pu être ajouté !
&lt;% } %&gt;
</body></html>

# Architecture mono-contrôleur

## Envoi des résultats aux Vues-Réponses

Contrôleur : traitement de la demande de recherche d’un livre

```java
public class ControleurBiblio extends HttpServlet {
public void service (HttpServletRequest req, HttpServletResponse resp) {
// identifier la demande
String op = req.getParameter("operation");
if (op.equals("rechercherLivre")) {
// récupérer les paramètres de la demande
String titre = req.getParameter("titreLivreRecherche");
// Transmettre la demande au modèle
Livre liv = modeleBiblio.rechercherLivre(titre);
// Transmettre le résultat à la page JSP confirmationAjout
req.setAttribute("livreTrouve", liv);
RequestDispatcher d = req.getRequestDispatcher("/vue/AfficherLivre.jsp"));
d.forward(req, res);
}
```

46

Architecture à contrôleurs mixtes

en TP !

47

# Vue JSP

## Vues-Réponses de l'application

### La vue AfficherLivre.JSP

- Récupérer du livre trouvé (type complexe)
- Afficher le détail du livre

```html
&lt;%@ page import="modele.Livre" %&gt;
AfficherLivre.jsp
&lt;%
Livre unLivre = (Livre) request.getAttribute("livreTrouve");
String titreRecherche = request.getParameter("titreLivreRecherche");
%&gt;
<html> <body>
<h1> Résultat de la recherche </h1> <br/>
&lt;% if (unLivre == null) { %&gt;
Aucun livre ne correspond au titre &lt;%= titreRecherche %&gt; !
&lt;% } else { %&gt;
Titre du livre : &lt;%=unLivre.getTitre()%&gt; <br/>
Auteur du livre : &lt;%=unLivre.getAuteur()%&gt;
&lt;% }%&gt;
</body></html>

49

# Vue JSP

## Vues-Réponses de l'application

### Vue – Structure d'une page JSP

&lt;%@ page import="monPackage.monBean" ... %&gt;  // importations de classes

&lt;%  // récupération des données envoyées par le contrôleur.
// Utiliser les variables implicites : request, session et application

%&gt;

<html>
...
// on cherchera ici à minimiser le code java

</html></html>

# Modèle : Java (POJO)

- Modèle de l'application
- Cœur de l'application
- Obtenu en suivant une démarche d'analyse objet
- Codage des diagrammes d'analyse

![img-32.jpeg](img-32.jpeg)

# Modèle : Java (POJO)

Modèle : cœur de l'application !
- Réalise les fonctionnalités de l'application
- Partie réutilisable, donc indépendante de son environnement
- Codé sous forme de classes Java simples ou POJO (Plain Old Java Object)

Conception du Modèle (abordées en trois étapes)
- Sans persistance : les données/objects de l'application sont dans des listes
- Avec persistance basée sur un framework de persistance (ORM)
- Avec persistance basée sur un framework de développement

![img-33.jpeg](img-33.jpeg)

# Modèle sans persistance : Java (POJO)

$\mathcal{O}$  Logique métier – Traduction des diagrammes UML (1)

```java
package modele;
public class Bibliothèque {
private ArrayList<livre> lesLivres;
public Bibliothèque () {
lesLivres = new ArrayList<livre>();
}
public boolean ajouterLivre (String titre, String auteur) {
I = new Livre();     I.setTitre (titre);     I.setAuteur (auteur)
ok = lesLivres.add (I);
return ok;
}
public Livre rechercherLivre (String titre) {
for (Livre I:lesLivres) {
if (I.getTitre == titre) return I;
}
return null;
}
```

52</livre></livre>

# Modèle sans persistance : Java (POJO)

53

- Logique métier – Traduction des diagrammes UML (2)

```java
package modele;

public class Livre {
private String titre;
private String auteur;
public Livre () {}
public void setTitre (String t) { this.titre = t; }
public void setAuteur (String a) { this.auteur = a; }
public String getTitre (String t) { return this.titre; }
public String getAuteur (String a) { return this.auteur; }
}

# Application MVC

## Structure sur le disque

- Organisation des fichiers sur disque

![img-34.jpeg](img-34.jpeg)

# Modèle UML complet

```txt
: «JSP» Page : «serveurHttp» Tomcat
1 : \requête http\
2 : \new\ req : «J2EE» HttpServlet Request
3 : \setParameter\ (\titreLivre\, \t)
4 : \setParameter\ (\auteurLivre\, \al)
5 : \setParameter\ (\operation, \ajoutLivre\)
6 : service ( \req\, \resp )
7 : \getParameter\ (\operaion\)
8 : \t=getParameter\ (\titreLivre\)
9 : \a=getParameter\ (\auteurLivre\)
10 : \res=ajouterLivre\ (\t, \al)
11 : \new\ : «Modèle» Livre
12 : \setTitre\ (\t)
13 : \setAuteur\ (\al)
14 : \setParameter\ (\conf Ajout\, \res\)
15 : \getRequestDispatcher\ (\/vue/confAjout.jsp\)
16 : \new\ (\/vue/confAjout.jsp\)
17 : \forward\ (\req\, \resp\)
18 : \jspService\ (\req\, \resp\)
19 : \getParameter\ (\confAjout\)
20 : \getParameter\ (\ttre\)
```

![img-35.jpeg](img-35.jpeg)

# Modélisation de la persistance

- Application tout en mémoire
- Ensemble d'objets communiquant pour réaliser une fonction précise
- Instanciés en mémoire, objets volatils

- Objectifs
- Stocker les objets du modèle sur un support de persistance (fichier, base de données)
- Principe : indépendance de l'application (modèle) des techniques de persistance

- Problème
- Multitudes de modèles de persistance : BD (relationnel, objet, objet-relationnel), fichier (CVS, XML, RDF, JSON), services Web, cloud, autres
- Evolution de l'architecture de persistance : centralisée, réparties, distribuées
- Différentes spécifications pour la persistance (JDBC, JDO, JPA, autres)
- Différents frameworks de persistance (TopLink, Hibernate, Oracle BC4J, ...)

56

# Persistence des données du modèle

Attention à la dépendance JDBC !

Coder la persistance dans le modèle: à ne pas faire !

|  Vue JSP | Contrôleur Servlet | Modèle Java POJO?!  |
| --- | --- | --- |

```java
public class Bibliothèque {
private Connection cnx;
private PreparedStatement pStAjout; private PreparedStatement pStRech;
public Bibliothèque () {
cnx = DriverManager.getConnection(...);
pStAjout = cnx.prepareStatement("INSERT INTO LIVRE VALUES ..");
pStRech = cnx.prepareStatement("SELECT * FROM LIVRE WEHRE titre = ?");
}
public String ajouterLivre (String titre, String auteur) {
pStAjout.executeUpdate();
}
public Livre rechercherLivre (String titre) {
ResultSet res = pStRech.setString(1, titre).executeQuery();
}
}
```

57

# Modélisation de la persistance

Architectures de l'application complétée

![img-36.jpeg](img-36.jpeg)

Couche de persistance : DAO (Data Access Object)

- Programme (classes) gérant les accès à la base de données
- Garante de l'indépendance du modèle des techniques de persistances
- Possibilité de changer de couche de persistance sans toucher au modèle

58

# Modélisation de la persistance

## Indépendance du modèle

- Interaction du Modèle avec la persistance
- Modèle : donneur d'ordre pour la persistance
- Dépendance du modèle de la persistance!

![img-37.jpeg](img-37.jpeg)

# Indépendance du modèle de persistance

## Principes de conception objet

- 1er principe : inversion de dépendance
- Faire dépendre le modèle d'une abstraction et non de classes d'implémentation

![img-38.jpeg](img-38.jpeg)

# Indépendance du modèle de persistance
## Principes de conception objet

## Inversion de dépendance

|  class Bibliothèque { private IPersistence persistance = null; public String ajouterLivre (String titre, String auteur) { Livre livre = new Client (titre, auteur); this.persistance.stockerLivre (livre) } public String rechercherLivre (String titre) { Livre livre = this.persistance.recherherLivre(titre); return livre; } } | interface IPersistence { public String ajouterLivre (String titre, Str auteur); public String rechercherLivre (String titre); }  |
| --- | --- |
|   | class Persistence implements IPersistence { Connection cnx; public Persistence () { cnx = DriverManager.getConnection(...); } public String ajouterLivre (String titre, Str auteur) { Statement st = cnx.createStatement(); st.executeUpdate("INSRT INTO .."); } public String rechercherLivre (String titre) { Statement st = cnx.createStatement(); st.executeQuery("SELECT * FROM .."); } }  |

61

# Indépendance du modèle de persistance
## Principes de conception objet

- Injection de dépendance
- Initialiser l'objet Persistance sans l'instancier dans le Modèle
- Elle peut être réalisée par le contrôleur qui utilise/instancie le Modèle

```java
public class Bibliothèque {
private IPersistence persistance;
public setPersistence (IPersistence persist) { // méthode d'injection appelée par le contrôleur
this.persistance = persist;
}
public String ajouterLivre (String titre, String auteur) {
Livre livre = new Client (titre, auteur);
this.persistance.stockerLivre (livre)
}
public String rechercherLivre (String titre) {
Livre livre = this.persistance.recherherLivre(titre);
return livre;
}
}
```

62

63

# Indépendance du modèle de persistance

## Injection de dépendance

## Injection de dépendance (objet de persistance)

- Paramétrer la classe de persistance (dépendance pour le modèle) dans le fichier de configuration Web : web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app>
...
<context-param>
<param-name>classe-persistence</param-name>
<param-value>nom-de-la-classe-de-persistence-avec-son-package</param-value>
</context-param>
<context-param>
<param-name>classe-modele</param-name>
<param-value>nom-de-la-classe-du-modèle-avec-son-package</param-value>
</context-param>
...
</web-app>

# Indépendance du Modèle de la persistance

## Injection de dépendance

### Injection de dépendance (objet de persistance)

- Récupérer le nom de la classe de persistance du fichier de configuration et créé un objet de persistance

```java
public class ControleurServlet extends HttpServlet {
private IBibliothèque modeleBiblio;
@Override
public void init() { // l'injection réalisée par le Modèle
super.init (cf);
String nomClasseModele = getServletContext().getInitParameter("classe-modele");
this.modeleBiblio = (IBiliothèque) Class.forName(nomClasseModele).newInstance();
String nomClassePersist = getServletContext().getInitParameter("classe-persistence");
IPersistence persistance = (IPersistence) Class.forName(nomClassePersist)
.getDeclaredConstructor()
.newInstance();
this.modeleBiblio.setPeristance (persistance);
}
...
}
```

64

65

# Modélisation de la persistance

## Eléments à prendre en considération

- Gestion des exceptions
- Réduire la dépendance des drivers de persistance
- Mapping d'exceptions du Driver de base de données en exceptions métier

- Gestion des transactions
- Création et validation (ou annulation)
- Gérer par le DAO ou le client

- Gestion de l'intégrité des données
- A quelle couche attribuer la responsabilité des règles de gestion ?

# Application MVC

## Structure sur le disque

- Organisation sur disque conforme ...

![img-39.jpeg](img-39.jpeg)