package com.projekt.SystemPolicja.controllers;

import com.projekt.SystemPolicja.Mandat;
import com.projekt.SystemPolicja.Sprawa;
import com.projekt.SystemPolicja.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.*;

import java.util.ArrayList;

import static ch.qos.logback.core.joran.action.ActionConst.NULL;


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


        // Walidacja
        if((data.isEmpty()) || (imie.isEmpty()) || (nazwisko.isEmpty()) || (opis.isEmpty()) || (status.isEmpty()) || (pesel.isEmpty()))
        {
            model.addAttribute("puste_pole",true);
            return "dodajSprawe";
        }


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

        // Walidacja
        if((data.isEmpty()) || (imie.isEmpty()) || (nazwisko.isEmpty()) || (opis.isEmpty()) || (kwota.isEmpty()) || (pesel.isEmpty()) || (adres.isEmpty()) )
        {
            model.addAttribute("puste_pole",true);
            return "dodajMandat";
        }

        addMandate(connection, data, imie, nazwisko, opis, kwota, pesel, adres);
        model.addAttribute("dodanoMandat",true);
        return "dodajMandat";
    }

    @RequestMapping(value="/wyszukajMandat", method= RequestMethod.GET)
    public String getSearchMandateForm()
    {
        // zwroc nazwe strony html
        return "wyszukajMandat";
    }



    @RequestMapping(value="/wyszukajMandat", method=RequestMethod.POST)
    public String wyszukajMandat(@ModelAttribute(name="findMandate") Mandat mandat, Model model)
    {
        Connection connection = getConnectionToDB();
        int id = mandat.getId();
        ArrayList<Mandat> mandateList = new ArrayList<>();
        mandateList = findMandate(connection, id);
        if(mandateList.isEmpty())
        {
            model.addAttribute("nieznalezionoMandatu", true);
        }
        else{
            model.addAttribute("znalezionoMandat", true);
        }
        model.addAttribute("MandateList",mandateList);

        return "wyszukajMandat";
    }

    @RequestMapping(value="/wyszukajMandatData", method=RequestMethod.POST)
    public String wyszukajMandatData(@ModelAttribute(name="findMandateData") Mandat mandat, Model model)
    {
        Connection connection = getConnectionToDB();
        String data = mandat.getDate();
        ArrayList<Mandat> mandateList = new ArrayList<>();
        mandateList = findMandateData(connection, data);
        if(mandateList.isEmpty())
        {
            model.addAttribute("nieznalezionoMandatuData", true);
        }
        else{
            model.addAttribute("znalezionoMandatData", true);
        }
        model.addAttribute("MandateList",mandateList);

        return "wyszukajMandat";
    }

    @RequestMapping(value="/wyszukajSprawe", method= RequestMethod.GET)
    public String getSearchCaseForm()
    {
        // zwroc nazwe strony html
        return "wyszukajSprawe";
    }

    @RequestMapping(value="/wyszukajSprawe", method=RequestMethod.POST)
    public String wyszukajSprawe(@ModelAttribute(name="findCase") Sprawa sprawa, Model model)
    {

        Connection connection = getConnectionToDB();
        int id = sprawa.getId();
        ArrayList<Sprawa> caseList = new ArrayList<>();
        caseList = findCase(connection, id);
        if(caseList.isEmpty())
        {
            model.addAttribute("nieznalezionoSprawy", true);
        }
        else{
            model.addAttribute("znalezionoSprawe", true);
        }
        model.addAttribute("CaseList",caseList);

        return "wyszukajSprawe";
    }

    @RequestMapping(value="/edytujOpisSprawy", method=RequestMethod.POST)
    public String edytujOpisSprawy(@ModelAttribute(name="findCaseEdit") Sprawa sprawa, Model model)
    {

        Connection connection = getConnectionToDB();
        int id = sprawa.getId();
        String opis = sprawa.getOpis();

        ArrayList<Sprawa> caseList = new ArrayList<>();
        caseList = editDecriptionOfCase(connection, id, opis);
        model.addAttribute("edytowanoSprawe", true);

        model.addAttribute("CaseList",caseList);

        return "wyszukajSprawe";
    }

    public ArrayList<Sprawa> editDecriptionOfCase(Connection conn, int id, String opis)
    {
        Statement stmt = null;
        ArrayList<Sprawa> caseList = new ArrayList<>();
        String query = "UPDATE POLICJA.dbo.SPRAWA SET opis=\'"+opis+"\' WHERE id=\'"+id+"\'";
        try {
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






    public ArrayList<Sprawa> findCase(Connection conn, int id)
    {
        Statement stmt = null;
        ArrayList<Sprawa> caseList = new ArrayList<>();
        String query = "SELECT * FROM POLICJA.dbo.SPRAWA WHERE id=\'"+id+"\'";
        try {
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






    public ArrayList<Mandat> findMandate(Connection conn, int id)
    {
        Statement stmt = null;
        ArrayList<Mandat> mandateList = new ArrayList<>();
        String query = "SELECT * FROM POLICJA.dbo.MANDATE WHERE id=\'"+id+"\'";
        try {
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

    public ArrayList<Mandat> findMandateData(Connection conn, String data)
    {
        Statement stmt = null;
        ArrayList<Mandat> mandateList = new ArrayList<>();
        String query = "SELECT * FROM POLICJA.dbo.MANDATE WHERE data=\'"+data+"\'";
        try {
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