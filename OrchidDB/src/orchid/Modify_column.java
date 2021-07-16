/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Window;
import oracle_util.connection_oracle;

/**
 * FXML Controller class
 *
 * @author Rupa
 */
public class Modify_column implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXComboBox table_names;

    @FXML
    private JFXComboBox column_names;

    @FXML
    private JFXComboBox data_types;

    @FXML
    private JFXTextField size;

    @FXML
    private JFXCheckBox pk;

    @FXML
    private JFXCheckBox nn;

    @FXML
    private JFXButton modify_column;

    @FXML
    private JFXCheckBox ai;

    connection_oracle obj = new connection_oracle();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        get_table_details object=new get_table_details();
        object.get_table_names(table_names);

        //get_table_names();
        table_names.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> {
            try {

                object.get_column_names(newvalue.toString(), column_names);
               // get_column_names(newvalue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(Fetch_Record.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        ObservableList<String> data_type = FXCollections.observableArrayList("Varchar2", "Number", "Float", "Date");
        data_types.setItems(data_type);
        column_names.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> {
            try {
                get_column_type(newvalue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(Fetch_Record.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    void get_column_type(String col_name) throws SQLException {
        connection_oracle obj = new connection_oracle();
        String table_name = table_names.getSelectionModel().getSelectedItem().toString();
        ObservableList col_names = FXCollections.observableArrayList();
        ResultSet rs = obj.get_column_name(table_name);
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            if (col_name.equalsIgnoreCase(rs.getMetaData().getColumnName(i + 1))) {
                //String s=rs.getMetaData().getColumnType(i+1).toString();
                int s1 = rs.getMetaData().getColumnType(i + 1);
                data_types.getSelectionModel().select(s1);
            }

        }
    }

    @FXML
    void modify_column() throws SQLException {
        Window owner = modify_column.getScene().getWindow();
        if (table_names.getSelectionModel().isEmpty() | column_names.getSelectionModel().isEmpty()) {
            if (table_names.getSelectionModel().isEmpty()) {
                System.out.println("combobox empty");
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                        "Please Select table name");
            }
            if (column_names.getSelectionModel().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                        "Please Select Column Name");
            }
        }
        else {
            String table_name = table_names.getSelectionModel().getSelectedItem().toString().toUpperCase();
            String old_column_name = column_names.getSelectionModel().getSelectedItem().toString().toUpperCase();
            String data_type = data_types.getSelectionModel().getSelectedItem().toString();
            String size_column = size.getText().toString();
            String query = "alter table " + table_name + " modify( " + old_column_name + " "+data_type + " (" + size_column + "))";
            String result = obj.alter_table(query);
            if (result.equalsIgnoreCase("Sucess")) {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Sucess!",
                        "Column Modified  Sucessfully!");
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!", result);
            }
            table_names.getSelectionModel().clearSelection();
        column_names.getSelectionModel().clearSelection();
        data_types.getSelectionModel().clearSelection();
        size.setText("");
        }
    }
}
