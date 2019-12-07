package com.projekt.SystemPolicja.controllers;

import com.projekt.SystemPolicja.Mandat;
import com.projekt.SystemPolicja.Sprawa;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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