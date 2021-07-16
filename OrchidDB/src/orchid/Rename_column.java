/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.stage.Window;
import javafx.util.Callback;
import oracle_util.connection_oracle;

/**
 * FXML Controller class
 *
 * @author Ruchita
 */
public class Rename_column implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXComboBox   table_names;
    @FXML
    private JFXComboBox column_names;
    @FXML
    private JFXTextField new_column_name;
    @FXML
    private JFXButton rename_button;
    @FXML
    private JFXButton cancel_btn;
    connection_oracle obj = new connection_oracle();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        get_table_details  object=new get_table_details();
        
        object.get_table_names(table_names);
        table_names.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> {
            try {
                object.get_column_names(newvalue.toString(), column_names);
                    //get_column_names(newvalue.toString());
            } catch (SQLException ex) {
                Logger.getLogger(Fetch_Record.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    
    @FXML
    void rename_column() throws SQLException
    {
         Window owner = rename_button.getScene().getWindow();
        if (table_names.getSelectionModel().isEmpty() | new_column_name.getText().isEmpty() | column_names.getSelectionModel().isEmpty()) {

            if (table_names.getSelectionModel().isEmpty()) {
                System.out.println("combobox empty");
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                        "Please Select table name");
            }
            
            if (column_names.getSelectionModel().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                        "Please select column");
            }
            if (new_column_name.getText().isEmpty()) {
                System.out.println("combobox empty");
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                        "Please Enter new column  name");
            }
        } else {
            String table_name = table_names.getSelectionModel().getSelectedItem().toString().toUpperCase();
            String new_name = new_column_name.getText().toString();
            String old_column_name = column_names.getSelectionModel().getSelectedItem().toString().toUpperCase();
             String query="alter table "+table_name+" rename column "+old_column_name+" to "+new_name;         
           //connection_oracle obj = new connection_oracle();
           String result = obj.alter_table(query);
            
            if (result.equalsIgnoreCase("Sucess")) {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Sucess!",
                        "Column Rename Sucessfully!");
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!", result);
            }
            table_names.getSelectionModel().clearSelection();
        column_names.getSelectionModel().clearSelection();
        new_column_name.setText("");
        }
    }
     @FXML
    void reset_data() {
      
        table_names.getSelectionModel().clearSelection();
        column_names.getSelectionModel().clearSelection();
        new_column_name.setText("");
         }
    
}
