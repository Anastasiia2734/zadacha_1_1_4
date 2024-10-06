package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;
public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://private.oxfraud.cc:3306/user_04102024";
    private static final String DB_LOGIN = "user_04102024";
    private static final String DB_PASSWORD = "04102024oO!";
    public static Statement statement;
    public static Connection connection;
/*public static Connection getConnection() {
    Connection connection1 = null;
    try {
        Class.forName(DB_DRIVER);
        connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASSWORD);
        System.out.println("Connection ok");
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
        System.out.println("Connection error");
    }
    return connection;
    }*/

public static SessionFactory getSessionFactory() {
SessionFactory sessionFactory = null;

try {
    //Class.forName(DB_DRIVER);
    Configuration configuration = new Configuration();
    Properties settings = new Properties();
    settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
    settings.put(Environment.URL, "jdbc:mysql://private.oxfraud.cc:3306/user_04102024");
    settings.put(Environment.USER, "user_04102024");
    settings.put(Environment.PASS, "04102024oO!");
    settings.put(Environment.DIALECT,  "org.hibernate.dialect.MySQL5Dialect");
    settings.put(Environment.SHOW_SQL, "true");
    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
    settings.put(Environment.HBM2DDL_AUTO, "create-drop");

    /*settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    settings.put("hibernate.hikari.dataSourceClassName", "com.mysql.cj.jdbc.Driver");
    //settings.put("hibernate.connection.driver_class", DB_DRIVER);
    settings.put("hibernate.hikari.dataSource.url", DB_URL);
    settings.put("hibernate.hikari.dataSource.user", DB_LOGIN);
    settings.put("hibernate.hikari.dataSource.password", DB_PASSWORD);
    settings.put("hibernate.hikari.maximumPoolSize", "10");
    settings.put("hibernate.hbm2ddl.auto", "update");
    settings.put("show_sql", "true");*/
    configuration.setProperties(settings);
    configuration.addAnnotatedClass(User.class);
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties()).build();
    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    System.out.println("Connection ok.");
} catch (Exception e) {
    e.printStackTrace();
}
return sessionFactory;
}
}