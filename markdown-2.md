U

IUT de Paris - Rives de Seine

Université Paris Cité

# Programmation avancée (R5.A.05) :
Développement d'applications d'entreprises avec l'édition JavaEE (Entreprise Edition)

BUT Informatique, 3ème année

Mourad Ouziri
mourad.ouziri@u-paris.fr

# Développement d'applications JEE avec le frameworks Spring :

Spring Core, Spring MVC, Spring Data-JPA et Spring Security

2

# Développement logiciel et qualité

## Rappel

- Application : ensemble de services fonctionnels rendus à l'utilisateur

![img-0.jpeg](img-0.jpeg)

4

# Architecture de découplage

## Principe d'interaction Application-Framework

### Le développeur :
- réalise le code fonctionnel (la couche Modèle de l'architecture MVC)
- en utilisant les interfaces des services (techniques) nécessaires à son bon fonctionnement

### Le framework apporte :
- des bibliothèques d'implémentation des services techniques (persistance, sécurité, etc.)
- les API de spécifications des services techniques
- un outil d'injection de dépendance permettant d'injecter les classes d'implémentation techniques là où se trouvent les interfaces

# Architecture fonctionnelle des applications d'entreprise
## Le dilemme du Modèle

![img-1.jpeg](img-1.jpeg)

Réalise les spécifications fonctionnelles
- Traitements fonctionnels et règles de gestion du métier
- Données/objets du métier
- Ne doit inclure que le code métier de l'application

Evolue au rythme du métier (du client)

Besoin de la brique technique
- Stockage dans la base de données
- Sécurité (authentification, permissions, chiffrement, etc.)
- Gestion de transactions
- Distribution des traitements

Evolue au rythme des technologies (informatiques)

5

# Architecture de découplage

## Injection de dépendances

![img-2.jpeg](img-2.jpeg)

Spécification des services techniques (le quoi ?)
Code métier hébergé par le serveur/framework
Implémentation des services techniques (bibliothèques) (le comment ?)

6

# L'architecture MVC

Le découplage du Modèle des services techniques

DIP (Dependency Inversion Principle)

- Ne pas faire dépendre de modules/services instables (changeants)
- Le modèle ne doit pas dépendre (comment sont codés) des services techniques mais de leur spécification (quoi ?)

![img-3.jpeg](img-3.jpeg)

# L'architecture MVC

Le découplage du Modèle des services techniques

DIP : exemple du service d'accès aux bases de données avec JPA

- JPA (Java Persistence API) est une spécification d'accès aux Bases de données
- Différentes librairies l'implémentent comme Hibernate et EclipseLink

![img-4.jpeg](img-4.jpeg)
Code des services (librairies)

# L'architecture MVC

## Le découplage du Modèle des services techniques

- Injection de dépendance :
- Comment injecter les classes des librairies techniques dans le modèle sans les mentionner explicitement (indépendance) ?
- Solution : Spring core permet d'injecter les objets techniques dans le modèle

![img-5.jpeg](img-5.jpeg)

# Architecture de Spring

# Architecture modulaire

Spring Framework Runtime

![img-6.jpeg](img-6.jpeg)

11

# Spring
## Ses apports

- Framework modulaire et léger
- Fournit tous les services non fonctionnels (techniques) aux applications
- Découplage entre classes : injection de dépendance
- Persistance : sauvegarde et recherche de données dans les bases de données
- Transactions : déclaration, validation et annulation
- Sécurité : authentification, autorisations/droits, etc.
- Web : réception, traitement et réponses aux requêtes http
- Services Web : production et consommation d'API REST/SOAP
- Cloud...
- Réalise l'injection de ces services techniques dans les applications (Spring Core)

12

Spring Core

l'injection de dépendance

# Architecture de découplage

## Injection de dépendances de Spring core

### Objectif

- Compléter le code fonctionnel des applications avec du code technique (service voire fonctionnel) du framework
- En instanciant les objets des classes de service puis les injectant dans les classes de l'application qui les requièrent

![img-7.jpeg](img-7.jpeg)

14

# Spring framework

Injection de dépendances : exemple

- Calcul de la marge bénéficiaire évolutive : problématique

```java
package magasin;
class Produit {
private double prixAchat;
private String categorie;
public double getMarge () {
return prixAchat * 0.25;
}
}
```

- Mauvaise structuration du code (non respect des principes SOLID) :
- Code de calcul de marge noyé dans la classe Produit
- Risque de régression de code dans la classe Produit lors des futures maintenances de calcul de la marge

# Spring framework

Injection de dépendances : exemple

Calcul de la marge bénéficiaire évolutive : solution
- Indépendance de la classe Produit du code de calcul de marge
- Isoler le code de calcul de marge dans une classe dédiée
- L'appeler dans Produit via une interface (pas directement)
- Produit ne doit pas utiliser (dépendre) directement MargeV1

```java
package magasin;
class Produit {
private double prixAchat;
private String categorie;
private IMarge marge;

public double getMarge () {
return marge.calculer (this);
}

public void setCalculMarge (IMarge m) {
this.marge = m;
}
}
```

```java
interface IMarge {
double calculer (Produit p);
}

package services.calcul;
class MargeV1 implements IMarge {
double pourc = 0.25;
double calculer (Produit p) {
return p.getPrixAchat () * pourc;
}
void setPourcentage (double p) {
this.pourc = p;
}
}
```

15

# Spring framework

Injection de dépendances : exemple

- Calcul de la marge bénéficiaire évolutive : problème
- Classe Produit n'est pas fonctionnelle car marge non initialisé, donc incomplète !
```java
Produit p = new Produit (1, 50, "sport");
p.getMarge(); // lève NullPointerException
```
- Car elle dépend de la classe **MargeV1** qui ne lui est pas donnée

- Solution : injecter un objet MargeV1 dans Produit avant de l'utiliser
![img-8.jpeg](img-8.jpeg)

17

# Spring framework

Injection de dépendances : configurations

- Pour réaliser l'injection de dépendance, il faudrait indiquer à Spring :
- La classe (la dépendance) à instancier/injecter : le bean à publier !
- L'attribut ou le paramètre (de constructeur ou de méthode) qui recevra l'objet créé (bean)

- Définir les classes (de service) injectables :
- Avec annotation des classes de service
- Dans un fichier de configuration XML
- Dans des classes de configuration Java

# Spring framework

Injection de dépendances : configuration dans un fichier xml

Ficher de configuration des classes (de services) injectables beans.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemalocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd">
<bean class="services.calcul.MargeV1" id="idCalculMargeV1">
<property name="pourcentage" value="0.3"></property>
</bean>
<bean class="services.calcul.MargeV2" id="idCalculMargeV2">
<constructor-arg name="pourcentage" type="Double" value="0.2"></constructor-arg>
<property name="categSport" value="0.3"></property>
<property value="0.5"></property>
</bean>
</beans>
```

18

19

# Spring framework

Injection de dépendances : configuration dans un fichier xml

- Configuration d'objets encapsulés

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemalocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd">
<bean class="magasin.Product" id="idProduit" scope="prototype">
<property name="marge" ref="idCalculMargeV1"></property>
</bean>
<bean class="services.calcul.MargeV1" id="idCalculMargeV1" scope="singleton">
<property name="pourc" value="0.3"></property>
</bean>
</beans>
```

# Spring framework

Injection de dépendances : configuration dans un fichier xml

- Interfaces d'injection de dépendance de Spring core
```
org.springframework.beans.BeanFactory etc
org.springframework.context.ApplicationContext
```
Elles instancient (publient) les objets Java POJO (beans) et les injectent aux endroits indiqués par le développeur (@Autowired)

- Classes d'implémentation pour les injections
```
org.springframework.beans.ClassPathXmlApplicationContext
```
ApplicationContext context = new ClassPath XmlApplicationContext("beans.xml");
```
Les classes injectables sont configurées dans un fichier xml (beans.xml)

- Classes d'implémentation pour les injections par annotations
```
org.springframework.context.annotation.AnnotationConfigApplicationContext
```
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
Config.class est une classe Java de configuration (annotée @Configuration)

- Classes d'implémentation pour les applications web
```
org.springframework.web.context.ContextLoaderListener (param web.xml contextConfigLocation)

20

# Spring framework

Injection de dépendances : configuration dans un fichier xml

## Exemple d'un code d'injection de dépendance

```java
class MonAppli {
// instancier la classe d'injection de dépendance de Spring
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
// Créer des objets à l'aide de Spring Core
Produit p1 = context.getBean(Produit.class); // réalise l'injection de IMerge
double marge = p.getMarge () ;
// créer un objet Produit hors du contexte de Spring
IMarge margev1 = context.getBean("idCalculMargeV1", IMergeV.class);
IMarge margev2 = (IMargeV) context.getBean("idCalculMargeV2");
Produit p2 = new Produit (10);
p2.setCalculMarge (margev1);
double marge = p2.getMarge () ;
double marge1 = margev1.calculer (p2);
double marge1 = margev2.calculer (p2);
}
```

21

# Spring framework

Injection de dépendances : configuration par annotations

Fichier de configuration pour les injections par annotation des classes

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemalocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
<!-- autoriser la configuration par annotations dans les classes -->
<context:annotation-config>
<!-- indiquer les packages où rechercher les classes annotées -->
<context:component-scan base-package="services.calcul,stock"></context:component-scan>
</bean>
```

22

# Spring framework

Injection de dépendances : configuration par annotations

- Annotations pour déclarer des classes (services) injectables
- @Component : déclare une classe/bean de service générique injectable
- @Service : sous annotation de @Component et permet de déclarer un service/bean fonctionnel (classe du Modèle MVC)
- @Repository : sous annotation de @Component et permet de déclarer un bean de type service de persistance injectable
- @Controller : sous annotation de @Component et permet de déclarer une classe/bean de type « contrôleur dédié MVC »
- @Bean : permet de déclarer une méthode qui crée et publie un bean

- Annotations pour réaliser l'injection
- @Autowired : attribut ou paramètre où injecter/stocker l'objet service créé
- @Required : injection requise sinon exception BeanInitializationException
- @Qualifier : pour distinguer les classes de service implémentant la même interface

23

# Spring framework

Injection de dépendances : annotations

$\mathcal{O}$  Définir la classe/bean réalisant le service : annotation @Service

```java
package services.calcul;
@Service
class MargeV1 implements IMerge {
double pourc = 0.25;
double calculer (Produit p) {
return p.getPrixAchat() * pourc;
}
}
```

```java
interface IMerge {
double calculer (Produit p);
}
```

$\mathcal{O}$  Injector un objet service/bean

- Spring recherche les classes du type (autowiring by type) de l'attribut accueillant l'objet/bean (ici IMerge)

```java
package stock;
@Component
class Produit {
private double prixAchat;
private String categorie;
@Autowired
private IMerge marge;
public double getMarge() {
return marge.calculer (this);
}
}

25

# Spring framework

Injection de dépendances : classes de configuration

- Classes Java dites de configuration pour l'injection par annotation
- @Configuration (hérite de @Component) : déclare la classe de configuration
- @ComponentScan : indique les packages où se trouvent les classes annotées (généralement les bean/services injectables ou les classes qui les utilisent)

```java
@Configuration
@ComponentScan(basePackages="stock, services.calcul")
class ConfigInjections {
}
```

```java
class Appli {
ApplicationContext context = new AnnotationConfigApplicationContext(configInjections.class);
double calculerMarge (Produit p) {
Produit p = context.getBean (Produit.class);
return p.getMarge();
}
}

# Spring framework

Injection de dépendances : classes de configuration

Factory de bean/services dans le classes de configuration

- @Configuration : déclarer la classe de configuration
- @Bean : déclarer les méthodes que Spring doit appeler pour instancier un bean

|  @Configuration class BeanConfig { @Bean IMarge construireMarge () { return new MargeV1 (); } } | @Component class Produit IProduit { @Autowired private IMarge marge; public double getMarge () { return marge.calculer (this); } }  |
| --- | --- |

```java
class Appli {
ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
double calculerMarge (Produit p) {
IProduit p = (IProduit) context.getBean (IProduit.class);
return produit.getMarge ();
// ou
IMarge marge = (IMarge) context.getBean ("construireMarge");
return marge.calculer (p);
}
}
```

26

27

# Spring framework

Injection de dépendances : portée des objets injectés

- Types d'injection (seulement pour la configuration par fichier xml)
- byName : injection basée sur le nom de la propriétés/attribut qui reçoit l'objet
- byType : injection basée sur le type/classe de la propriétés/attribut reçoit l'objet
- constructor : injection basée sur les paramètres du constructeur

```xml
<bean autowire="byType" id="store" class="services.calcul.MargeV1">
</bean>
```

Remarque : l'injection avec @Autowired ne se fait que par type

28

# Spring framework

Injection de dépendances : portée des objets injectés

https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html

- Portée d'un bean/objet injecté, définie avec @Scope
- Singleton (par défaut) : un seul est instancié et utilisé pour toutes les injections (dans le contexte Spring)
- Prototype : un nouvel objet est instancié à chaque injection
- Request (Web) : un objet est instancié pour chaque requête http
- Session (Web) : un objet est instancié par session Web (utilisateur)
- Application(Web) : un objet est instancié pour l'ensemble de l'application Web

```txt
@Service
@Scope ("prototype")
class MargeV1 implements IMarge {
...
}
```

```txt
<beans>
<bean class="..." scope="prototype" id="..."} </bean>
</beans>

