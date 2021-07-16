/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Rupa
 */
public class ORCHID extends Application {
   static  boolean issplahloaded=false;
    @Override
   public void start(Stage stage) throws Exception {
      // Create_table_new object=new Create_table_new();
       //object.setVisible(true); 
        
        Parent root=null;
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        Scene scene = new Scene(root);
        //scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Login");
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
