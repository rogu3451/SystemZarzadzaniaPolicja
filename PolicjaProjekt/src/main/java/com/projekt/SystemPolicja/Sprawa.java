package com.projekt.SystemPolicja;

public class Sprawa {
    private int id;
    private String date, status, opis, imie, nazwisko, pesel;

    public Sprawa(int id, String date, String status, String opis, String imie, String nazwisko, String pesel) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.opis = opis;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getOpis(){
        return opis;
    }

    public String getImie(){
        return imie;
    }

    public String getNazwisko(){
        return nazwisko;
    }

    public String getPesel(){
        return pesel;
    }



}
