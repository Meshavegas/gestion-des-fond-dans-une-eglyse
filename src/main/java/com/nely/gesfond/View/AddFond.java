package com.nely.gesfond.View;

import com.nely.gesfond.Controller.FondController;
import com.nely.gesfond.Controller.TypeController;
import com.nely.gesfond.Controller.UserController;
import com.nely.gesfond.Model.Type;
import com.nely.gesfond.Model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddFond implements Initializable {

    @FXML
    private Button adh_anuler;

    @FXML
    private DatePicker adh_datn;

    @FXML
    private Text adh_statut;


    @FXML
    private TextField fond_mont;

    @FXML
    private TextField fond_origine;

    @FXML
    private Button adh_valider;

    @FXML
    private ComboBox<String> income_com;

    @FXML
    private ComboBox<String> type_combo;

FondController fondController = new FondController();
UserController userController = new UserController();
    ObservableList<String> typesListe = FXCollections.observableArrayList();
    TypeController typeController = new TypeController();
        @FXML
        void OnBtnClick(ActionEvent event) throws SQLException, ClassNotFoundException {
            if (event.getSource() == adh_valider){
                boolean isError = false;
                System.out.println("Montant : "+ fond_mont.getText()+" Type : "+ type_combo.getValue());

                if (type_combo.getValue().length() < 1 || typeController.getId(type_combo.getValue()) == 0){
                    isError = true;
                    adh_statut.setText("Veuiller entrez le champs destiner à");
                }
                if (Double.valueOf(fond_mont.getText().toString()) <= 0){
                    isError = true;
                    adh_statut.setText("Veuiller entrez le champs montant");
                }
                if (fond_origine.getText().length()<3){
                    isError = true;
                    adh_statut.setText("Veuiller entrez le champs obtenue à");
                }
                if (income_com.getValue().length() <1){
                    isError = true;
                    adh_statut.setText("Veuiller entrez le entrée sorttir");
                }
                System.out.print(isError);
                if (!isError){
                    int income = 0;
                    if(income_com.getValue() == "Entrée"){
                        income=1;
                    }else {
                        income=0;
                    }

                    System.out.print(fondController.insertFond(Double.valueOf(fond_mont.getText().toString()), typeController.getId(type_combo.getValue()),fond_origine.getText(),income, Date.valueOf(adh_datn.getValue()), userController.getUserId(Connexion.getNom())));
                    Stage stage =(Stage) adh_valider.getScene().getWindow();
                    stage.close();

                }
                
            } else if (event.getSource() == adh_anuler) {
                Stage stage =(Stage) adh_anuler.getScene().getWindow();
                stage.close();

            }
        }
        void loadType(){
            try {
                ResultSet resultSet = typeController.loadType();
                while (resultSet.next()){
                    Type type = new Type(resultSet.getInt(1),resultSet.getString(2));
                    typesListe.add(type.getNom());
                }
                type_combo.setItems(typesListe);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        void isUpdate() throws SQLException, ClassNotFoundException {
            if (Dashbord.getEdited_id() != 0){
                ResultSet resultSet = fondController.getFondById(Dashbord.getEdited_id());

              while (  resultSet.next()){
                  fond_mont.setText(resultSet.getString(2));
                  fond_origine.setText(resultSet.getString(4));
                  adh_datn.setValue(resultSet.getDate(3).toLocalDate());
                  if (resultSet.getInt(5) == 0){
                      income_com.setValue("Entrée");
                  }else {
                      income_com.setValue("Sortie");
                  }
                  type_combo.setValue(resultSet.getString(6));

              }
            }
        }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadType();
        // adh_nom.setText(Connexion.getNom());
        income_com.getItems().add("Entrée");
        income_com.getItems().add("Sortie");
        adh_datn.setValue(LocalDate.now());
        try {
            isUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        fond_mont.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d*")) {
                    fond_mont.setText(t1.replaceAll("[^\\d]", ""));
                }
            }
        });

    }
}
