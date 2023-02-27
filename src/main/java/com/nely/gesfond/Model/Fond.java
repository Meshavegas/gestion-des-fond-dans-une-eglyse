package com.nely.gesfond.Model;

import java.sql.Date;

public class Fond {
    int id;
    double montant;
     String origine_id;
     String type_id;
     Date date_saisir;
     String isIncome;

    String user_id;

    public Fond() {
    }

    public Fond(double montant, String origine_id, String type_id, Date date_saisir, String isIncome, String user_id) {
        this.montant = montant;
        this.origine_id = origine_id;
        this.type_id = type_id;
        this.date_saisir = date_saisir;
        this.isIncome = isIncome;
        this.user_id = user_id;
    }

    public Fond(int id, double montant, String origine_id, String type_id, Date date_saisir, String isIncome, String user_id) {
        this.id = id;
        this.montant = montant;
        this.origine_id = origine_id;
        this.type_id = type_id;
        this.date_saisir = date_saisir;
        this.isIncome = isIncome;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getOrigine_id() {
        return origine_id;
    }

    public void setOrigine_id(String origine_id) {
        this.origine_id = origine_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public Date getDate_saisir() {
        return date_saisir;
    }

    public void setDate_saisir(Date date_saisir) {
        this.date_saisir = date_saisir;
    }

    public String getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(String isIncome) {
        this.isIncome = isIncome;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
