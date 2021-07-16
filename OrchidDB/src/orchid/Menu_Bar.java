/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Ruchita
 */
public class Menu_Bar implements Initializable {

    /**
     * Initializes the controller class.
     */
        @FXML
    private AnchorPane anchor_pane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       // create_menu.setGraphic(new ImageView("databse_icon.png")); // 2.png (16x16) only
    
         
        

    }    
      @FXML
    void call_alter_table(ActionEvent event) throws IOException {
        
        call_fxml_page obj=new call_fxml_page();
        obj.open_fxml("alter_table.fxml","Alter Table");
        
         /*    Parent root = FXMLLoader.load(getClass().getResource("alter_table.fxml"));
        
        Scene scene = new Scene(root);
        
        Stage stage=new Stage();
         stage.setScene(scene);
          stage.setTitle("Alter Table");
          stage.initStyle(StageStyle.UTILITY);
           stage.setResizable(false);
        stage.show();
*/
    }

    @FXML
    void call_create_table(ActionEvent event) throws IOException {
            Create_table_new object=new Create_table_new();
        object.setVisible(true); 
        
    }

    @FXML
    void call_drop_table(ActionEvent event) throws IOException {
        
        call_fxml_page obj=new call_fxml_page();
        obj.open_fxml("Drop_Table.fxml","Drop Table");
         
    }

    @FXML
    void call_rename_table(ActionEvent event) throws IOException {
            
         call_fxml_page obj=new call_fxml_page();
        obj.open_fxml("rename_table.fxml","Rename Table");
             
    }

    @FXML
    void  call_view_table(ActionEvent  event) throws IOException
    {
        call_fxml_page obj=new call_fxml_page();
        obj.open_fxml("View Table.fxml","View Table");
             
    }
    
    @FXML
    void call_fetch_records(ActionEvent event) throws IOException {
        
        call_fxml_page obj=new call_fxml_page();
        obj.open_fxml("Fetch_Record.fxml","Fetch  Table Data");
             
    }

    @FXML
    void call_insert_records(ActionEvent event) throws IOException {
        
        call_fxml_page obj=new call_fxml_page();
        obj.open_fxml("Insert_Data.fxml","Insert Data");
             
    }


    @FXML
    void call_update_records(ActionEvent event) throws IOException {

        call_fxml_page obj=new call_fxml_page();
        obj.open_fxml("Update_Record.fxml","Update Data");
             
    }
 @FXML
    void call_delete_records(ActionEvent event) throws IOException {
             call_fxml_page obj=new call_fxml_page();
        obj.open_fxml("Delete_Record.fxml","Delete Data");
    }
   @FXML
    void call_about_us(ActionEvent event) throws IOException {
             call_fxml_page obj=new call_fxml_page();
            obj.open_fxml("About_us.fxml","About Us");
    }
}
