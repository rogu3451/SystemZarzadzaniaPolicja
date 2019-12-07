package com.projekt.SystemPolicja;

public class User {
    private int id;
    private String login, haslo, imie, nazwisko, pesel, telefon;

    public User(int id, String login, String haslo, String imie, String nazwisko, String pesel, String telefon) {
        this.id = id;
        this.login = login;
        this.haslo = haslo;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.telefon = telefon;
    }

    public int getId() {
        return id;
    }

    public String getLogin(){
        return login;
    }

    public String getHaslo(){
        return haslo;
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

    public String getTelefon(){
        return telefon;
    }

}
