package com.projekt.SystemPolicja.controllers;

import com.projekt.SystemPolicja.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.sql.*;

import java.util.ArrayList;


@Controller
public class AdminController {
    @RequestMapping("/showpolicemans")
    public String policemans(Model model)
    {
        Connection connection = getConnectionToDB();
        ArrayList<User> usersList = new ArrayList<>();
        usersList = GetAllPolicemans(connection);

        model.addAttribute("UsersList", usersList);
        return "showpolicemans";
    }

    public ArrayList<User> GetAllPolicemans(Connection conn)
    {
        ArrayList<User> usersList = new ArrayList<>();
        Statement stmt = null;
        String query = "SELECT * FROM POLICJA.dbo.SYSTEM_USERS;";
        try
        {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            User user;
            while(rs.next()){
                user=new User(rs.getInt("id"), rs.getString("login"), rs.getString("haslo"), rs.getString("imie"),
                              rs.getString("nazwisko"), rs.getString("pesel"), rs.getString("telefon"));
                usersList.add(user);
            }
            return usersList;

        }
        catch(SQLException e) {
            System.out.println(e);
            return usersList;
        }
    }

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