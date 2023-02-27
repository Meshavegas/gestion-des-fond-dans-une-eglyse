package com.nely.gesfond.View;

import com.nely.gesfond.Controller.UserController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Connexion {
    @FXML
    private Button btn_connect;

    @FXML
    private TextField inp_name;

    @FXML
    private TextField inp_pass;

    @FXML
    private Button close_btn;

    UserController userController = new UserController();

    private static String nom;

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        Connexion.nom = nom;
    }
    @FXML
    void onConnect(ActionEvent event) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException, ClassNotFoundException {
        if (event.getSource() == btn_connect){


            System.out.println(inp_name.getText()+":"+inp_pass.getText());
            if (userController.Connexion(inp_name.getText(),inp_pass.getText())){
                try{

                    Stage stage =(Stage) btn_connect.getScene().getWindow();
                    stage.close();

                    setNom(inp_name.getText());
                    Stage pStage = new Stage();
                    AnchorPane root = new FXMLLoader(getClass().getResource( "/com/nely/gesfond/dashbord.fxml")).load();


                    pStage.setTitle("Aceuill");
                    pStage.setResizable(false);
                    pStage.setScene(new Scene(root));
                    pStage.show();
                }catch (Exception e){
                    System.err.println(e.getMessage());
                }
            }

        } else if (event.getSource() == close_btn) {
            Stage stage =(Stage) btn_connect.getScene().getWindow();
            stage.close();
        }
    }
}
