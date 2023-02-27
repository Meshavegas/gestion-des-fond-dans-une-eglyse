package com.nely.gesfond.View;
import com.nely.gesfond.Controller.FondController;
import com.nely.gesfond.Model.Fond;
import com.nely.gesfond.Model.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Dashbord implements Initializable {


        @FXML
        private Button ac_btn;

        @FXML
        private GridPane ac_grid;

        @FXML
        private TableColumn<Fond, String> action_his_col;

        @FXML
        private TableColumn<Fond, Date> date_his_col;

        @FXML
        private TableColumn<Fond, String> efect_his_col;

        @FXML
        private Button his_add_btn;

        @FXML
        private Button his_btn;

        @FXML
        private GridPane his_grid;

        @FXML
        private Button his_refresh_btn;

        @FXML
        private TextField his_seach;

        @FXML
        private Button his_seach_btn;

        @FXML
        private TableView<Fond> his_table;

        @FXML
        private TableColumn<Fond, Integer> his_ti_col;

        @FXML
        private TableColumn<Fond, Double> mnt_his_col;

        @FXML
        private Text mtn_ac_txt;

        @FXML
        private TableColumn<Fond, String> origine_his_col;

        @FXML
        private PieChart source_ac_pi;

        @FXML
        private Button stat_btn;

        @FXML
        private Text transac_ac_txt;

        @FXML
        private PieChart type_ac_pie;

        @FXML
        private TableColumn<Fond, String> type_his_col;

        @FXML
        private TableColumn<Fond, String> isIncome_his_col;

        @FXML
        private Text user_txt;

        @FXML
        private Pane ac_pane;

        ObservableList<Fond> fondsList = FXCollections.observableArrayList();
        ObservableList<PieChart.Data> source = FXCollections.observableArrayList();
        ObservableList<PieChart.Data> type = FXCollections.observableArrayList();

        @FXML
        private Pane his_pane;

        Fond fondSelect = null;

        @FXML
        void onBtnClick(ActionEvent e) throws IOException, SQLException, ClassNotFoundException {
                if (e.getSource() == his_refresh_btn){
                        loadFond();
                        his_seach.setText("");

                } else if (e.getSource() == his_add_btn) {

                        AnchorPane root = new FXMLLoader(getClass().getResource("/com/nely/gesfond/AddFond.fxml")).load();
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);

                        stage.show();
                } else if (e.getSource() == his_seach_btn) {
                        fondsList.clear();
                        ResultSet resultSet = fondController.getSeachedFon(his_seach.getText());
                        while (resultSet.next()){
                                Fond fond = new Fond();
                                fond.setId(resultSet.getInt("id"));
                                fond.setMontant(resultSet.getDouble("montant"));
                                fond.setType_id(resultSet.getString("nom").toUpperCase());
                                fond.setDate_saisir(resultSet.getDate("date_saisir"));

                                if (resultSet.getInt("isImcome") == 1){
                                        fond.setIsIncome("Entrée");
                                }else{
                                        fond.setIsIncome("Sortie");
                                }

                                fond.setUser_id(resultSet.getString("userName").toUpperCase());
                                fond.setOrigine_id(resultSet.getString("origine"));
                                fondsList.add(fond);
                                his_table.setItems(fondsList);
                        }
                }
        }

        FondController fondController = new FondController();