29
Spring MVC

# Architecture de Spring MVC

## Architecture à contrôleurs mixtes

- Deux types de contrôleurs :
- Frontal : fourni par Spring MVC (classe **DispatcherServlet**)
- Dédié (à une fonction) : classe Java codée par le développeur et annotée avec @Controller

![img-9.jpeg](img-9.jpeg)

# Architecture de Spring MVC

## Architecture à contrôleurs mixtes

- Traitement d'une requête http :
- Handler Mapping : se sert des annotations @Controller pour déterminer les contrôleurs dédiés

![img-10.jpeg](img-10.jpeg)

# Architecture de Spring MVC

## Architecture à contrôleurs mixtes

- Traitement d'une requête http :
- Handler Mapping : se sert des annotations @Controller pour déterminer le contrôleur dédié à la requête

![img-11.jpeg](img-11.jpeg)

33
Spring Data et JPA

34

# Architecture de persistance

## Problématique de dépendance de la persistance

### Objectif
- Sauvegarder les données/objets de l'application (objet du modèle)
- Répliquer les opérations sur les données du modèle qui le nécessitent (enregistrement, modification, suppression, recherche, etc.) sur le support de persistance

### Caractéristiques des supports de persistance
- Multitudes de supports de persistance : BD (relationnel, NoSQL, objet, objet-relationnel), SGF (CVS, XML, RDF, JSON), Services Web, Cloud, autres
- Evolution de l'architecture de persistance : centralisée, réparties, distribuées
- Différentes spécifications pour la persistance (JDBC, JDO, JPA, autres)
- Différents frameworks de persistance (TopLink, Hibernate, Oracle BC4J, autres)

