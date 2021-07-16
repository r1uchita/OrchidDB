package orchid;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author Deepak
 */
public class Login_register implements Initializable {

      @FXML
    private JFXButton sign_up;

    @FXML
    private JFXButton sign_in;

    @FXML
    private VBox v_box;

    @FXML
    void open_login(ActionEvent event) {

    }

    @FXML
    void open_register(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      
        
        TranslateTransition t= new TranslateTransition(Duration.millis(2000),v_box);
               t.setToX(20 *v_box.getLayoutX());
        t.play();
        t.setOnFinished((e)->{
            try {
               
               Parent  root = FXMLLoader.load(getClass().getResource("Register_user .fxml"));
                v_box.getChildren().removeAll();
                v_box.getChildren().setAll(root);
           }
            catch (IOException ex) {
                Logger.getLogger(ORCHID.class.getName()).log(Level.SEVERE, null, ex);
            }
      
           });    
    
    }
}