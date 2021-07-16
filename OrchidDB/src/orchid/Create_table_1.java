/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import com.sun.prism.impl.Disposer.Record;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Deepak
 */
public class Create_table_1 implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private TableView tabel;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabel.getItems().clear();
        tabel.getColumns().clear();
        String[] title = new String[]{"name","address","email"};
        for (String t : title) {
        TableColumn<create_table_bean,Object> col = new TableColumn<>(t.toUpperCase());
        col.setCellValueFactory(new PropertyValueFactory<>(t));
        tabel.getColumns().add(col);
         }
        
        tabel.getItems().add(new create_table_bean());
       
        // TODO
    }    
    
}
 //Define the button cell
   