# Architecture de persistance

## Problématique de dépendance de la persistance

- Architectures de l'application complétée avec la persistance

|  Vue JSP | Contrôleur Servlet | Modèle Java POJO | Persistance Java  |
| --- | --- | --- | --- |

- ☑ Couche de persistance :
- Programme (classes) gérant les accès à la base de données
- Garante de l'indépendance du modèle des techniques de persistances
- Possibilité de changer de couche de persistance sans toucher au modèle

35

# Architecture de persistance

## Problématique de dépendance de la persistance

### Éléments d'architecture

- Fait partie de la brique technique/technologique (et no du fonctionnel/métier)
- Donc évolutifs (instables) par hypothèse
- Séparer la persistance du Modèle

![img-12.jpeg](img-12.jpeg)

### Dilemme de la persistance des objets du modèle

- Le Modèle (métier) faite appel à la persistance pour réaliser ses fonctions
- Mais il ne doit pas en dépendre
- Pour pouvoir changer de support de persistance à moindre coûts sans impacter le Modèle

# Architecture de persistance

## Inversion de dépendance Modèle-&gt;Persistence

- Couplage faible du Modèle (inversion de dépendance)
- Le Modèle utilise la persistance via une interface de services de persistance
- Cette interface est implémentée à l'aide de Spring Data

![img-13.jpeg](img-13.jpeg)

# Architecture de persistance

- Interface de persistance :
- Spécification d'un ensemble de fonctions (services) de persistance de données (recherche, insertion, suppression, modification, etc.)
- JDBC, JPA et Spring Data définissent des interfaces de persistance

- Niveaux d'abstraction (simplification) de la persistance

![img-14.jpeg](img-14.jpeg)

Interfaces de persistance

JPA

https://docs.oracle.com/javaee/7/api/javax/persistence/EntityManager.html

Interface de persistance définie par JPA : EntityManager

```java
package javax.persistence;
public interface EntityManager {
void persist (Object o); // Enregistrer dans la BD
Void remove (Object o); // Supprimer de la BD
<t> T find (Class<t> uneClasse, Object pk); // Recherche par PK dans la BD
Query createQuery (String requêteSQL); // Rechercher dans la BD
<t> T merge (T o); // Rattacher un objet au manager
void refresh(Object o) // Restaurer l'objet (de la BD)
Void flush (); // Synchroniser avec la BD
void clear (); // Réinitialiser le contexte
}
```

39</t></t></t></t>

# Interfaces de persistance

# Spring Data JPA

https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html

$\mathcal{F}$  Interface de persistance : CrudRepository<t, id="">

|  Modifier and Type | Method | Description  |
| --- | --- | --- |
|  long | count() | Returns the number of entities available.  |
|  void | delete(T entity) | Deletes a given entity.  |
|  void | deleteAll() | Deletes all entities managed by the repository.  |
|  void | deleteAll(Iterable <? extends T> entities) | Deletes the given entities.  |
|  void | deleteAllById(Iterable <? extends ID> ids) | Deletes all instances of the type T with the given IDs.  |
|  void | deleteById(ID id) | Deletes the entity with the given id.  |
|  boolean | existsById(ID id) | Returns whether an entity with the given id exists.  |
|  Iterable <T> | findAll() | Returns all instances of the type.  |
|  Iterable <T> | findAllById(Iterable <ID> ids) | Returns all instances of the type T with the given IDs.  |
|  Optional <T> | findById(ID id) | Retrieves an entity by its id.  |
|  <S extends T> | save(S entity) | Saves a given entity.  |
|  <S extends T> | saveAll(Iterable <S> entities) | Saves all given entities.  |
|  Iterable <S> |  |   |</t,>

11

# Interfaces de persistance

# Spring Data JPA

https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html

☑ Interface de persistance : JpaRepository<t, id="">

