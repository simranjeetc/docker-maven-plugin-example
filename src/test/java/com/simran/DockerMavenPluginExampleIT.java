package com.simran;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DockerMavenPluginExampleIT
{
    @Test
    public void testConnection()
    {

        try (final Connection conn = getConnection()) {

            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (final SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            Assertions.fail();
        } catch (final Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    public void testUsersCount()
    {
        final String QUERY = "SELECT 1";
        try (final Connection conn = getConnection();
             final Statement stmt = conn.createStatement();
             final ResultSet rs = stmt.executeQuery(QUERY)) {

            while (rs.next()) {
                Assertions.assertTrue(rs.getInt(1) == 1);
            }

        } catch (final SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            Assertions.fail();
        } catch (final Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    private Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(getUrl(), getProperties());
    }

    private Properties getProperties()
    {
        final String pgUser = System.getProperty("postgres.user", "postgres");
        final String pgPassword = System.getProperty("postgres.pass", "postgres");
        final Properties props = new Properties();
        props.setProperty("user", pgUser);
        props.setProperty("password", pgPassword);
        return props;
    }

    private String getUrl()
    {
        final String pgHost = System.getProperty("postgres.host", "localhost");
        final String pgPort = System.getProperty("postgres.port", "5432");
        final String pgDatabase = System.getProperty("postgres.db", "postgres");
        return "jdbc:postgresql://" + pgHost + ":" + pgPort + "/" + pgDatabase + "";
    }
}
