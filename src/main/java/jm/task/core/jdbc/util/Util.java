package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String log = "postgres";
    private static final String pass = "panda1213";
    private static final String drive = "org.postgresql.Driver";
    private static final String dialect = "org.hibernate.dialect.PostgreSQLDialect";

    public static Connection getConnectionDataBase() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, log, pass);
            if (connection != null) {
                System.out.println("База данных подключена!");
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

        return connection;
    }

    public static SessionFactory getSessionFactory() {

        Properties properties = new Properties();
        properties.setProperty("hibernate.driver_class", drive);
        properties.setProperty("hibernate.connection.url", url);
        properties.setProperty("hibernate.connection.username", log);
        properties.setProperty("hibernate.connection.password", pass);
        properties.setProperty("hibernate.dialect", dialect);

        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .setProperties(properties);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}