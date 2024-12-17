# Essentiële Vaardigheden en Concepten voor 9.2.2 - Spring Boot

![hbo-ict logo](beeldmerk_hbo-ict.png)

auteur: **Fauzan Zidni Ramadhan Rahman**

## Aanbevolen Bronnen:

-   **Online Cursus**: "Master Java Web Services and REST API with Spring Boot" van Ranga Karanam op O'Reilly:  
    [O'Reilly Spring Boot Cursus](https://learning-oreilly-com.rps.hva.nl/course/master-java-web/9781789130133/)

-   **Belangrijke Hoofdstukken**:

    -   **2**: Introduction to RESTful Web Services
    -   **3**: Introduction to the Spring Framework
    -   **4**: Introduction to Spring Boot (st1-7)
    -   **5**: RESTful Web Services (st1-13)

-   **Baeldung Tutorials**: [Baeldung Spring Boot Tutorials](https://www.baeldung.com/spring-boot)

## Kernconcepten Spring Boot:

### **RESTful Web Services**:

-   Ontwikkelen van REST API’s met Spring Boot.
-   Gebruik van annotaties zoals `@RestController` en `@GetMapping`, `@PostMapping`, `@PutMapping` voor het definiëren van endpoints.

### **Spring Boot Setup**:

-   **Spring Boot Initializer**: Gebruik om projecten op te zetten.
-   **Library Manager**: Kies tussen **Maven** of **Gradle** (aanbevolen: Maven vanwege de eenvoud).
-   **Configuratie**: Gebruik van `application.properties` voor het configureren van applicatie-instellingen.

### **Spring Boot Annotaties**:

-   **@SpringBootApplication**: Hoofdannotatie voor een Spring Boot applicatie.
-   **@RestController**: Definieert een REST-controller.
-   **@GetMapping**, **@PostMapping**, **@PutMapping**: Voor het maken van HTTP endpoints.
-   **@PathVariable**: Voor het werken met variabelen in URL-paden.

### **Dependency Injection**:

-   Het gebruik van Spring's dependency injection voor het beheren van objecten en services binnen de applicatie.

### **ORM (Object-Relational Mapping)**:

-   Gebruik van **Spring Data JPA** en **Hibernate** voor het werken met databases.

### **Beveiliging**:

-   Basisbeveiliging zoals authenticatie en autorisatie voor REST API’s (kan als apart leerdoel worden behandeld).

## Aanbevolen Tijd:

-   Besteed minimaal een week aan het leren van de basisprincipes van **Spring Boot** voordat je het toepast in je project.

## 9.2.3 - Dependency Inversion en Dependency Injection

### **Dependency Inversion Principle (DIP)**

Het **Dependency Inversion Principle** is een ontwerppatroon dat stelt dat high-level modules niet afhankelijk moeten zijn van low-level modules, maar beide afhankelijk moeten zijn van abstraheringen. Verder moeten abstracties niet afhankelijk zijn van details, maar details van abstracties. Dit helpt om een flexibele en onderhoudbare codebasis te creëren.

### **Dependency Injection (DI)**

**Dependency Injection** is een techniek waarbij objecten of afhankelijkheden van een klasse extern worden verstrekt in plaats van dat de klasse deze zelf maakt. Dit vermindert de koppeling tussen klassen en verhoogt de herbruikbaarheid en testbaarheid.

### **Toepassing in Spring Boot**

In **Spring Boot** wordt **Dependency Injection** veel gebruikt. Het framework maakt het mogelijk om objecten automatisch te injecteren in de benodigde componenten via annotaties zoals `@Autowired` en `@Inject`. Dit is een concrete toepassing van het Dependency Inversion Principle.

-   **Aanbevolen Bron**: [Spring Boot cursus op O'Reilly](https://learning-oreilly-com.rps.hva.nl/course/master-java-web/9781789130133/)
    -   **Hoofdstuk 3**: Introduction to the Spring Framework behandelt Dependency Injection in detail.

### **Dependency Injection in Vue.js**

In **Vue.js** wordt **Dependency Injection** mogelijk gemaakt door de `provide` en `inject` API's. Deze functionaliteit maakt het mogelijk om gegevens en methoden van een bovenliggende component beschikbaar te stellen aan onderliggende componenten zonder dat ze expliciet als props hoeven te worden doorgegeven.

-   **Vue.js Documentatie**: [Provide/Inject in Vue.js](https://vuejs.org/guide/components/provide-inject.html)

### **Belangrijk voor je Project**

-   Wanneer je **Spring Boot** of **Vue.js** gebruikt, is het belangrijk om het concept van **Dependency Injection** goed te begrijpen. Het helpt bij het maken van flexibele, herbruikbare en makkelijk testbare code.

## 9.2.4 - Gelaagde, Logische Architectuur van een Enterprise Web Applicatie

### **Wat is Gelaagde Architectuur?**

Een **layered architecture** is een ontwerpprincipe waarbij een applicatie is verdeeld in logische lagen, waarbij elke laag een specifieke verantwoordelijkheid heeft. Dit zorgt voor betere organisatie, onderhoudbaarheid en testbaarheid van de applicatie. De gelaagde architectuur maakt het mogelijk om complexiteit te beheersen door de verschillende lagen van elkaar te scheiden.

### **Veelgebruikte Lagen in Web Applicaties:**

1. **Presentatielaag (Frontend)**:

-   Behandelt de gebruikersinterface (UI) en interactie met de gebruiker.
-   In de frontend wordt deze laag vaak gerealiseerd met frameworks zoals **Vue.js**.

2. **Applicatielaag (Business Logic Layer)**:

-   Behandelt de bedrijfslogica van de applicatie. Dit is waar de regels en logica voor de werking van de applicatie worden geïmplementeerd.
-   In een backend-applicatie wordt deze laag vaak geïmplementeerd in een **Spring Boot**-service of controller.

3. **Datalayer (Data Access Layer)**:

-   Verantwoordelijk voor interactie met de database of andere opslagmechanismen.
-   In **Spring Boot** wordt dit vaak gerealiseerd via **JPA** of **Hibernate**, die de database abstractie en gegevensbeheer verzorgen.

### **Voordelen van een Gelaagde Architectuur:**

-   **Scheidt verantwoordelijkheden**: Elke laag heeft een specifieke verantwoordelijkheid, wat de onderhoudbaarheid vergemakkelijkt.
-   **Herbruikbaarheid en testbaarheid**: Door lagen te scheiden, kunnen onderdelen van de applicatie gemakkelijker getest en hergebruikt worden.
-   **Schaling en uitbreidbaarheid**: Het is eenvoudiger om een systeem op te schalen door lagen onafhankelijk van elkaar te schalen of te optimaliseren.
-   **Beheer van complexiteit**: De gelaagde benadering helpt om complexiteit te beheersen door het op te splitsen in behapbare delen.

### **Toepassing in Vue.js en Spring Boot**:

-   **Frontend (Vue.js)**: De presentatie van de applicatie wordt gescheiden van de logica, bijvoorbeeld door componenten en Vuex voor state management te gebruiken.
-   **Backend (Spring Boot)**: De backend maakt gebruik van gelaagde architectuur door controllers, services en repositories te scheiden, waarbij elke laag een specifieke rol speelt, zoals het afhandelen van HTTP-aanvragen, bedrijfslogica uitvoeren en toegang tot de database beheren.

### **Aanbevolen Bronnen**:

-   Zoek naar informatie over **layered architecture** in webapplicaties om meer inzicht te krijgen in de implementatie en voordelen van dit principe.
-   Als je **Spring Boot** of **Vue.js** beheerst, ben je waarschijnlijk al vertrouwd met gelaagde architectuur, zelfs als je het niet expliciet hebt toegepast.

### **Belangrijk voor je Project**:

-   Zorg ervoor dat zowel de frontend als de backend goed gelaagd zijn, waarbij je de scheiding van verantwoordelijkheden respecteert. Dit draagt bij aan een gestructureerde en schaalbare applicatie.

## 9.2.5. Design and Develop the Persistence Layer in the Backend of a Web Application

**ORM (Object-Relational Mapping)** maakt het mogelijk om Java-objecten direct te koppelen aan tabellen in een relationele database, waardoor je geen SQL-code hoeft te schrijven voor database-interacties. Dit bespaart ontwikkeltijd en zorgt voor een schonere code.

### Concepten:

-   **Entity**: Java-klassen die de structuur van een tabel in de database representeren. Elke instantie van de klasse komt overeen met een rij in de tabel.
-   **JPA (Java Persistence API)**: Een specificatie voor ORM in Java die het mogelijk maakt om objecten op een gestandaardiseerde manier naar de database te schrijven en deze te lezen.
-   **Hibernate**: De implementatie van JPA, vaak gebruikt met Spring Boot voor ORM-doeleinden.
-   **JPQL (Java Persistence Query Language)**: Een object-georiënteerde querytaal waarmee queries op entities kunnen worden uitgevoerd, in plaats van op tabellen.
-   **Repository**: Een interface voor het beheren van databaseoperaties via JPA. Dit maakt het mogelijk om CRUD-operaties (Create, Read, Update, Delete) uit te voeren zonder zelf SQL te schrijven.

#### Voorbeeld van een eenvoudige Entity in Spring Boot:

```java
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // Getters en setters
}
```

## 9.2.6. Design and Implement the Security Controls of Authentication and Authorization in an Enterprise Web Application

### **Authenticatie en Autorisatie met JWT (JSON Web Tokens)**

In het eerste jaar heb je waarschijnlijk frontend-code geschreven die gerelateerd is aan inloggen. De volgende stap in het verbeteren van de beveiliging van je applicatie is om in de backend te verifiëren of de inkomende aanvraag afkomstig is van een gebruiker die is ingelogd. Een goede en efficiënte manier om dit te implementeren is door gebruik te maken van **JWT (JSON Web Tokens)** in combinatie met de Spring Boot filterchain.

#### **Wat is JWT?**

JWT is een compact, URL-veilige manier om informatie tussen twee partijen uit te wisselen. Het wordt vaak gebruikt voor authenticatie, waarbij een server een token genereert en deze naar de client stuurt. De client kan dit token vervolgens gebruiken voor toegang tot beveiligde endpoints zonder opnieuw in te loggen.

-   **Voordelen van JWT**:
    -   **Stateless**: De server hoeft geen sessie-informatie op te slaan, omdat alle informatie in het token zelf wordt opgeslagen.
    -   **Veilig**: JWT kan worden ondertekend met een geheim om de integriteit en authenticiteit te waarborgen.
    -   **Flexibiliteit**: JWT kan worden gebruikt voor verschillende beveiligingsdoelen, zoals authenticatie en autorisatie.

#### **JWT Implementatie in Spring Boot**:

1. **JWT Filter**: Maak een filter die de inkomende HTTP-aanvragen controleert op het JWT-token.

-   Dit kan worden geïmplementeerd door een custom filter in Spring Security.
-   Het filter valideert het token en stelt de gebruiker in staat om toegang te krijgen tot de beveiligde endpoints.

2. **JWT Token Aanmaken**: Na een succesvolle inlog genereert de server een JWT-token, dat naar de client wordt gestuurd en opgeslagen in bijvoorbeeld `localStorage` of `sessionStorage` van de browser.

-   Dit token wordt vervolgens toegevoegd aan de autorisatieheader van de client voor verdere aanvragen.

3. **JWT Validatie**: Bij elke vervolgrequest valideert het backend de geldigheid van het JWT-token. Als het token geldig is, wordt de aanvraag geautoriseerd en doorgestuurd naar het betreffende endpoint.

#### **Stappen om JWT in Spring Boot te integreren**:

1. **Voeg de benodigde dependencies toe**:
   Voeg de juiste Spring Security en JWT-dependencies toe aan je `pom.xml` (bij gebruik van Maven).

    ```xml
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>0.11.5</version>
    </dependency>
    ```

```

```