☑ C'est une interface qui étend CrudRepository<t, id=""> en mettant à disposition donc de plus de méthodes de persistance

|  Modifier and Type | Method | Description  |
| --- | --- | --- |
|  void | deleteAllByIdInBatch(Iterable <ID> ids) | Deletes the entities identified by the given ids using a single query.  |
|  void | deleteAllInBatch() | Deletes all entities in a batch call.  |
|  void | deleteAllInBatch(Iterable <T> entities) | Deletes the given entities in a batch which means it will create a single query.  |
|  default void | deleteInBatch(Iterable <T> entities) | Deprecated. Use deleteAllInBatch(Iterable) instead.  |
|  <S extends T> List <S> | findAll(Example <S> example) |   |
|  <S extends T> List <S> | findAll(Example <S> example, Sort sort) |   |
|  void | flush() | Flushes all pending changes to the database.  |
|  T | getById(ID id) | Deprecated. use getReferenceById(ID) instead.  |
|  T | getOne(ID id) | Deprecated. use getReferenceById(ID) instead.  |
|  T | getReferenceById(ID id) | Returns a reference to the entity with the given identifier.  |
|  <S extends T> List <S> | saveAllAndFlush(Iterable <S> entities) | Saves all entities and flushes changes instantly.  |
|  <S extends T> S | saveAndFlush(S entity) | Saves an entity and flushes changes instantly.  |</t,></t,>

# Implémentation de la persistance
## Spring Data JPA

https://docs.spring.io/spring-data/data-jpa/docs/current/api/org/springframework/data/jpa/repository/support/SimpleJpaRepository.html

Exemple d'une classe d'implémentation de la persistance de Spring Data

```java
package org.springframework.data.jpa.repository.support;
@Repository
@Transactional
public class SimpleJpaRepository<t,id> implements JpaRepository <t,id>,... {
private EntityManager em; // interface de JPA, implémentée par Hibernate
private EntityInformation eInfo; // interface Spring Data injectée via le constructeur
public <s extends="" t=""> save (S entity) {
if (eInfo.isnew (entity)
return em.persist (entity);
else
return em.merge (entity)
}
}
</s></t,id></t,id>

# Architecture de la persistance

## Utilisation de Spring Data

- Schéma de l’utilisation des interfaces de persistance génériques (appelée *Repository*) de Spring Data

![img-15.jpeg](img-15.jpeg)

# Interface de persistance
## Spring Data JPA

- Spécialisation de JpaRepository pour la persistance de Livre

```java
@Repository
public interface LivresRepository extends JpaRepository <livre, long=""> {
}
```

- Utilisation

```java
@Service
public class Bibliothèque {
@Autowired
private LivresRepository livreRepo;
public Livre ajouterLivre (String titre) {
Livre livre = new Livre();
livre.setTitre (titre);  // l'identifiant du livre est automatique
return livreRepo.save (livre);
}
}
```

44</livre,>

45

# Interface de persistance

## Spring Data JPA

https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

- Ajout de méthodes de persistance en utilisant les mots clés du langage JPA

```java
@Repository
public interface LivresRepository extends JpaRepository <livre, long=""> {
List<livre> findByTitre (String titre);
List<livre> findDistinctByTitre (String titre);
List<livre> findByTitreIgnoreCase (String titre);
List<livre> findByTitreAndAuteur (String titre, String auteur);
List<livre> findByTitreIsNull ();
List<livre> findByTitreNot (String titreAEviter);
List<livre> findByTitreContaining (String sous-titre);
List<livre> findByDatePublicationAfter (Date datePubli);
List<livre> findByEmpruntéTrue ();
List<livre> findByTitreLike (String titre);
}
```</livre></livre></livre></livre></livre></livre></livre></livre,>

46

# Interface de persistance

Spring Data JPA

https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

- Ajout de méthodes de persistance en utilisant des requêtes nommées

```java
@Repository
public interface LivresRepository extends JpaRepository <livre, long=""> {
@Query ("SELECT liv FROM Livres liv WHERE liv.titre = :unTitre")
List<livre> rechercheParTitre (@Param ("unTitre") String titre);

@Query ("SELECT liv FROM Livres liv WHERE liv.titre = :unTitre and liv.auteur &lt;&gt; :unAuteur")
List<livre> rechercheParTitreEtAuteurDifferent (
@Param ("unTitre") String titre,
@Param ("unAuteur") String auteur
);
}</livre></livre,>

# Architecture du Modèle MVC

- Types de classes du Modèle :
- Classes de type Service :
- Classes qui implémentent les services fonctionnels de l'application
- Elle sont appelées par le contrôleur et elles font appel à la couche de persistance
- Classe Entité : classes qui représentent les objets/données de l'application

![img-16.jpeg](img-16.jpeg)

# Architecture de persistance

## Utilisation de Spring Data

- Architecture de classes (et interfaces) du Modèle avec la couche de persistance

![img-17.jpeg](img-17.jpeg)

# Classe de type Entité (JPA Entity)

## Configuration

### Classe de type Entité

- C'est une classe du Modèle, donc une classe Java (POJO)
- Classe Java pure : se focalise sur le traitement fonctionnel/métier
- Annotée : pour être qualifiée en Entité

![img-18.jpeg](img-18.jpeg)
POJO

![img-19.jpeg](img-19.jpeg)

![img-20.jpeg](img-20.jpeg)
Entity

![img-21.jpeg](img-21.jpeg)

![img-22.jpeg](img-22.jpeg)
Entity class

# Classe de type Entité

## Exemple d'annotations

$\mathcal{F}$  Les annotations sot définies dans JPA

```java
import javax.persistence.*;
@Entity
@Table (name="T_Adherent")
public class Adherent implements Serializable {
@Id @GeneratedValue
private Long numero;
@Column (nullable=false, length=50)
private String nom;
public Long getNumero() {
return numero;
}
...
}
```

```java
please Entité
```

```java
create table T_Adherent (
numero Integer Primary Key,
nom varchar2(50) not null,
)
create sequence seqNumero;
```

Table correspondante 50

# Gestion des objets Entity

## Persistance des objets dans la base de données

- Quelques annotations élémentaires définies par JPA
- @Entity : attribue le caractère entité de persistance à une classe
- @Table (name=" ... ", schema=" ... ") : définie le nom de la table associée
- @Id : définie l'attribut qui sera déclaré « clé primaire »
- @GeneratedValue (strategy=GenerationType.AUTO, GenerationType.IDENTITY,..)
- @Column (name=" ... ", nullable = false, length=15, unique=true, ...) : définie les caractéristiques de la colonne correspondant à l'attribut
- @Transient : définie un attribut no persistent (non stocké dans la table)

