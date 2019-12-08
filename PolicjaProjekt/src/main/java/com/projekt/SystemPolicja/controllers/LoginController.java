package com.projekt.SystemPolicja.controllers;

import com.projekt.SystemPolicja.LoginForm;
import com.projekt.SystemPolicja.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import static com.projekt.SystemPolicja.controllers.PolicemanController.getConnectionToDB;

@Controller
public class LoginController
{
    // Aby dostac formularz logowania ze strony
    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String getLoginForm(Model model)
    {
        model.addAttribute("wylogowano",true);
        // zwroc nazwe strony html
        return "index";
    }

    // Sprawdzenie zgodnosci podanych danych
    @RequestMapping(value="/login", method=RequestMethod.POST)
public String login(@ModelAttribute(name="loginForm") LoginForm loginForm, Model model){
    String username = loginForm.getUsername();
    String password = loginForm.getPassword();

    if("admin".equals(username) && "admin".equals(password)){
        // jesli login i haslo sa poprawne zwrocimy strone po zalogowaniu
        return "administrator";
    }
    Connection connection = getConnectionToDB();
    ArrayList<User> usersList = GetAllPolicemans(connection);
    for(User user : usersList) {
        if (user.getLogin().equals(username) && user.getHaslo().equals(password)) {
            // jesli login i haslo sa poprawne zwrocimy strone po zalogowaniu
            return "policjant";
        }
    }

    // jesli login i haslo sa niepoprawne zwrocimy strone logowania jeszcze raz
    model.addAttribute("invalidCredentials",true);
    return "index";

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
}
