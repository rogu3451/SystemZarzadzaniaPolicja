package com.projekt.SystemPolicja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.sql.*;


@Controller
public class GreetingsController {
    @RequestMapping("/helloworld")
    public String greeting(Model model)
    {
        Connection testConnection = getConnectionToDB();
        String greeting = "";
        if(testConnection == null)
        {
            greeting = "No Connection";
        }
        else
        {
            greeting = GetGreeting(testConnection);
        }
        model.addAttribute("Greeting", greeting);
        return "helloworld";
    }

    public String GetGreeting(Connection conn)
    {
        Statement stmt = null;
        String query = "SELECT TOP 1 imie FROM PROJEKT_SIEC_APTEK.dbo.[APTEKA.LEKARZ]";
        try
        {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            return rs.getString("imie");
        }
        catch(SQLException e) {
            System.out.println(e);
            return "";
        }
    }
    public Connection getConnectionToDB()
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