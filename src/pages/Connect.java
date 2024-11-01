package pages;

/*
JDBC Connectivity
step 1 : register the driver
step 2 : create connection
step 3 : create statement
step 4 : execute query
step 5 : close connection
*/

import java.sql.*;

public class Connect {

    Connection c;
    Statement s;
    public Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3303/atm", <username>, <password>);
            s = c.createStatement();
            System.out.println("Connection established successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to the database.", e);
        }
    }
}
