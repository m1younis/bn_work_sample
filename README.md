# Bright Network Technology Internship 2021 – Google Coding Challenge

This repo contains a solution to the programming challenge set by Google during the 2021 Bright
Network Technology Internship work sample. Further details relating to its nature are covered
extensively in
[this](https://github.com/m1younis/bn_work_sample/files/10329373/instructions.pdf) document.

The certificate of completion attained can be found
[here](https://www.brightnetwork.co.uk/certificates/internship-experience-uk-techn_7g53xcudnj8hwh).

## Installation & Configuration

Java 11, JUnit 5.4 and Maven 3.8 are required. If you need to install Java for Windows, follow
[these](https://java.tutorials24x7.com/blog/how-to-install-java-11-on-windows)
instructions. Click
[here](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-macos.html)
for Mac installation instructions and
[here](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-linux-plaforms.html) for
Linux.

To install Maven on any of the platforms mentioned above, click
[this](https://www.baeldung.com/install-maven-on-windows-linux-mac) link.

## Execution

Any IDE or text editor can be used. However, different editors have different ways of dealing with
Java code, so it is recommended to execute the program via your terminal or IntelliJ IDEA.

**NOTE:** Editing the contents of `/resources/videos.txt` will cause the unit tests to break.

### Via the terminal

Firstly, navigate into `java/` then build via Maven by running:

```
$  mvn compile
```

Then execute:

```
$  mvn exec:java
```

To run all of the unit tests associated with each task, compile the program using:

```
$  mvn compile
```

Then:

```
$  mvn test
```

To test a specific part of the challenge after compiling, run:

```
$  mvn test -Dtest=Part1Test
$  mvn test -Dtest=Part2Test
$  mvn test -Dtest=Part3Test
$  mvn test -Dtest=Part4Test
```

### Via IntelliJ IDEA

Follow the official instructions from JetBrains (found
[here](https://www.jetbrains.com/help/idea/maven-support.html#maven_import_project_start)) to open
this repository as a Maven project in IntelliJ, which should automatically recognise the `java/`
directory as such due to the presence of a `pom.xml` file.

Ensure that the project SDK is set to Java 11 by following
[these](https://www.jetbrains.com/help/idea/sdk.html#change-project-sdk) instructions. Any vendor
can be used for JDK 11 if it isn't already installed.

To run the entire program, locate and open the `Run.java` file from the project menu, then click on
the green play-shaped symbol positioned in the sidebar.

To run a unit test associated with a part of the challenge, locate `/test/` within the project
menu, open the specific file, then click on the green double-arrow symbol besides the class name.

Right-clicking the root directory and selecting the **`"Run 'All Tests'"`** option from the
drop-down menu will do as stated.

Please note that your current version of IntelliJ will most likely not be the same as that shown in
the following screen recording summarising the steps above, yet they remain identical.

![](https://user-images.githubusercontent.com/72233083/210183125-6bbb3387-24c7-4a5c-9ed8-509acad90933.gif)