51

52

# Gestion des objets Entity

## Règles d'écriture

- Quelques règles de codage d'une classe de type Entity
- Respect de la convention Java Bean : au moins un constructeur public sans paramètres, getters et setters des attributs avec la règle de nommage camelcase
- Pas d'attributs déclarés Final
- Peut hériter de classes non Entity
- Les attributs ne peuvent être static
- Ne peut employer les thread

# Gestion des objets Entity

## Cycle de vie

- Cycle de vie d’un objet Entity dans JPA (EntityManager)

![img-23.jpeg](img-23.jpeg)

# Persistence des relations entre Entities

$\mathcal{F}$  Relations mappées selon les annotations suivantes :

|  Type d'association |   |   | Annotation  |
| --- | --- | --- | --- |
|  Client | 0..1 | Adresse | @OneToOne  |
|  Client | 0..1 | Commande | @OneToMany  |
|  Commande | 0..* | Client | @ManyToOne  |
|  Commande | * | Produit | @ManyToMany  |

$\mathcal{F}$  Cas particulier: @ManyToOne unidirectionnelle

# Persistence des relations entre Entities

Exemple @OneToOne

Association bidirectionnelle : mappedBy indique l'attribut implémenté par la clé étrangère représentant l'association dans la BD (ici adresseClt)

![img-24.jpeg](img-24.jpeg)

55

# Persistence des relations entre Entities

Exemple @OneToMany et @ManyToOne

![img-25.jpeg](img-25.jpeg)

mappedBy : la clé étrangère de l'association est clientCde se trouve dans la table de l'entité Commande

56

# Persistence des relations entre Entities

57

Exemple @ManyToMany

