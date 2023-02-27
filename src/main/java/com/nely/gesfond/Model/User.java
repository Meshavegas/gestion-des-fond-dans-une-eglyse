package com.nely.gesfond.Model;

public class User {
    int id;
    private static String nom;
    String poste;
    String Password;

    public User() {
    }
    public User(String nom) {
        User.nom = nom;
    }
    public User( String nom, String poste, String password) {
        User.nom = nom;
        this.poste = poste;
        Password = password;
    }
    public User(int id, String nom, String poste, String password) {
        this.id = id;
        User.nom = nom;
        this.poste = poste;
        Password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        User.nom = nom;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
