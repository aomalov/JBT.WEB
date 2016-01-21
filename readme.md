# Coupon Webbi

### Preamble

Web interface for the JBT project. Uses jar library interfacing the database storage layer and other functions. Brief review of [Phase 1 - Coupon DB]:

  - `Derby Db` is used to store data on: Companies, Customers, Coupons, Companies issuing coupons, Customers buying coupons
  - **Security** is enforced at the authentication layer: passwords are stored with hash, some salt is provided. Neither password could be found in open text, nor hash function could be easily refactored to get plain text passwords from stolen hashes (see [Security] for more details)
  - Data Transfer Objects `DTO` used for entities
  - Data Access Objects `DAO` used for conveying DTO to storage facility
  - `JDBC` used as middleware db driver, actual data storage can be replaced at the DAO layer by:
    - file system
    - NoSql db 
    - whatever ...
  - `Facade` classes encompass business logic at the moment of data storage and retrieval. They encapsulate DAO and DTO to allow for an easy API - create , read, update and delete (`CRUD`)
  - Single entry point to the **Coupon Dibi** is the CouponSystem singleton. Login gives successful user a facade and a connection pool of 20 parallel session to the database.

## Second Phase

> Create a nice and neat WEB presentation layer for 3 different facades , introduced earlier. Use `SPA` - single page application modern paradigm 

### Server-side 

- Use [WildFly](http://wildfly.org/downloads/) most recent `JBOSS` server as a web container
- Use [RestEASY](http://resteasy.jboss.org/) as the `REST`ful services router servlet
    - benefit from automatic DTO <-> JSON data mapping
    - benefit from automatic Collection -> JSON array data mapping
- Use `@WebFilter` annotation to filter out unauthorised requests to rest resources and unappropriate requests within the restricted site area
    - Save *facade* to user `Session` and check each request if it is in place
- Check *facade* type for the appropriate service - within the corresponding restful resource class
- Use `@Provider` annotation to catch all Exceptions  and turn them into a shaped response at the client side
- Use `web.xml` **ONLY** for */img* folder definition - define local path to store uploaded coupon images on the server
    - use `@MultipartForm` annotation to host upload image content into a POJO (see [this](http://examples.javacodegeeks.com/enterprise-java/rest/resteasy/resteasy-file-upload-example/) for details)

#### Tricks and references

- Send `datetime` data from web-browser and back: changed Coupon DTO generic setter parameter to String and used **SimpleDateFormat** to deserialize it back to Date
- Get context params configured in web.xml- see [this](http://tutorials.jenkov.com/java-servlets/web-xml.html#contextParams)

### Client side

- Use [Bootstrap](http://getbootstrap.com) as the out-of-the-box ``CSS+JavaScript`` web interface foundation
- Among the alternative frameworks worth mentioning:
    - [Semantic UI](http://semantic-ui.com/)
    - [AUI](https://docs.atlassian.com/aui/5.2/index.html)
- **VOID use of ~~JQuery~~** - use [AngularJS](http://angularjs.org)
    - Native Bootstrap javascript unit has been nicely adapted to pure Angular style - [here](http://angular-ui.github.io/bootstrap/#/getting_started)

#### Tricks and References

* Use Angular service to provide a singleton facility for client type and error message, injected to every controller (see **services.js**)
* Use *$emit/$broadcast* to escalate error events from nested controllers
* Use [UI-Router](https://github.com/angular-ui/ui-router) external module to create a state machine for a Single Page Application, extending generic angular *$router* service to handle ``hash urls`` (like *http://..../index.html#login*). See **app.js** 
* Enforce client-side input form validation using external angular directive (see [Show-errors](https://github.com/paulyoder/angular-bootstrap-show-errors) extending the common bootstrap validation)
* Integrate RESTful resource as a single ajax browser entity using *$resource* angular service (see **services.js**)
* Introduce modern user experience with js and ajax :
    * modal forms
    * navigation bar
    * pagination
    * dropdown menu
    * disappearing closable alert
    * typeahead with async server lookup
    * html rendering, adapted to a flexible browser width, allow for mobiles, for instance 
> all comes for free with Bootstrap

* Introduce modern user experiense with css
    * Icons and glyphs for free: [Font Awesome](https://fortawesome.github.io/Font-Awesome/icons/)
    * Nice fonts for free : [Google fonts](https://www.google.com/fonts#)


### Installation



```sh
give the name "coupon.web" to a new dynamic web project
download the sources from here
prepare JAR file from [Phase 1 - Coupon DB] sources
get derbyclient.jar
copy those 2 jar files to /web-inf/lib
set up "web.xml" file to point to local folder , where the "WebContent/img" folder is mapped to
```


[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [Phase 1 - Coupon DB]: <https://github.com/aomalov/JBT>
   [Security]: <https://www.owasp.org/index.php/Hashing_Java>



