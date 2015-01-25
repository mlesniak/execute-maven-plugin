package com.mlesniak.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * A quick hack to generate executable scripts for jar executing.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Mojo(name = "generate")
public class MainMojo extends AbstractMojo {
    @Parameter(property = "generate.path", defaultValue = ".")
    private File path;

    @Parameter(property = "generate.jarName")
    private String jarName;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            getLog().info("Writing executable script to " + getFilename());
            FileUtils.fileWrite(getFilename(), getTemplate());
            // Or File.setExecutable(true)?
            Runtime.getRuntime().exec("chmod 777 " + getFilename());
        } catch (IOException e) {
            getLog().error("Unable to write execution file. error=" + e.getMessage());
            throw new MojoFailureException(e.getMessage());
        }
    }

    private String getTemplate() {
        return "#!/bin/sh\n" +
                "java -jar " + System.getProperty("user.dir") + "/target/" + jarName + " $*";
    }

    private String getFilename() {
        String withoutJar = jarName.replaceAll(".jar", "");
        return path + "/" + withoutJar;
    }
}