|  @Entity public class Commande { @Id private int numCde; @ManyToMany @JoinTable(name="LIGNE_CDE", joinColumns= @JoinColumn(name="CMD_FK", referencedColumnName="numCde"), inverseJoinColumns= @JoinColumn(name="PROD_FK", referencedColumnName="numProd")) private List lesProds; | @Entity public class Produit { @Id private int numProd; private String designation; @ManyToMany (mappedBy="lesProds") private List lesCdes; ... }  |
| --- | --- |

JoinTable: la table de l’association est : LIGNE_CDE (#CMD_FK, #PROD_FK)

# Persistence des relations entre Entities

## Propagation de la persistance

- Propager la persistance, suppression ou attachement à travers les associations

```csharp
@Entity
public class Client {
@Id
private int numClt;
private String nomClt;
@OneToOne
(cascade=CascadeType.PERSIST)
private Adresse adresseClt;
}
```

```csharp
@Entity
public class Adresse {
private String rue;
private String ville;
@OneToOne (mappedBy= adresseClt)
private Client proprio;
...
}
```

- La persistance d'un objet client entraîne la persistance de son adresse
- Valeurs possibles : PERSIST, REMOVE, MERGE, REFRESH, ALL

58

# Persistence des relations entre Entities
## Propagation du chargement en mémoire

A quel moment les objets associés sont récupérés de la base de données ?

```c
@Entity
public class Client {
@Id
private int numClt;
@OneToMany (fetch=FetchType.LAZY)
private List<command/>cdeClt;
}
```

```c
@Entity
public class Commande {
@Id
private int numCde;
@ManyToOne (fetch=FetchType.EAGER)
private Client clientCde;
}
```

## Attribut fetch

LAZY : association passive, chargement à la demande (val. par défaut de @OneToMany)
EAGER : association active, chargement systématique (val. par défaut de @ManyToOne)

Par défaut, les commandes d’un client ne sont pas systématiquement chargées (donc inaccessibles). Par contre, à partir d’une commande, son client l’est. 59

# Persistence des relations entre Entities

## Persistence de la relation d'héritage

### Exemple

```csharp
@Entity
public class Employe {
@Id
private int num;
private String nom;
...
}
```

```csharp
@Entity
public class EmployeTPlein extends Employe {
private float salaire;
}
@Entity
public class EmployeTPartiel extends Employe {
private float heureTravaillees;
}
```

L'héritage n'est pas défini dans le modèle relationnel
Donc, nécessité de définir un schéma relationnel pour la relation d'héritage

60

61

# Persistence des relations entre Entities

## Persistence de la relation d'héritage

### Trois types de mapping

- Single Table : les classes de la hiérarchie sont stockées dans une seule table
- Joined tables : chaque classe de la hiérarchie est stockées dans une table spécifique à elle. Les tables sont reliées
- Table per classe (peu efficace) : chaque classe de la hiérarchie est stockée dans une table spécifique à elle. Les tables sont indépendantes les unes des autres et chaque table aura toute les propriétés de la classe même celle héritées. Le polymorphisme est donc complexe à gérer.

# Persistence des relations entre Entities

## Persistence de la relation d'héritage

### Single Table Mapping

```csharp
@Entity
@Inheritance(
strategy=InheritanceType.SINGLE_TABLE
)
@DiscriminatorColumn(
name="TYPE_EMP",
discriminatorType="STRING",
length=7
)
public class Employe {
@Id
private int num;
private String nom;
}
```

```csharp
@Entity
@DiscriminatorValue(value="PLEIN")
public class EmployeTPlein extends Employe {
private float salaire;
}
@Entity
@DiscriminatorValue(value="PARTIEL")
public class EmployeTPartiel extends Employe {
private float heureTravaillees;
}
```

Résultat : Employe (num, nom, salaire, heuresTravaillees, TYPE_EMP)

# Persistence des relations entre Entities

## Persistence de la relation d'héritage

### Joined Table Mapping

|  @Entity @Inheritance(strategy=InheritanceType.JOINED) public class Employee { @Id @Column(name="NUM_EMP") private int num; private String nom; ... } | @Entity @PrimaryKeyJoinColumn(name="NUM_EMP") public class EmployeeTPlein extends Employee { private float salaire; } @Entity @PrimaryKeyJoinColumn(name="NUM_EMP") public class EmployeeTPartiel extends Employee { private float heureTravaillees; }  |
| --- | --- |

Résultat : Employee (num, nom),  EmployeeTPlein (#num, salaire),

EmployeeTPartiel (#num, heuresTravaillees)

63

# Persistence des relations entre Entities

## Persistence de la relation d'héritage

Table Per Class Mapping

|  @Entity @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) public class Employe { @Id private int num; private String nom; } | @Entity public class EmployeTempsPlein extends Employe { private float salaire; }  |
| --- | --- |
|   | @Entity public class EmployeTempsPartiel extends Employe { private float heureTravaillees; }  |

Résultat : Employe (num, nom)
EmployeTPlein (num, nom, salaire)
EmployeTPartiel (num, nom, heuresTravaillees)

64

# Résumé de l'architecture à base de framework

![img-26.jpeg](img-26.jpeg)

Framework JEE (Spring, Serveur d'application)

Spécification des services techniques (le quoi ?)
Code métier hébergé par le serveur/framework

Implémentation des services techniques (bibliothèques) (le comment ?)
65

# Architecture des applications JEE

Résumé de l'injection de dépendances par le fram

![img-27.jpeg](img-27.jpeg)

POJO (Enterprise Java Beans) :

- Garder le code technique hors du modèle et l'injecter au besoin

67

Sécurité des applications JEE avec le
frameworks Spring Security

# Sécurité des applications

- Schéma de sécurisation de l'application : pare-feu de sécurité

![img-28.jpeg](img-28.jpeg)

# Sécurité
## Fonctions canoniques

- Authentification
- Identifier l'utilisateur (savoir qui a envoyé chaque requête)

- Autorisation (contrôle d'accès)
- Définir et contrôler ce que peux faire le client une fois authentifié

- Confidentialité des données
- Chiffrer les données « sensibles » pour qu'elles ne soient pas lisibles par un tiers externe

- Intégrité des données
- S'assurer que les données n'ont pas été modifiées par un tiers externe à l'application

- Non-répudiation :
- Prouver qu'un utilisateur est bel et bien lui qui était à l'origine d'une action

70

# Sécurité

## Quelques vulnérabilités/attaques

- Injections SQL (côté serveur)
- Un attaquant saisit dans en login/pwd : "toto; delete from users; --"
- La table users est entièrement effacée (si elle existe !)

- Injections XSS (Cross-Site Scripting)/Javascript (côté navigateur)
- Un attaquant saisit dans login/nom : "xx; <script>document.cookie()</script>"
- Il récupère les données de l'admin (cookie, id de session, etc.) qui consulte les logins

- CSRF Cross-Site Request Forgery (côté navigateur)
- L'attaquant envoie à un utilisateur connecté/authentifié une pseudo page/image/email piégée qui inclut un script js qui exécute une action sur le serveur à son insu (comme faire un virement !)

71

# Sécurité

## Quelques vulnérabilités/attaques

- Session fixation (côté navigateur)
- L'attaquant se connecte sur un serveur et force un utilisateur à utiliser s'authentifier avec sa session
- L'attaquant peut réaliser toutes les actions permises pour l'utilisateur

- CORS (Cross Origin Resource Sharing)
- Assouplissement de SOP : permettre aux scripts d'envoyer des requêtes à des serveurs qui ne sont pas ceux d'où ils proviennent
- L'attaquant peut donc aspirer des données utilisateur vers son serveur

- DDoS (Distributed Denial of Service)
- Surcharge des ressources serveur de manière à le rendre indisponible

# Sécurité

## Principe

- La sécurité est assurée par une suite/chaîne de filtres appelée FilterChain
- Elle est structurée suivant le design pattern Chain of responsibility
- De sorte à pouvoir ajouter/retirer des filtres plus facilement

![img-29.jpeg](img-29.jpeg)

# Sécurité

## Principe des filtres de sécurité Spring

- Chaque filtre est spécialisé sur une tâche de sécurité précise
- L'ordre d'exécution des filtres est important (ex : l'authentification doit précéder l'autorisation)

![img-30.jpeg](img-30.jpeg)

- Chaque filtre délègue le traitement à un Manager

![img-31.jpeg](img-31.jpeg)

# Filtres de sécurité de Spring

- Plusieurs chaines de filtres peuvent être définies (par type de requêtes)
- Le proxy délègue le traitement de la sécurité à la chaîne de filtres appropriée

![img-32.jpeg](img-32.jpeg)

75

# Sécurité

## Filtres de sécurité

- Quelques filtres activés par défaut
- CsrfFilter : protège contre les attaques CSRF (gestion du token csrf)
- UsernamePasswordAuthenticationFilter : authentifie les utilisateurs des requêtes /login avec username/password
- BasicAuthenticationFilter : authentifie à partir des attributs header de type basic
- HeaderWriterFilter : insère des attributs de sécurité en entête http (no-cache, HttpOnly, nosniff, csp, etc.)
- SessionManagementFilter : gère la session de l'utilisateur authentifié
- LogoutFilter : assure la déconnexion effective (nettoyage du contexte de sécurité)

76

# Sécurité

## Filtres de sécurité

### Quelques filtres activés par défaut (suite)

- SecurityContextHolderAwareRequestFilter : donne les informations sur l'utilisateur connecté, ses rôles, etc.
- DefaultLoginPageGeneratingFilter : construit une page d'authentification par défaut
- DefaultLogoutPageGeneratingFilter : construit une page de déconnexion par défaut
- AnonymousAuthenticationFilter : donne un objet Authentication à SecurityContextHolder
- ExceptionTranslationFilter : traduit les exceptions Java en réponses http
- FilterSecurityInterceptor : vérifie les autorisations de l'utilisateur authentifié

# Authentification

## Deux types d'authentification

- Authentification dite *Stateful* (stockées côté serveur)

![img-33.jpeg](img-33.jpeg)

# Authentification

## Deux types d'authentification

- Authentification dite *Stateless* (stockées côté client)

![img-34.jpeg](img-34.jpeg)

# Authentication

## Architecture du système d'authentification

Composants de l'architecture d'authentification

![img-35.jpeg](img-35.jpeg)

80

# Authentification

## Objets de l'authentification

https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.html

- AuthenticationManager :
- Interface implémentée par ProviderManager
- Permet de vérifier plusieurs types d'authentification (en mémoire, en base de données, ldap, etc.)
- Appelle les AuthenticationProvider pour authentifier l'utilisateur

![img-36.jpeg](img-36.jpeg)

![img-37.jpeg](img-37.jpeg)

- Un AuthenticationProvider utilise un UserDetailsService et un PasswordEncoder

# Authentication

# API

https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.

# L'interface AuthenticationManager

Package org.springframework.security.authentication

# Interface AuthenticationManager

All Known Implementing Classes:

ObservationAuthenticationManager, ProviderManager

public interface AuthenticationManager

Processes an Authentication request.

Method Summary

|  All Methods | Instance Methods | Abstract Methods |   |
| --- | --- | --- | --- |
|  Modifier and Type | Method |  | Description  |
|  Authentication | authenticate (Authentication authentication) |  | Attempts to authenticate the passed Authentication object, returning a fully populated Authentication object (including granted authorities) if successful.  |

# Authentication

# API

https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationProvider.

# L'interface AuthenticationProvider

Package org.springframework.security.authentication

# Interface AuthenticationProvider

All Known Implementing Classes:

AbstractJaasAuthenticationProvider, AbstractLdapAuthenticationProvider, AbstractUserDetailsAuthenticationProvider,

ActiveDirectoryLdapAuthenticationProvider, AnonymousAuthenticationProvider,

AuthenticationManagerBeanDefinitionParser.NullAuthenticationProvider, CasAuthenticationProvider, DaoAuthenticationProvider,

DefaultJaasAuthenticationProvider, JaasAuthenticationProvider, JwtAuthenticationProvider, LdapAuthenticationProvider,

OAuth2AuthorizationCodeAuthenticationProvider, OAuth2LoginAuthenticationProvider, OidcAuthorizationCodeAuthenticationProvider

OpaqueTokenAuthenticationProvider, OpenSaml4AuthenticationProvider, PreAuthenticatedAuthenticationProvider,

RememberMeAuthenticationProvider, RunAsImplAuthenticationProvider, TestingAuthenticationProvider

public interface AuthenticationProvider

Indicates a class can process a specific Authentication implementation.

Method Summary

|  All Methods | Instance Methods | Abstract Methods  |
| --- | --- | --- |
|  Modifier and Type | Method | Description  |
|  Authentication | authenticate(Authentication authentication) | Performs authentication with the same contract as AuthenticationManager.authenticate(Authentication).  |
|  boolean | supports(Class <?> authentication) | Returns true if this AuthenticationProvider supports the indicated Authentication object.  |

# Authentication

# API

https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.

L'interface Authentication : représenté l'utilisateur avant et après authentication

Package org.springframework.security.core

# Interface Authentication

All Superinterfaces:

Principal, serializable

Method Summary

|  All Methods | Instance Methods | Abstract Methods |   |
| --- | --- | --- | --- |
|  Modifier and Type |   | Method | Description  |
|  Collection <? extends GrantedAuthority> |   | getAuthorities() | Set by an AuthenticationManager to indicate the authorities that the principal has been granted.  |
|  Object |   | getCredentials() | The credentials that prove the principal is correct.  |
|  Object |   | getDetails() | Stores additional details about the authentication request.  |
|  Object |   | getPrincipal() | The identity of the principal being authenticated.  |
|  boolean |   | isAuthenticated() | Used to indicate to AbstractSecurityInterceptor whether it should present the authentication token to the AuthenticationManager.  |
|  void |   | setAuthenticated(boolean isAuthenticated) | See isAuthenticated() for a full description. 83  |

# Authentication

# API

https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetailsService.

# L'interface UserDetailsService

Package org.springframework.security.core.userdetails

# Interface UserDetailsService

All Known Subinterfaces:

UserDetailsManager

All Known Implementing Classes:

CachingUserDetailsService, InMemoryUserDetailsManager, JdbcDaoImpl, JdbcUserDetailsManager, LdapUserDetailsManager, LdapUserDetailsS

public interface UserDetailsService

Core interface which loads user-specific data.

It is used throughout the framework as a user DAO and is the strategy used by the DaoAuthenticationProvider.

The interface requires only one read-only method, which simplifies support for new data-access strategies.

See Also:

DaoAuthenticationProvider, UserDetails

# Method Summary

|  All Methods | Instance Methods | Abstract Methods  |
| --- | --- | --- |
|  Modifier and Type | Method | Description  |
|  UserDetails | loadUserByUsername(String | username) Locates the user based on the username.  |

# Authentication

# API

https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetailsService.

# L'interface UserDetails

Package org.springframework.security.core.userdetails

# Interface UserDetails

All Known Implementing Classes:

InetOrgPerson, LdapUserDetailsImpl, Person, User

Method Summary

|  All Methods | Instance Methods | Abstract Methods | Default Methods |   |
| --- | --- | --- | --- | --- |
|  Modifier and Type |   | Method |   | Description  |
|  Collection <? extends GrantedAuthority> |   | getAuthorities() |   | Returns the authorities granted to the user.  |
|  String |   | getPassword() |   | Returns the password used to authenticate the user.  |
|  String |   | getUsername() |   | Returns the username used to authenticate the user.  |
|  default boolean |   | isAccountNonExpired() |   | Indicates whether the user's account has expired.  |
|  default boolean |   | isAccountNonLocked() |   | Indicates whether the user is locked or unlocked.  |
|  default boolean |   | isCredentialsNonExpired() |   | Indicates whether the user's credentials (password) has expired.  |
|  default boolean |   | isEnabled() |   | Indicates whether the user is enabled or disabled.  |

# Authentication

# Processus d'authentification

Diagramme de sequence de l'authentification

(Spring Security classes are highlighted in green)

![img-38.jpeg](img-38.jpeg)

# Authentication

## Configuration de l’authentification

### Publier les objets de l’authentification

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
@Bean
public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
// définir la chaine de filtres de sécurité
return http.build();
}
@Bean
public UserDetailsService userDetailsService () {
// instancier un objet de l’interface UserDetailsService (InMemoryUserDetailsManager,, JdbcDaoImpl etc.)
}
@Bean
public PasswordEncoder passwordEncoder() {
// instancier un objet de l’interface PasswordEncoder (BCryptPasswordEncoder, StandardPasswordEncoder,...)
}
@Bean
AuthenticationManager authenticationManager (UserDetailsService userDetailsService,
PasswordEncoder passwordEncoder) {
// créer un AuthenticationProvider (DaoAuthenticationProvider, etc.)
return new ProviderManager(authenticationProvider);
}

88

# Authentification

## Configuration de la chaîne de filtres

$\diamond$ Classe de configuration de la sécurité : création de chaînes de filtres

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
@Bean
public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
// définir ici la chaîne de filtres de sécurité
return http.build();
}
}
```

- `HttpSecurity` : permet de créer une chaîne de filtre de sécurité SecurityFilterChain
https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html
- `http.build()` construit la chaîne de filtres

# Authentification

## Configuration de la chaîne de filtres

Sécuriser les communications avec https : requiresChannel ()

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
@Bean
public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
http
.requiresChannel(requiresChannel -&gt; requiresChannel
.anyRequest()
.requiresSecure()
)
.csrf();
return http.build();
}
}
```

89

90

# Authentification

## Configuration de la chaîne de filtres

- Configurer les autorisations d'accès aux URL : authorizeHttpRequests ()

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
@Bean
public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
http
.authorizeHttpRequests(authorize -&gt; authorize.requestMatchers("/public/**").permitAll()
.requestMatchers("/admin/**").hasRole("ADMIN")
.anyRequest().authenticated()
)
.formLogin(Customizer.withDefaults());
return http.build();
}
}

