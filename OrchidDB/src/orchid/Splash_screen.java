/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Deepak
 */
public class Splash_screen extends Thread  implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private AnchorPane ap;
      @Override

    public void run() {
        try {
            Thread.sleep(500);
            Platform.runLater(() -> {
                Stage stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                ap.getScene().getWindow().hide();
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
       /* FadeTransition.applyFadeTransition(ap,Duration.seconds(5),e->{
        try
        {
            Parent  fxml=FXMLLoader.load(getClass().getResource("Login.fxml"));
            ap.getChildren().removeAll();
            
            ap.getChildren().setAll(fxml);
        }
        catch(IOException ex){
                 Logger.getLogger(splash_screen.class.getName()).log(Level.SEVERE, null, ex);
        }
        });*/
    }    
    
}
