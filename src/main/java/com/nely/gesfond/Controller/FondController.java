package com.nely.gesfond.Controller;

import com.nely.gesfond.dbHelper.Connector;

import java.sql.*;

public class FondController {

    public double getMontant() throws SQLException, ClassNotFoundException {
        Connection con = Connector.getConect();
        double montant = 0.0;
        String query = "select * from fond";
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            if (result.getInt("isImcome") == 0) {
                montant -= result.getDouble("montant");
            } else {
                montant += result.getDouble("montant");
            }
        }
        System.out.println("Solde : " + montant);
        return montant;
    }

    public int getTransac() throws SQLException, ClassNotFoundException {
        Connection con = Connector.getConect();
        String query = "select count(id) from fond";
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            return result.getInt("count(id)");
        }
        return 0;
    }
    public ResultSet loadFond() throws SQLException, ClassNotFoundException {
        Connection con = Connector.getConect();
        String query = """
                SELECT fd.id, fd.montant, fd.date_saisir, fd.origine, fd.isImcome, tp.nom, usr.userName  
                FROM fond as fd 
                INNER JOIN  type tp on tp.id = fd.type_id 
                INNER JOIN  user usr on usr.id = fd.user_id order by fd.id
                """;
        PreparedStatement statement = con.prepareStatement(query);
        return statement.executeQuery();
    }
    public void deleteFond(int id) throws SQLException, ClassNotFoundException {
        Connection con = Connector.getConect();
        String query = "DELETE FROM fond WHERE id=?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1,id);

        statement.executeUpdate();
    }

    public ResultSet getSeachedFon(String word) throws SQLException, ClassNotFoundException {
        Connection con = Connector.getConect();
        String query = """
                SELECT fd.id, fd.montant, fd.date_saisir, fd.origine, fd.isImcome, tp.nom, usr.userName  
                FROM fond as fd 
                INNER JOIN  type tp on tp.id = fd.type_id 
                INNER JOIN  user usr on usr.id = fd.user_id
                where fd.origine like ? or tp.nom like ? or usr.userName like ? order by fd.id
                """;
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1,"%"+word+"%");
        statement.setString(2,"%"+word+"%");
        statement.setString(3,"%"+word+"%");
        return statement.executeQuery();
    }

    public int insertFond(double montant, int type_id, String origine, int isImcome, Date date, int user_id) throws SQLException {
        int isDone = 0;
        try {
            Connection con = Connector.getConect();

        String query = "INSERT INTO fond(montant,type_id,date_saisir,user_id,origine,isImcome) VALUES(?,?,?,?,?,?)";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setDouble(1,montant);
        statement.setInt(2, type_id);
        statement.setDate(3, date);
        statement.setInt(4, user_id);
        statement.setString(5,origine);
        statement.setInt(6,isImcome);

        isDone = statement.executeUpdate();
        }
        catch(Exception e) {
            System.err.println("Erreur ! ");
            System.err.println(e.getMessage());
        }

    return isDone;
    }

    public  ResultSet getOrigine() throws SQLException, ClassNotFoundException {
        Connection con = Connector.getConect();
        String query = "SELECT  montant, origine FROM mesfonds.fond group by(origine)";
        PreparedStatement statement = con.prepareStatement(query);
        return statement.executeQuery();


    }
    public  ResultSet getType() throws SQLException, ClassNotFoundException {
        Connection con = Connector.getConect();
        String query = "SELECT fd.montant, tp.nom FROM fond fd INNER JOIN type tp ON tp.id =fd.type_id GROUP BY (tp.id);";
        PreparedStatement statement = con.prepareStatement(query);
        return statement.executeQuery();


    }
    public ResultSet getFondById(int id) throws SQLException, ClassNotFoundException {
        Connection con = Connector.getConect();
        String query = """
                SELECT fd.id, fd.montant, fd.date_saisir, fd.origine, fd.isImcome, tp.nom, usr.userName  
                FROM fond as fd 
                INNER JOIN  type tp on tp.id = fd.type_id 
                INNER JOIN  user usr on usr.id = fd.user_id 
                where fd.id = ?
                """;
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1,id);
        return statement.executeQuery();
    }
}