# Authentification

## Configuration de la chaîne de filtres (versions &gt;6)

- Configurer les formulaires de connexion : `formLogin` ()

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
@Bean
public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
http
.authorizeHttpRequests(authorize -&gt; authorize.anyRequest().authenticated()
)
.formLogin( formLogin -&gt; formLogin
.usernameParameter ("username")
.passwordParameter ("password")
.loginPage("/public/authentification/login")
.failureUrl("/public/authentification/loginFailed")
.loginProcessingUrl("/prive/menu.html"));
return http.build();
}
```

91

# Authentification

## Configuration de la chaîne de filtres (versions &gt;6)

- Configurer les formulaires de déconnexion : logout()

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
@Bean
public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
http
.authorizeHttpRequests(authorize -&gt; authorize.anyRequest().authenticated()
)
}
.logout(formLogout -&gt;
formLogout
.logoutUrl("/vue/logout")
.logoutSuccessUrl("/accueil"))
.invalidateHttpSession(true)
.deleteCookies()
}

return http.build();
}
```

92

# Authentification

## Configuration de la chaîne de filtres (versions &lt;6)

- Configurer les formulaires d’authentification : `formLogin()` et `logout()`

```text
@Configuration @EnableWebSecurity
public class WebSecurityConfig {
@Bean
public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
http.authorizeHttpRequests().requestMatchers("/user/**")...
.and()
.formLogin()
.loginPage("/connexion") // à retirer pour utiliser le form par défaut
.defaultSuccessUrl("/accueil-prive")
.failureUrl("/connexion")
.permitAll()
.logout()
.deleteCookies("JSESSIONID")
.logoutSuccessUrl("/")
.permitAll()
return http.build();
}
```

