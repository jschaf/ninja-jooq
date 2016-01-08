jOOQ support for the Java Ninja Web Framework
=============================================

jOOQ generates Java code from your database and lets you build type safe SQL queries through its fluent API.

[![Build Status](https://travis-ci.org/jschaf/ninja-jooq.svg?branch=master)](https://travis-ci.org/jschaf/ninja-jooq)

More
----

 * Source: http://github.com/ninjaframework/ninja

Getting started
---------------
Configuring the module for your application is quite easy. There
is a demo application that shows you how to do it.
Check out subproject ninja-jooq-demo/pom.xml for 
further information.

More about jOOQ: http://www.jooq.org

Setup
-----

1) ninja-jooq will read the database info from your application.conf.  Optionally, you can configure the following 
   [settings](http://www.jooq.org/javadoc/3.2.3/org/jooq/conf/Settings.html).

   * `jooq.renderSchema` - `(true|false)`, default: true
   * `jooq.renderNameStyle` - `("QUOTED"|"AS_IS"|"LOWER"|"UPPER")`, default "QUOTED"
   * `jooq.renderKeywordStyle` - `("LOWER"|"UPPER")`, default "LOWER"
   * `jooq.renderFormatted` - `(true|false)`, default false
   * `jooq.statementType` - `("STATIC_STATEMENT"|"PREPARED_STATEMENT")`, default "PREPARED_STATEMENT"
   * `jooq.executeLogging` - `(true|false)`, default true
   * `jooq.executeWithOptimisticLocking` - `(true|false)`, default true
   * `jooq.attachRecords` - `(true|false)`, default true
   * `jooq.sqlDialect`  - one of the values of [SQLDialect](http://www.jooq.org/javadoc/3.2.0/org/jooq/SQLDialect.html), 
     default "DEFAULT"
   
2) Add the ninja-jooq dependency to your pom.xml:

    <dependency>
        <groupId>org.ninjaframework</groupId>
        <artifactId>ninja-jooq-module</artifactId>
        <version>0.0.1</version>
    </dependency>
    
3) Add the jOOQ generator plugin to your pom.xml:

```xml
<plugin>
    <!-- Specify the maven code generator plugin -->
    <groupId>org.jooq</groupId>
    <artifactId>jooq-codegen-maven</artifactId>
    <version>3.7.2</version>

    <!-- The plugin should hook into the generate goal -->
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>

    <!-- Manage the plugin's dependency. In this example, we'll use a H2 database -->
    <dependencies>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.190</version>
        </dependency>

        <dependency>
            <groupId>org.ninjaframework</groupId>
            <artifactId>ninja-jooq-module</artifactId>
            <version>0.0.1</version>
        </dependency>
    </dependencies>

    <!-- Specify the plugin configuration.
         The configuration format is the same as for the standalone code generator -->
    <configuration>

        <!-- JDBC connection parameters -->
        <jdbc>
            <url>${db.url}</url>
            <user>${db.username}</user>
        </jdbc>

        <!-- Generator parameters -->
        <generator>
            <database>
                <includes>.*</includes>
                <inputSchema>FLYWAY_TEST</inputSchema>
            </database>
            <target>
                <packageName>models</packageName>
                <directory>target/generated-sources/jooq</directory>
            </target>
        </generator>
    </configuration>
</plugin>
```

4) Install the module in your conf.Module:

    protected void configure() {
        // This installs the NinjaModule and handles the lifecycle
        install(new NinjaJooqModule());
    }
    
    
And that's it already :)

