# JavaEE HowTos

## Installation of the JavaEE 2019 demo application

- before deployment, please make sure that the database created in the initial session is up and running
- try to ping the JDBC connection pool to test the setup
- make sure that you have configured a JDBC resource named `jdbc/javaee-2019` that points to the above mentioned connection pool
- copy the `uniko-kefile` from the folder `JavaEE2019-ejb/src/java/org/riediger/ldap/impl/testdata` into your payara server's `domain1/config` folder
- this file contains a recent list of university e-mail addresses, each account has password `x`.
- in the administration console, create a new Security Realm `uniko-file-realm` (see screenshot `uniko-file-realm.png`)
- build and deploy the application via NetBeans
- to create test data, please log in as `wimmer@uni-koblenz.de` (this account has admin privileges) and use the administration menu
- test data is only created if the database is still empty!

##  Set a user specified root URL for the application

- change/set the `<context-root>` in application.xml (Application Module) and in glassfish-web.xml (Web Module)

##  Map any .xhtml file to be handled by the JSF Servlet

- set URL pattern in servlet definition of web.xml (Web Module)

  `<url-pattern>*.xhtml</url-pattern>`

##  Encode form data in UTF-8 (important to transfer special chars as umlauts, €, etc...)

- add the following to glassfish-web.xml (Web Module)

    `<locale-charset-info>
        <parameter-encoding default-charset="UTF-8"/>
    </locale-charset-info>`

##  Add BootsFaces library

- create folder `WEB-INF/lib`
- place the BootsFaces JAR file into that folder
- add the JAR to the libraries of the Web Module (use properties window...)
- add a namespace declaration `b` to the header your JSF .xhtml files

    `xmlns:b="http://bootsfaces.net/ui`

- use `b:` as namespace prefix for BootsFaces elements, e.g. `<b:row>`

##  Force ecrypted transfer (HTTPS/SSL)

- add a security constraint to web.xml (Web Module)

    `<security-constraint>
        <display-name>Force HTTPS</display-name>
        <web-resource-collection>
            <web-resource-name>all resources</web-resource-name>
            <description/>
            <url-pattern>/*</url-pattern>
        </web-resource-collection>
        <user-data-constraint>
            <description>require encrypted transfer</description>
            <transport-guarantee>CONFIDENTIAL</transport-guarantee>
        </user-data-constraint>
    </security-constraint>`
