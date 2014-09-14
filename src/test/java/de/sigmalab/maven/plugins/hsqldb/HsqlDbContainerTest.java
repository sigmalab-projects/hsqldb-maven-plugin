package de.sigmalab.maven.plugins.hsqldb;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;

import org.hsqldb.server.ServerAcl.AclFormatException;

import org.junit.Test;

public class HsqlDbContainerTest {

    @Test
    public void startContainer() throws IOException, AclFormatException, InterruptedException {
        HsqlDbContainer container = new HsqlDbContainer();
        container.afterProperties();
        container.start();
        Assertions.assertThat(container.isRunning()).isTrue();

        TimeUnit.SECONDS.sleep(8);

        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001/xdb");

            DatabaseMetaData metaData = c.getMetaData();
            System.out.println("DB-Major-Version : " + metaData.getDatabaseMajorVersion());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        container.stop();
        Assertions.assertThat(container.isRunning()).isFalse();
    }
}
