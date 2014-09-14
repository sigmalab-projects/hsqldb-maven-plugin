package de.sigmalab.maven.plugins.hsqldb;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * @author  jbellmann
 */
@Mojo(name = "shutdown", defaultPhase = LifecyclePhase.NONE)
public class ShutdownHsqldbMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        HsqlDbContainer container = (HsqlDbContainer) getPluginContext().get(RunHsqldbMojo.HSQlDB_SERVER_INSTANCE);
        if (container != null) {
            getLog().info("Stopping HSQLDB-SERVER ...");
            container.stop();
            getLog().info("HSQLDB-SERVER stopped.");
        }

    }

}
