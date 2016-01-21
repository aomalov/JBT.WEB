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
- Use `@WebFilter` annotation to filter out unauthorised requests to rest resources and unappropriate requests within the restricted site area
    - Save *facade* to user `Session` and check each request if it is in place
- Check *facade* type for the appropriate service - within the corresponding restful resource class
- Use `@Provider` annotation to catch all Exceptions  and turn turn them into a shaped response at the client side


### Installation



```sh
$ git clone [git-repo-url] dillinger
$ cd dillinger
$ npm i -d
$ mkdir -p downloads/files/{md,html,pdf}
$ gulp build --prod
$ NODE_ENV=production node app
```





[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [dill]: <https://github.com/joemccann/dillinger>
   [Phase 1 - Coupon DB]: <https://github.com/aomalov/JBT>
   [Security]: <https://www.owasp.org/index.php/Hashing_Java>



