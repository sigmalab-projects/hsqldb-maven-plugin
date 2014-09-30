package de.sigmalab.maven.plugins.hsqldb;

import java.util.concurrent.TimeUnit;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author  jbellmann
 */
@Mojo(name = "shutdown", defaultPhase = LifecyclePhase.NONE)
public class ShutdownHsqldbMojo extends AbstractMojo {

    @Parameter(property = "hsqldb.server.sleepOnStop", defaultValue = "-1")
    private int sleepOnStop = -1;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        HsqlDbContainer container = (HsqlDbContainer) getPluginContext().get(RunHsqldbMojo.HSQlDB_SERVER_INSTANCE);
        if (container != null) {
            if (sleepOnStop > 0) {
                getLog().info("'sleepOnStop' configured for " + sleepOnStop + " mins. Please wait ...");
                try {
                    TimeUnit.MINUTES.sleep(sleepOnStop);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            getLog().info("Stopping HSQLDB-SERVER ...");
            container.stop();
            getLog().info("HSQLDB-SERVER stopped.");
        }

    }

}
