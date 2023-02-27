package com.nely.gesfond.Controller;


import com.nely.gesfond.dbHelper.Connector;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    public Boolean Connexion(String userName, String mdp) throws SQLException, ClassNotFoundException, UnsupportedEncodingException, NoSuchAlgorithmException {
        Connection con = Connector.getConect();
        String query ="select * from user where( username = ? and passwrd = ?)";

        PreparedStatement statement = con.prepareStatement(query);

        byte[] bytesOfMessage = mdp.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] thedigest = md.digest(bytesOfMessage);

        BigInteger bigInt = new BigInteger(1,thedigest);

        StringBuilder hashtext = new StringBuilder(bigInt.toString(16));

        while(hashtext.length() < 32 ){
            hashtext.insert(0, "0");
        }

        System.out.print(hashtext);

        statement.setString(1, userName);
        statement.setString(2, hashtext.toString());
        ResultSet result = statement.executeQuery();

        return result.next();
    }
    public int getUserId(String userName) throws SQLException, ClassNotFoundException {
        Connection con = Connector.getConect();
        String query ="select * from user where( username = ?)";

        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, userName);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
}
