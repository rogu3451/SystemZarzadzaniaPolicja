package com.projekt.SystemPolicja.controllers;

import com.projekt.SystemPolicja.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value="/dodajPolicjanta", method= RequestMethod.GET)
    public String getPolicemanForm()
    {
        // zwroc nazwe strony html
        return "dodajPolicjanta";
    }

    @RequestMapping(value="/dodajPolicjanta", method=RequestMethod.POST)
    public String dodajPolicjanta(@ModelAttribute(name="addPoliceman") User policjant, Model model)
    {
        Connection connection = getConnectionToDB();
        int id = policjant.getId();
        String login =  policjant.getLogin();
        String haslo =  policjant.getHaslo();
        String imie =  policjant.getImie();
        String nazwisko =  policjant.getNazwisko();
        String pesel =  policjant.getPesel();
        String telefon =  policjant.getTelefon();

        addPoliceman(connection, login, haslo, imie, nazwisko, pesel, telefon);
        model.addAttribute("dodanoPolicjanta",true);
        return "dodajPolicjanta";
    }

    @RequestMapping(value="/znajdzPolicjanta", method= RequestMethod.GET)
    public String getSearchPolicemanForm()
    {
        // zwroc nazwe strony html
        return "znajdzPolicjanta";
    }

    @RequestMapping(value="/znajdzPolicjanta", method=RequestMethod.POST)
    public String znajdzPolicjanta(@ModelAttribute(name="findPoliceman") User policjant, Model model)
    {
        Connection connection = getConnectionToDB();
        int id = policjant.getId();
        ArrayList<User> usersList = new ArrayList<>();
        usersList = findPoliceman(connection, id);
        if(usersList.isEmpty())
        {
            model.addAttribute("nieznalezionoPolicjanta", true);
        }
        else{
            model.addAttribute("znalezionoPolicjanta", true);
        }
        model.addAttribute("UsersList", usersList);

        return "znajdzPolicjanta";
    }


    public ArrayList<User> findPoliceman(Connection conn, int id)
    {
        Statement stmt = null;
        ArrayList<User> usersList = new ArrayList<>();
        String query = "SELECT * FROM POLICJA.dbo.SYSTEM_USERS WHERE id=\'"+id+"\'";
        try {
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




    public void addPoliceman(Connection conn, String login, String haslo, String imie, String nazwisko, String pesel, String telefon)
    {
        String query = "INSERT INTO POLICJA.dbo.SYSTEM_USERS(login, haslo, imie, nazwisko, pesel, telefon) VALUES(?,?,?,?,?,?);";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, login);
            pst.setString(2, haslo);
            pst.setString(3, imie);
            pst.setString(4, nazwisko);
            pst.setString(5, pesel);
            pst.setString(6, telefon);
            pst.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e);
        }
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