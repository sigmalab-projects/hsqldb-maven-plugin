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

    @Parameter(property = "hsqldb.server.logServerProperties", defaultValue = "true")
    private boolean logServerProperties = true;

    @Parameter
    private Map<String, String> serverProperties = new HashMap<String, String>();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        HsqlDbContainer container = new HsqlDbContainer();
        if (logServerProperties) {
            logServerProperties(serverProperties);
        }

        container.setServerProperties(serverProperties);
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

    protected void logServerProperties(final Map<String, String> severProperties2) {
        getLog().info("----- SERVERPROPERTIES START -----");

        for (Map.Entry<String, String> entry : severProperties2.entrySet()) {
            getLog().info(entry.getKey() + "  :  " + entry.getValue());
        }

        getLog().info("----- SERVERPROPERTIES END -----");
    }

}
