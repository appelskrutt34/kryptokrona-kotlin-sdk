---
title: Installation
description: How to install the libraries.
---

# {$frontmatter.title}

{$frontmatter.description}

To install Kryptokrona Kotlin SDK into your Maven/Gradle project we need to include the dependencies:

**Maven**
```
<dependency>
    <groupId>org.kryptokrona.sdk</groupId>
    <artifactId>kryptokrona-wallet</artifactId>
    <version>enter-version-here</version>
    <scope>implementation</scope>
</dependency>

<dependency>
    <groupId>org.kryptokrona.sdk</groupId>
    <artifactId>kryptokrona-node</artifactId>
    <version>enter-version-here</version>
    <scope>implementation</scope>
</dependency>

<dependency>
    <groupId>org.kryptokrona.sdk</groupId>
    <artifactId>kryptokrona-crypto</artifactId>
    <version>enter-version-here</version>
    <scope>implementation</scope>
</dependency>

<dependency>
    <groupId>org.kryptokrona.sdk</groupId>
    <artifactId>kryptokrona-util</artifactId>
    <version>enter-version-here</version>
    <scope>implementation</scope>
</dependency>
```

**Gradle**
```
dependencies {
    implementation 'org.kryptokrona.sdk:kryptokrona-wallet:<version>'
    implementation 'org.kryptokrona.sdk:kryptokrona-node:<version>'
    implementation 'org.kryptokrona.sdk:kryptokrona-crypto:<version>'
    implementation 'org.kryptokrona.sdk:kryptokrona-util:<version>'
}
```

**Gradle Kotlin DSL**
```
dependencies {
    implementation("org.kryptokrona.sdk:kryptokrona-wallet:<version>")
    implementation("org.kryptokrona.sdk:kryptokrona-node:<version>")
    implementation("org.kryptokrona.sdk:kryptokrona-crypto:<version>")
    implementation("org.kryptokrona.sdk:kryptokrona-util:<version>")
}
```
