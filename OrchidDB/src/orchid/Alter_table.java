/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import java.awt.Font;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Ruchita
 */
public class Alter_table implements Initializable {

    /**
     * Initializes the controller class.
     */
    call_fxml_page obj=new call_fxml_page();
        @FXML
    private AnchorPane anchor_pane;
    
   @FXML
    private Hyperlink add_column_link;

    @FXML
    private Hyperlink rename_column_link;

    @FXML
    private Hyperlink drop_column_link;

    @FXML
    private Hyperlink modify_column_link;

    @FXML
    private Hyperlink rename_tabel_link;
    @FXML
    void call_add(ActionEvent event) throws IOException {
        obj.open_fxml("add_column.fxml", "Add Column");
    }

    @FXML
    void call_drop(ActionEvent event) throws IOException {
        obj.open_fxml("drop_column.fxml", "Drop Column");
    }

    @FXML
    void call_modify(ActionEvent event) throws IOException {
        obj.open_fxml("modify_column.fxml", "Modify Column");
    }

    @FXML
    void call_rename(ActionEvent event) throws IOException {
        obj.open_fxml("rename_column.fxml", "Rename Column");
    }
    
    @FXML
    void call_rename_table(ActionEvent event) throws IOException {
        obj.open_fxml("rename_table.fxml", "Rename Table");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
       
        
        // TODO
    }  
    
    void call_alter_table() throws IOException {
        
             Parent root = FXMLLoader.load(getClass().getResource("create_table.fxml"));
        
        Scene scene = new Scene(root);
        
        Stage stage=new Stage();
         stage.setScene(scene);
          stage.setTitle("Alter Table");
          stage.initStyle(StageStyle.UTILITY);
            final Label label = new Label("Address Book");
       
        //label.setFont(new Font("Arial", 20));
       
  //      anchor_pane.add(label, 0, 0);
      //  anchor_pane.getChildren().add(label
  

    }
    
}
