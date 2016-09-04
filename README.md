# javadotenv

Just loads your environment variables from your .env file.

Javadotenv was inspired on [dotenv for Node.js](https://github.com/motdotla/dotenv) and [dotenv for Ruby](https://github.com/bkeepers/dotenv).

It works with Java 6+ versions and its only dependency is Sl4j.

Not separating config from code is a violation of [The Twelve-Factor App](http://12factor.net/config) methodology.

## Usage

First create your `.env` file and place it under your default package.
If you are using gradle or maven, it should be under `src/main/resources`.
This file follows the Java `properties` format:

```
KEY=VALUE
DATABASE_URL=VALUE2
```

As earlier as possible, load your .env file.
```java
new Dotenv.Builder().build().load();
```

Now, these properties should be accessible through `System.getenv` static method.

## Configuration

### resource

It is also possible to specify the resource to be loaded:
```java
new Dotenv.Builder().resource(".env.other").build().load();
```

The only constraint is that this file should be acessible via ClassPath.

### silent

When your .env or specified file is not available, it will log a warn message.
If you don't want to see this message, this is what you have to do:
```java
new Dotenv.Builder().silent(true).build().load();
```

## FAQ

### Should I commit my .env file?

My advice is __No__. It will prevent situations where you have Production pointing to Development keys.
And please, make sure you add the .env to your .gitignore file.

### When should I run it?

My advice is to run as soon as possible. If you have access to the main method, then it should be the first line of it.

### What I should do if I am using Tomcat?
If you are using Tomcat or a similar web container, you can add a `ServletContextListener` and register it in the web.xml, preferably in the beginning of it:

```xml
<listener>
  <listener-class>
    com.myapp.listener.DotenvContextListener
  </listener-class>
</listener>
```

```java
package com.myapp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyAppServletContextListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent arg0) {
      new Dotenv.Builder().build().load();
  }

  public void contextDestroyed(ServletContextEvent arg0) {  }

}
```