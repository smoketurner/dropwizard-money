Dropwizard Money
================
[![Build Status](https://travis-ci.org/smoketurner/dropwizard-money.svg?branch=master)](https://travis-ci.org/smoketurner/dropwizard-money)
[![Coverage Status](https://coveralls.io/repos/smoketurner/dropwizard-money/badge.svg?branch=master)](https://coveralls.io/r/smoketurner/dropwizard-money?branch=master)
[![Maven Central](https://img.shields.io/maven-central/v/com.smoketurner.dropwizard/dropwizard-money.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/com.smoketurner.dropwizard/dropwizard-money/)
[![GitHub license](https://img.shields.io/github/license/smoketurner/dropwizard-money.svg?style=flat-square)](https://github.com/smoketurner/dropwizard-money/tree/master)

A bundle for using [Money](http://javamoney.github.io) objects in Dropwizard applications. This bundle provides:

* Jackson module for serializing/deserializing Money objects
* JDBI argument mappers for persisting Money objects as a BigDecimal SQL type
* Jersey `MoneyParam` for serializing/deserialzing Money objects
* Jersey `MonetaryExceptionMapper` for handling `MonetaryException`

Usage
-----

Within your `Configuration` class, add the following:

```java
@Valid
@NotNull
private final MoneyFactory money = new MoneyFactory();

@JsonProperty
public MoneyFactory getMoneyFactory() {
    return money;
}
```

Then within your `Application` class:

```java
@Override
public void initialize(Bootstrap<MyConfiguration> bootstrap) {
    bootstrap.addBundle(new MoneyBundle<MyConfiguration>() {
        @Override
        public MoneyFactory getMoneyFactory(MyConfiguration configuration) {
            return configuration.getMoneyFactory();
        }
    });
}

@Override
public void run(MyConfiguration config, Environment environment) throws Exception {
    final String defaultCurrency = config.getMoneyFactory().getDefaultCurrencyCode();

    final DBIFactory factory = new DBIFactory();
    final DBI jdbi = factory.build(environment, config.getDataSourceFactory(), "postgresql");
    jdbi.registerArgumentFactory(new MoneyArgumentFactory());
    jdbi.registerColumnMapper(new MoneyMapper(defaultCurrency));
}
```

Maven Artifacts
---------------

This project is available on Maven Central. To add it to your project simply add the following dependencies to your `pom.xml`:

```xml
<dependency>
    <groupId>com.smoketurner.dropwizard</groupId>
    <artifactId>dropwizard-money</artifactId>
    <version>1.1.0-1</version>
</dependency>
```

Support
-------

Please file bug reports and feature requests in [GitHub issues](https://github.com/smoketurner/dropwizard-money/issues).


License
-------

Copyright (c) 2017 Smoke Turner, LLC

This library is licensed under the Apache License, Version 2.0.

See http://www.apache.org/licenses/LICENSE-2.0.html or the LICENSE file in this repository for the full license text.
