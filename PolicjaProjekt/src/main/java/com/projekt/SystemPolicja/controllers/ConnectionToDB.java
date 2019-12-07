package com.projekt.SystemPolicja.controllers;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionToDB {

    public static Connection getConnectionToDB()
    {
        try
        {
            Connection conn = DriverManager.getConnection(
                    "jdbc:sqlserver://DESKTOP-P8ERKEM\\KAROLSQL",
                    "spring",
                    "123");

            return conn;

        }
        catch(Exception e)
        {
            return null;
        }
    }
}
