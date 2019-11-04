package com.projekt.SystemPolicja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.*;


@Controller
public class GreetingsController {
    @RequestMapping("/helloworld")
    public String greeting(@RequestParam(value="name2", required=false, defaultValue="World") String nValue, Model model)
    {
        Connection testConnection = getConnectionToDB();
        System.out.println("Połączono z baza danych");
        model.addAttribute("name",nValue);
        return "helloworld";
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
