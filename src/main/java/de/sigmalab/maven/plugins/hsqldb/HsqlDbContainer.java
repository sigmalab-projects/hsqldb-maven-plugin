package de.sigmalab.maven.plugins.hsqldb;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.hsqldb.persist.HsqlProperties;

import org.hsqldb.server.Server;
import org.hsqldb.server.ServerAcl.AclFormatException;

public class HsqlDbContainer {

    private boolean running = false;

    private Server server;

    private int port = 9001;

    private Map<String, String> properties = new HashMap<String, String>();

    public void afterProperties() throws IOException, AclFormatException {
        HsqlProperties p = new HsqlProperties();
        if (properties.isEmpty()) {
            properties = getDefaultProperties();
        }

        for (Map.Entry<String, String> entry : properties.entrySet()) {
            p.setProperty(entry.getKey(), entry.getValue());
        }

        server = new Server();
        server.setProperties(p);
        server.setPort(port);
        server.setLogWriter(null); // can use custom writer
        server.setErrWriter(null); // can use custom writer

    }

    protected Map<String, String> getDefaultProperties() {
        Map<String, String> props = new HashMap<String, String>();
        props.put("server.database.0", getUserDirPath());
        props.put("server.dbname.0", "xdb");
        return props;
    }

    private String getUserDirPath() {
        return "file:" + System.getProperty("user.dir");
    }

    public void start() {
        if (isRunning()) {
            return;
        }

        try {

            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.running = true;
    }

    public void stop() {
        if (!isRunning()) {
            return;
        }

        try {

            server.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public void setServerProperties(final Map<String, String> properties) {
        this.properties = properties;
    }

}
