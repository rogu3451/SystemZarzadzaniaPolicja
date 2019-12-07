package com.projekt.SystemPolicja.controllers;

import com.projekt.SystemPolicja.Mandat;
import com.projekt.SystemPolicja.Sprawa;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.*;

import java.util.ArrayList;


@Controller
public class PolicemanController {


    @RequestMapping("/mandaty")
    public String mandaty(Model model)
    {
        Connection connection = getConnectionToDB();
        ArrayList<Mandat> mandateList = new ArrayList<>();
        mandateList = GetAllMandates(connection);

        model.addAttribute("MandateList", mandateList);
        return "mandaty";
    }



    @RequestMapping("/sprawy")
    public String sprawy(Model model)
    {
        Connection connection = getConnectionToDB();
        ArrayList<Sprawa> caseList = new ArrayList<>();
        caseList = GetAllCases(connection);

        model.addAttribute("CaseList",caseList);
        return "sprawy";
    }


    @RequestMapping(value="/dodajSprawe", method= RequestMethod.GET)
    public String getCaseForm()
    {
        // zwroc nazwe strony html
        return "dodajSprawe";
    }

    @RequestMapping(value="/dodajSprawe", method=RequestMethod.POST)
    public String dodajSprawe(@ModelAttribute(name="addCase") Sprawa sprawa, Model model)
    {
        Connection connection = getConnectionToDB();
        int id = sprawa.getId();
        String data = sprawa.getDate();
        String status = sprawa.getStatus();
        String opis = sprawa.getOpis();
        String imie = sprawa.getImie();
        String nazwisko = sprawa.getNazwisko();
        String pesel = sprawa.getPesel();
        addCase(connection, data, status, opis, imie, nazwisko, pesel);
        model.addAttribute("dodanoSprawe",true);
        return "dodajSprawe";
    }

    @RequestMapping(value="/dodajMandat", method= RequestMethod.GET)
    public String getMandateForm()
    {
        // zwroc nazwe strony html
        return "dodajMandat";
    }

    @RequestMapping(value="/dodajMandat", method=RequestMethod.POST)
    public String dodajMandat(@ModelAttribute(name="addMandate") Mandat mandat, Model model)
    {
        Connection connection = getConnectionToDB();
        int id = mandat.getId();
        String data =  mandat.getDate();
        String imie =  mandat.getImie();
        String nazwisko =  mandat.getNazwisko();
        String opis =  mandat.getOpis();
        String kwota =  mandat.getKwota();
        String pesel =  mandat.getPesel();
        String adres =  mandat.getAdres();

        addMandate(connection, data, imie, nazwisko, opis, kwota, pesel, adres);
        model.addAttribute("dodanoMandat",true);
        return "dodajMandat";
    }

    public void addMandate(Connection conn, String data, String imie, String nazwisko, String opis, String kwota, String pesel, String adres)
    {
        String query = "INSERT INTO POLICJA.dbo.MANDATE(data, imie, nazwisko, opis, kwota, pesel, adres) VALUES(?,?,?,?,?,?,?);";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, data);
            pst.setString(2, imie);
            pst.setString(3, nazwisko);
            pst.setString(4, opis);
            pst.setString(5, kwota);
            pst.setString(6, pesel);
            pst.setString(7, adres);
            pst.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e);
        }
    }




    public void addCase(Connection conn, String data, String status, String opis, String imie, String nazwisko, String pesel)
    {
        String query = "INSERT INTO POLICJA.dbo.SPRAWA(data, status, opis, imie, nazwisko, pesel) VALUES(?,?,?,?,?,?);";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, data);
            pst.setString(2, status);
            pst.setString(3, opis);
            pst.setString(4, imie);
            pst.setString(5, nazwisko);
            pst.setString(6, pesel);
            pst.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e);
        }
    }


    public ArrayList<Mandat> GetAllMandates(Connection conn)
    {
        ArrayList<Mandat> mandateList = new ArrayList<>();
        Statement stmt = null;
        String query = "SELECT * FROM POLICJA.dbo.MANDATE;";
        try
        {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Mandat mandat;
            while(rs.next()){
                mandat=new Mandat(rs.getInt("id"), rs.getString("data"), rs.getString("imie"),
                        rs.getString("nazwisko"), rs.getString("opis"), rs.getString("kwota"), rs.getString("pesel"), rs.getString("adres"));
                mandateList.add(mandat);
            }
            return mandateList;

        }
        catch(SQLException e) {
            System.out.println(e);
            return mandateList;
        }
    }

    public ArrayList<Sprawa> GetAllCases(Connection conn)
    {
        ArrayList<Sprawa> caseList = new ArrayList<>();
        Statement stmt = null;
        String query = "SELECT * FROM POLICJA.dbo.SPRAWA;";
        try
        {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Sprawa sprawa;
            while(rs.next()){
                sprawa=new Sprawa(rs.getInt("id"), rs.getString("data"), rs.getString("status"), rs.getString("opis"),
                        rs.getString("imie"),  rs.getString("nazwisko"), rs.getString("pesel"));
                caseList.add(sprawa);
            }
            return caseList;

        }
        catch(SQLException e) {
            System.out.println(e);
            return caseList;
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