private static int edited_id = 0;

        public static int getEdited_id() {
                return edited_id;
        }

        public static void setEdited_id(int edited_id) {
                Dashbord.edited_id = edited_id;
        }

        @FXML
        void onNavigate(ActionEvent e) throws SQLException, ClassNotFoundException {
                if (e.getSource() == ac_btn) {
                        ac_pane.toFront();
                        fondController.getMontant();
                } else if (e.getSource() == his_btn) {
                        his_pane.toFront();
                        loadFond();
                        his_ti_col.setCellValueFactory(new PropertyValueFactory<>("id"));
                        mnt_his_col.setCellValueFactory(new PropertyValueFactory<>("montant"));
                        type_his_col.setCellValueFactory(new PropertyValueFactory<>("type_id"));
                        origine_his_col.setCellValueFactory(new PropertyValueFactory<>("origine_id"));
                        date_his_col.setCellValueFactory(new PropertyValueFactory<>("date_saisir"));
                        efect_his_col.setCellValueFactory(new PropertyValueFactory<>("user_id"));
                        isIncome_his_col.setCellValueFactory(new PropertyValueFactory<>("isIncome"));


                        Callback<TableColumn<Fond, String> ,TableCell<Fond, String>> cellFactory = (TableColumn<Fond, String > param) ->{
                                final javafx.scene.control.TableCell<Fond,String> cell = new TableCell<Fond, String>(){
                                        @Override
                                        public void updateItem(String item, boolean empty){
                                                super.updateItem(item,empty);
                                                if (empty){

                                                        setGraphic((null));
                                                        setText(null);
                                                }else {
                                                        FontAwesomeIconView deleteicon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                                                        FontAwesomeIconView editicon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);


                                                        deleteicon.setStyleClass("myDeleteIcon");

                                                        editicon.setStyleClass("myEditIcon");

                                                        deleteicon.setOnMouseClicked((MouseEvent e) ->{

                                                                fondSelect = his_table.getSelectionModel().getSelectedItem();
                                                                try {
                                                                        fondController.deleteFond(fondSelect.getId());
                                                                        loadFond();
                                                                } catch (SQLException | ClassNotFoundException ex) {
                                                                        throw new RuntimeException(ex);
                                                                }
                                                                System.out.println("suprimer");
                                                        });
                                                        editicon.setOnMouseClicked((MouseEvent e) -> {

                                                                fondSelect = his_table.getSelectionModel().getSelectedItem();
                                                                try {
                                                                        edited_id = fondSelect.getId();
                                                                        AnchorPane root = new FXMLLoader(getClass().getResource("/com/nely/gesfond/AddFond.fxml")).load();
                                                                        Scene scene = new Scene(root);
                                                                        Stage stage = new Stage();
                                                                        stage.setScene(scene);
                                                                        stage.initModality(Modality.APPLICATION_MODAL);
                                                                        stage.showAndWait();
                                                                        stage.show();

                                                                } catch (IOException ex) {
                                                                        throw new RuntimeException(ex);
                                                                }
                                                                System.out.println("Edition");
                                                        });

                                                        HBox managebtn = new HBox(editicon, deleteicon);
                                                        managebtn.setStyle("-fx-alignment:center");
                                                        HBox.setMargin(deleteicon, new Insets(2, 2, 0, 3));
                                                        HBox.setMargin(editicon, new Insets(2, 3, 0, 2));

                                                        setGraphic(managebtn);

                                                        setText(null);

                                                }

                                        }

                                };
                                return cell;
                        };

                        action_his_col.setCellFactory(cellFactory);
                        his_table.setItems(fondsList);

                }

        }

        @FXML
        void getMontant() throws SQLException, ClassNotFoundException {
                mtn_ac_txt.setText(String.valueOf(fondController.getMontant()));
        }
        @FXML
        void getTransac() throws SQLException, ClassNotFoundException {
                transac_ac_txt.setText(String.valueOf(fondController.getTransac()));
        }

        @FXML
        void loadFond() throws SQLException, ClassNotFoundException {
                fondsList.clear();
                ResultSet resultSet = fondController.loadFond();
                while (resultSet.next()){
                        Fond fond = new Fond();
                        fond.setId(resultSet.getInt("id"));
                        fond.setMontant(resultSet.getDouble("montant"));
                        fond.setType_id(resultSet.getString("nom").toUpperCase());
                        fond.setDate_saisir(resultSet.getDate("date_saisir"));

                        if (resultSet.getInt("isImcome") == 1){
                                fond.setIsIncome("Entrée");
                        }else{
                                fond.setIsIncome("Sortie");
                        }

                        fond.setUser_id(resultSet.getString("userName").toUpperCase());
                        fond.setOrigine_id(resultSet.getString("origine"));
                        fondsList.add(fond);
                        his_table.setItems(fondsList);
                }
        }
        public void drawSourcePie() throws SQLException, ClassNotFoundException {
                ResultSet resultSet = fondController.getOrigine();
                while (resultSet.next()){
                        source.add(
                                new PieChart.Data(resultSet.getString(2),resultSet.getDouble(1))
                        );
                }
                source_ac_pi.setData(source);
        }
        public void drawtypePie() throws SQLException, ClassNotFoundException {
                ResultSet resultSet = fondController.getType();
                while (resultSet.next()){
                        type.add(
                                new PieChart.Data(resultSet.getString(2),resultSet.getDouble(1))
                        );
                }
                type_ac_pie.setData(type);
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                try {
                        getMontant();
                        getTransac();
                        drawSourcePie();
                        drawtypePie();
                        user_txt.setText( user_txt.getText() + " "+Connexion.getNom().toUpperCase());
                        System.out.println("userName : "+Connexion.getNom());
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                }
        }
}
