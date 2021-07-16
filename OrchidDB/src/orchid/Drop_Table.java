/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
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

public class Drop_Table implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXToggleButton them_changer;

    @FXML
    private JFXComboBox  table_names;

    @FXML
    private JFXCheckBox verification_checkbox;

    @FXML
    private JFXButton drop_table;
    connection_oracle obj = new connection_oracle();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        get_table_details object=new get_table_details();
        object.get_table_names(table_names);
    }

    @FXML
    void drop_table() throws SQLException {
        //  String new_name=new_table_name.getText();
        Window owner = drop_table.getScene().getWindow();
        System.out.println("before if");

        if (table_names.getSelectionModel().isEmpty()|!verification_checkbox.isSelected()) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                    "Please Select table name");
            if (verification_checkbox.isSelected()) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                    "Please select checkbox!");
            }
        }
        
        else {
            String t_name = table_names.getSelectionModel().getSelectedItem().toString();
                String msg = obj.drop_table_name(t_name);
                if (msg.equalsIgnoreCase("Sucess")) {
                    AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Result", "Table Droped Sucessfully!");
                } else {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning", msg);
                }
        }
        table_names.getSelectionModel().clearSelection();
        verification_checkbox.setSelected(false);
    }

    
}
