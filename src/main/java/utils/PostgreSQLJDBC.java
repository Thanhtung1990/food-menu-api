package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreSQLJDBC {

    Connection c = null;

    // open connection to PostgreSQL server
    public Connection postgreSQlConnect(){

        try {

            // get values from properties file.
            PropertiesCache propertiesCache = new PropertiesCache("postgreSQLConfig");

            // get data from properties file
            String host = propertiesCache.getProperty("postgre_host");
            String port = propertiesCache.getProperty("postgre_port");
            String db_name = propertiesCache.getProperty("postgre_db_name");
            String user = propertiesCache.getProperty("postgre_user");
            String pass = propertiesCache.getProperty("postgre_pass");

            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db_name,
                            user, pass);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");

        return c;
    }

    public void _closeConnection(){
        try {
            c.close();
            System.out.println("Closed database successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }

    }
}
