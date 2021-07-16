/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
public class Drop_column implements Initializable {
    @FXML
    private JFXComboBox table_names;

    @FXML
    private JFXComboBox column_names;
    @FXML
    private JFXButton drop_table;

    connection_oracle obj = new connection_oracle();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        get_table_details object=new get_table_details();
        
        object.get_table_names(table_names);
        table_names.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> {
            try {
                object.get_column_names(newvalue.toString(),column_names);
            } catch (SQLException ex) {
                Logger.getLogger(Fetch_Record.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    @FXML
    void drop_column() throws SQLException {
        Window owner = drop_table.getScene().getWindow();
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
        } else {
            String table_name = table_names.getSelectionModel().getSelectedItem().toString().toUpperCase();
            String old_column_name = column_names.getSelectionModel().getSelectedItem().toString().toUpperCase();
            String query = "alter table " + table_name + " drop column " + old_column_name;
            String result = obj.alter_table(query);
            if (result.equalsIgnoreCase("Sucess")) {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Sucess!",
                        "Column Drop  Sucessfully!");
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!", result);
            }
            table_names.getSelectionModel().clearSelection();
        column_names.getSelectionModel().clearSelection();
    
        }
    }
}
