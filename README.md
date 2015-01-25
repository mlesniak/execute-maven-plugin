# Introduction

This is a *proof of concept* plugin to generate an executable shell script which runs the generated jar. To be
actually useful, the corresponding project should use maven-assembly-plugin to generate a jar with all necessary
dependencies.

## Usage example

Add the following plugin configuration to your pom.xml

    <build>
        <plugins>
            ...
            <plugin>
                <groupId>com.mlesniak.maven</groupId>
                <artifactId>execute-maven-plugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <configuration>
                    <path>/usr/local/bin</path>
                    <jarName>sonar-report.jar</jarName>
                </configuration>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            ...
        </plugins>
    </build>

By setting the configuration properties path and jarName, an exectuable shell script with the following contents
will be generated at ```/usr/local/bin/sonar-report`` (e.g. without the .jar-suffix):

    #!/bin/sh
    java -jar /Users/mlesniak/Documents/sonar-report/target/sonar-report.jar $*

Note that the actual path will differ due to the absolute path referencing.

## Configuration

### jarName

The property ```jarName``` defines the name of the jar which will be executed. By correctly configuring the
maven-assembly-plugin, a nice jar-name can be generated (see [pom.xml](https://github.com/mlesniak/sonar-report/blob/master/pom.xml))

### path

The property ```path``` defines the path in which the shell script will be written.
