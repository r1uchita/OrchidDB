/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.awt.Window;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oracle_util.connection_oracle;

/**
 * FXML Controller class
 *
 * @author Deepak
 */
public class Insert_Data implements Initializable {

    @FXML
    private AnchorPane anchor_pane;
    ObservableList col_names = FXCollections.observableArrayList();
    @FXML
    private JFXComboBox table_names;

    @FXML
    private VBox vbox;
    @FXML
    private JFXButton insert_button;
    connection_oracle obj = new connection_oracle();
    JFXTextField[] textfields;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        get_table_names();
        table_names.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> {

            try {
                vbox.getChildren().remove(0, vbox.getChildren().size());
                col_names.remove(0, col_names.size());
                get_column_names(newvalue.toString());
                textfields = new JFXTextField[col_names.size()];
                for (int i = 0; i < col_names.size(); i++) {
                    textfields[i] = new JFXTextField();
                    textfields[i].setPrefWidth(286);
                    textfields[i].setPromptText(" pls Enter " + col_names.get(i));
                    vbox.getChildren().add(textfields[i]);

                }

                //Build_query(textfields);
            } catch (SQLException ex) {
                Logger.getLogger(Fetch_Record.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    void get_column_names(String table_name) throws SQLException {
        col_names.remove(0, col_names.size());
        connection_oracle obj = new connection_oracle();
        ResultSet rs = obj.get_column_name(table_name);
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            col_names.add(rs.getMetaData().getColumnName(i + 1));
            System.out.println(col_names.get(i));
        }
    }

    void get_table_names() {

        ResultSet rs;
        try {
            rs = obj.get_table_name();

            ObservableList<String> data;
            int count = 3;
            //System.out.println(rs.last());
            rs.last();
            count = rs.getRow();
            String[] items = new String[count];
            //System.out.println(count);
            int i = 0;
            rs = obj.get_table_name();
            while (rs.next()) {

                items[i] = rs.getString(1);
                //System.out.println(items[i]);
                i++;

                data = FXCollections.observableArrayList(items);
                // System.out.println(data);
                table_names.setItems(data);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Add_column.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void Build_query() throws SQLException {
        javafx.stage.Window owner = insert_button.getScene().getWindow();
        if (table_names.getSelectionModel().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                    "Please Select Table  Name");
        } else {
            String query = "Insert  into ";
            query += table_names.getSelectionModel().getSelectedItem().toString() + " values(";
            String values = "";
            for (int i = 0; i < col_names.size(); i++) {
                JFXTextField j = (JFXTextField) vbox.getChildren().get(i);
                j.setPrefWidth(286);
                if (j.getText().isEmpty()) {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                            "Please Enter data");
                } else {
                    if (i == col_names.size() - 1) {
                        query += "?)";
                    } else {
                        query += "?,";
                    }

                }
            }
            System.out.println(query);
            try {
                PreparedStatement ps = obj.conn.prepareStatement(query);
                for (int i = 0; i < col_names.size(); i++) {
                    JFXTextField j = (JFXTextField) vbox.getChildren().get(i);
                    ps.setString(i + 1, j.getText());
                }

                ResultSet rs = ps.executeQuery();
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Result!", "Sucess Recoreds are inserted");
            } catch (Exception e) {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Result!", e.toString());
            }
            for (int i = 0; i < col_names.size(); i++) {
                JFXTextField j = (JFXTextField) vbox.getChildren().get(i);
                j.setText("");
            }
        }
    }
}
