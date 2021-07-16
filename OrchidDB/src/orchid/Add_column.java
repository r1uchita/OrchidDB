/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import oracle_util.connection_oracle;

/**
 * FXML Controller class
 *
 * @author Ruchita
 */
public class Add_column implements Initializable {

    @FXML
    private JFXComboBox table_names;

    @FXML
    private Button add_column_button;

    @FXML
    private Button cancel_button;

    @FXML
    private ComboBox datatype;

    @FXML
    private CheckBox pk;

    @FXML
    private CheckBox nn;

    @FXML
    private CheckBox ai;

    @FXML
    private TextField column_name_textfield;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          get_table_details object=new get_table_details();
        object.get_table_names(table_names);
        connection_oracle obj = new connection_oracle();
        ObservableList<String> data_type = FXCollections.observableArrayList("Varchar2", "Number", "Float", "Date");
        datatype.setItems(data_type);

        if (pk.isSelected()) {
            nn.setSelected(true);
        }
    }

    @FXML
    void add_column() throws SQLException {
        Window owner = add_column_button.getScene().getWindow();
        if (table_names.getSelectionModel().isEmpty() | column_name_textfield.getText().isEmpty() | datatype.getSelectionModel().isEmpty()) {

            if (table_names.getSelectionModel().isEmpty()) {
                System.out.println("combobox empty");
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                        "Please Select table name");
            }
            if (column_name_textfield.getText().isEmpty()) {
                System.out.println("combobox empty");
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                        "Please Enter new column  name");
            }
            if (datatype.getSelectionModel().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                        "Please select data types");
            }
        } else {
            String table_name = table_names.getSelectionModel().getSelectedItem().toString().toUpperCase();
            String column_name = column_name_textfield.getText().toString();
            String data_type = datatype.getSelectionModel().getSelectedItem().toString().toUpperCase();
            String constarints = "";
            if (pk.isSelected()) {
                constarints = "primary key";
            } else if (nn.isSelected()) {
                constarints = "not null";
            } 
            else if (ai.isSelected()) {
                constarints = "auto increment";
            }
            String result,query;
            if(constarints==null)
                query="alter table "+table_name+"add"+column_name+data_type;
            else
                query="alter table "+table_name+" add "+column_name+" "+data_type+" "+constarints;         
           connection_oracle obj = new connection_oracle();
           result = obj.alter_table(query);
            
            if (result.equalsIgnoreCase("Sucess")) {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Sucess!",
                        "Column Added Sucessfully!");
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!", result);
            }
                table_names.getSelectionModel().clearSelection();
        datatype.getSelectionModel().clearSelection();
        column_name_textfield.setText(null);
        pk.setSelected(false);
        nn.setSelected(false);
        ai.setSelected(false);
        }
    }

    @FXML
    void reset_data() {
        table_names.getSelectionModel().clearSelection();
        datatype.getSelectionModel().clearSelection();
        column_name_textfield.setText(null);
        pk.setSelected(false);
        nn.setSelected(false);
        ai.setSelected(false);
    }

}
