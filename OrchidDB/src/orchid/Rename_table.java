package orchid;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Window;
import oracle_util.connection_oracle;
import orchid.Fetch_Record;
/**
 * FXML Controller class
 *
 * @author Deepak
 */
public class Rename_table implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private JFXComboBox  table_names;

    @FXML
    private JFXTextField new_table_name;

    @FXML
    private JFXButton rename_button;

    @FXML
    private JFXToggleButton them_changer;

 connection_oracle obj=new connection_oracle();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        get_table_details object=new get_table_details();
        object.get_table_names(table_names);
           
    }    
       @FXML
    void rename_table() throws SQLException {
              String new_name=new_table_name.getText();
              Window owner = rename_button.getScene().getWindow();
              System.out.println("before if");
              
            if(new_table_name.getText().isEmpty()){
                System.out.println("name empty");
               AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!", 
                    "Please enter  new table name");
                    
            }
            if(table_names.getSelectionModel().isEmpty()){
                System.out.println("combobox empty");
              AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!", 
                    "Please Select table name");
            
             }
        
        else{
             System.out.println("in button click else befor function");
            String t_name=table_names.getSelectionModel().getSelectedItem().toString();
            String result=obj.rename_table_name(t_name,new_name);
            if(result.equalsIgnoreCase("Sucess"))
            {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Sucess!", 
                    "Table Renamed Sucessfully");
            }
            else{
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!", result);
              
            }
            table_names.getSelectionModel().clearSelection();
            new_table_name.setText("");
        }
    }
    
    
}
