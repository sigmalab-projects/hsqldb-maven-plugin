package de.sigmalab.maven.plugins.hsqldb;

import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author  jbellmann
 */
@Mojo(name = "run", defaultPhase = LifecyclePhase.NONE)
public class RunHsqldbMojo extends AbstractMojo {

    static final String HSQlDB_SERVER_INSTANCE = "HSQLDB_SERVER_INSTANCE";

    @Parameter(property = "hsqldb.server.port", defaultValue = "9001")
    public Integer port;

    @Parameter
    private Map<String, String> severProperties = new HashMap<String, String>();

    public void execute() throws MojoExecutionException, MojoFailureException {
        HsqlDbContainer container = new HsqlDbContainer();
        container.setServerProperties(severProperties);
        container.setPort(port);
        try {
            container.afterProperties();
            getLog().info("Starting HSQLDB-SERVER ...");
            container.start();
            getPluginContext().put(HSQlDB_SERVER_INSTANCE, container);
            getLog().info("HSQLDB-SERVER started on port : " + port);
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

    }

}
