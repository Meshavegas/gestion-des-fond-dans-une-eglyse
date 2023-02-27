package com.nely.gesfond.Controller;

import com.nely.gesfond.dbHelper.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeController {
    public ResultSet loadType() throws SQLException, ClassNotFoundException {
        Connection con = Connector.getConect();
        String query = "select * from type";
        PreparedStatement statement = con.prepareStatement(query);

        return statement.executeQuery();
    }

    public int getId(String nom) throws SQLException, ClassNotFoundException {
        Connection con = Connector.getConect();
        String query = "select id from type where nom = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, nom);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}
