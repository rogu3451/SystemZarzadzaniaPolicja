package com.projekt.SystemPolicja;

public class Mandat {
    private int id;
    private String date, imie, nazwisko, opis, kwota, pesel, adres;

    public Mandat(int id, String date, String imie, String nazwisko, String opis, String kwota, String pesel, String adres) {
        this.id = id;
        this.date = date;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.opis = opis;
        this.kwota = kwota;
        this.pesel = pesel;
        this.adres = adres;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getImie(){
        return imie;
    }

    public String getNazwisko(){
        return nazwisko;
    }
    public String getOpis(){
        return opis;
    }
    public String getKwota(){
        return kwota;
    }

    public String getPesel(){
        return pesel;
    }
    public String getAdres(){
        return adres;
    }



}