93

# Authentification

Configuration de compte utilisateur (unsername/password) en mémoire

- Publier un objet de l'interface UserDetailsService
- La classe InMemoryUserDetailsManager permet de déclarer des comptes utilisateurs non persistants en dur dans le code (pour les tests)

```text
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
@Bean
public UserDetailsService userDetailsService () {
UserDetails user = User
.withUsername("user")
.password("{noop}secret") // algo d'encodage du pwd (ici sans)
.roles("USER")
.build();
InMemoryUserDetailsManager usrManger = new InMemoryUserDetailsManager();
usrManger.createUser (user); // ajoute le compte à la liste des comptes en mémoire
return usrManger;
}
```

94

95

# Authentification

## Configuration de l'algorithme de chiffrement

- Publier un objet de l'interface PasswordEncoder

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
@Bean
public UserDetailsService userDetailsService() {
UserDetails user = User.username("user")
.password(passwordEncoder().encode("secret"))
.roles("USER")
.build();
return new InMemoryUserDetailsManager (user, admin);
}
@Bean
public PasswordEncoder passwordEncoder() {
return new BCryptPasswordEncoder();
}

# Authentification

## Bases de données des comptes utilisateurs

- Authentifier un utilisateur à partir d'une base de données
- Implémenter l'interface Spring UserDetailsService
- Authority = "ROLE_" + Nom du rôle (exemple : ROLE_USER, ROLE_ADMIN)

```java
Import org.springframework.security.core.userdetails.User;

@Service("authBaseDDUserDetailService")
public class AuthBaseDDUserDetailsService implements UserDetailsService {
@Autowired
private UserRepository userServiceRepo; // interface à coder
@Override
public UserDetails loadUserByUsername (String login) throws UsernameNotFoundException {
Compte compte=comptesRepo.findByLogin(login); // User est une entité JPA
if (user == null)
throw new UsernameNotFoundException("Login " + login + " not valide");
List<grantedauthority> roles = new ArrayList<grantedauthority>();
roles.add(new SimpleGrantedAuthority ("ROLE_" + compte.getRole ())); // ex : ROLE_USER
return new User(compte.getLogin(), user.getPassword(), roles);
}
```

96</grantedauthority></grantedauthority>

# Authentication

## Configuration de l’authentification

### Publier AuthenticationManager

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
@Bean
AuthenticationManager authenticationManager (UserDetailsService userDetailsService,
PasswordEncoder passwordEncoder) {
DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
authenticationProvider.setUserDetailsService(userDetailsService);
authenticationProvider.setPasswordEncoder(passwordEncoder);
return new ProviderManager(authenticationProvider);
}
@Bean
public UserDetailsService userDetailsService() {
// retourner un objet de l’interface UserDetailsService (par injection de dépendance)
}
@Bean
public PasswordEncoder passwordEncoder() {
// retourner un objet de l’interface PasswordEncoder (StandardPasswordEncoder,
BCryptPasswordEncoder, SCryptPasswordEncoder, etc.)
}
}
```

97

# Authentification

Bases de données des comptes utilisateurs (version &lt;6)

- Authentifier un utilisateur en utilisant une base de données
- En utilisant la factory AuthenticationManagerBuilder

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
@Autowired @Qualifier("authBaseDDUserDetailService")
private UserDetailsService authBaseDDUserDetailsService;

@Override
protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Excel
authManagerBuilder.userDetailsService(authBaseDDUserDetailsService)
.passwordEncoder(bCryptPasswordEncoder);
}

@Bean
public BCryptPasswordEncoder bCryptPasswordEncoder() {
return new BCryptPasswordEncoder();
}
```

98