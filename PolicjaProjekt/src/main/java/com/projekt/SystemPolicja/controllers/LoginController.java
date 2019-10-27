package com.projekt.SystemPolicja.controllers;

import com.projekt.SystemPolicja.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController
{
    // Aby dostac formularz logowania ze strony
    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String getLoginForm()
    {
        // zwroc nazwe strony html
        return "login";
    }

    // Sprawdzenie zgodnosci podanych danych
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(@ModelAttribute(name="loginForm") LoginForm loginForm, Model model){
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        if("admin".equals(username) && "admin".equals(password)){
            // jesli login i haslo sa poprawne zwrocimy strone po zalogowaniu
            return "home";
        }

        // jesli login i haslo sa niepoprawne zwrocimy strone logowania jeszcze raz
        model.addAttribute("invalidCredentials",true);
        return "login";

    }
}
